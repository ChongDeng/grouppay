package com.chongdeng.grouppay;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.chongdeng.entity.MyTool;
import com.chongdeng.entity.User;
import com.chongdeng.util.HttpUtil;

import android.app.Activity; 
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_pay.R;
public class MeActivity extends Activity {

	TextView textView_UserBalance;
	Button BalanceRefreshButton;
	EditText DonateMoneyInput;
	Button submitButton;
	
	String result = "";
	String ChargeResult = "";
	private static final int COMPLETED = 0;
	private static final int COMPLETED2 = 1;
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {            
	            	textView_UserBalance.setText("Account Balance: " + result);		    	 
	            }  
	            else if (msg.what == COMPLETED2) {  
	            	//Toast.makeText(getApplicationContext(), FundResult, Toast.LENGTH_LONG).show();
	            	if(ChargeResult.equals("0"))
						MyTool.showDialog("Charge Success", MeActivity.this);
	            	else
	            		MyTool.showDialog("Charge Failed", MeActivity.this);
	            		 
	            }  
	        }  
	    }; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);         
        
         init();
               
    }   
    
    public void init()
    {
    	textView_UserBalance = (TextView)findViewById(R.id.textView_UserBalance); 
    	BalanceRefreshButton = (Button)findViewById(R.id.BalanceRefreshButton); 
    	BalanceRefreshButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				getBalance();
				//Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
			}
		});
    	
    	DonateMoneyInput = (EditText)findViewById(R.id.DonateMoneyInput); 
    	submitButton = (Button)findViewById(R.id.submitButton); 
    	submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				//Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
				if(!DonateMoneyInput.getText().toString().trim().equals(""))
					charge(DonateMoneyInput.getText().toString().trim());				
			}
		});    	
    	
    	getBalance();
    }
    
    public void getBalance()
    {    	
    	    Thread t = new Thread(new Runnable(){
		    public void run()
		    {
		    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);			
				String username = pre.getString("name", "");
				
				String queryString = "username="+username;
				// url
				String url = HttpUtil.BASE_URL+"servlet/GetBalanceServlet?"+queryString;
				System.out.println(url);
				// 查询返回结果
				result = HttpUtil.queryStringForPost(url);
				
				 Message msg = new Message();  
			      msg.what = COMPLETED;  
			      handler.sendMessage(msg); 
		    }
		    }); 
		    t.start();	
    }
    
    public void charge(final String money)
    {
    	
    	 Thread t = new Thread(new Runnable(){
 		    public void run()
 		    {
 		    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);			
 				String username = pre.getString("name", "");
 				
 				String queryString = "username=" + username + "&money="+ money;
 				// url
 				String url = HttpUtil.BASE_URL+"servlet/ChargeServlet?"+queryString;
 				System.out.println(url);
 				// 查询返回结果
 				ChargeResult = HttpUtil.queryStringForPost(url);
 				
 				  Message msg = new Message();  
 			      msg.what = COMPLETED2;  
 			      handler.sendMessage(msg); 
 		    }
 		    });
 		    t.start();	
    }
}
