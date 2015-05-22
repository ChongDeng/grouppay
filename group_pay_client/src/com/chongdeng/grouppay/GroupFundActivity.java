package com.chongdeng.grouppay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chongdeng.entity.MyTool;
import com.chongdeng.util.HttpUtil;
import com.example.group_pay.R;
public class GroupFundActivity extends Activity {
	
	String group_name = "";
	String fund = "";
	String OwnerName = "";
	TextView FundView;
	Button remindButton;
	Button DonateButton;
	EditText DonateMoneyInput;
	private Button RefreshFriendButton; 
	String result = "";
	String FundResult = "";
	private static final int COMPLETED = 0;
	private static final int COMPLETED2 = 1;
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {  	            		        		
	            
	        		if(result !=null && result.equals("0")){	        			
	        			MyTool.showDialog("Donation Success", GroupFundActivity.this);
	        		}else{
	        			MyTool.showDialog("Fail! Please check whether you have enogh money!", GroupFundActivity.this);
	        		}  		 
			    	 
	            }  
	            else if (msg.what == COMPLETED2) {  
	            	//Toast.makeText(getApplicationContext(), FundResult, Toast.LENGTH_LONG).show();
	            	FundView.setText("group fund account: " + FundResult);    	 
	            }  
	        }  
	    }; 
	    

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_group_fund); 
		
		RetreiveData();
		
		setDisplay();
	}
	
	public void RetreiveData()
	{
		    Intent intent = this.getIntent();	 	 
			Bundle b = intent.getBundleExtra("data");	
			
			group_name = b.getString("group_name").toString();			
			fund = b.getString("fund").toString();
			OwnerName = b.getString("OwnerName").toString();
			
	}
	
	public void setDisplay()
	{
		FundView = (TextView)findViewById(R.id.groupfund_fund); 
		FundView.setText("group fund account: " + fund);
				
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);			
		final String username = pre.getString("name", "");
		
		remindButton = (Button)findViewById(R.id.RemindDonateButton); 
		remindButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				if(!OwnerName.equals(username))
					MyTool.showDialog("No, only group owner can remind others!", GroupFundActivity.this);
				else
					;
			}
		});
		
		 
		DonateMoneyInput = (EditText)findViewById(R.id.DonateMoneyInput); 
		DonateButton = (Button)findViewById(R.id.DonateSubmitButton);   
		DonateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				if(!DonateMoneyInput.getText().toString().trim().equals(""))
					Donate(DonateMoneyInput.getText().toString().trim());					
			}
		});			
		
		RefreshFriendButton = (Button)findViewById(R.id.DonateRefreshButton);
		RefreshFriendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {		
				
				RefreshGroupFund(group_name);
				
			}
		});	
	}
	
	private void Donate(final String money)
	{
		    Thread t = new Thread(new Runnable(){
		    public void run()
		    {
		    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);			
				String username = pre.getString("name", "");
				
				String queryString = "groupname="+group_name + "&username="+username + "&money="+money;
				// url
				String url = HttpUtil.BASE_URL+"servlet/DonateServlet?"+queryString;
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
	
	public void RefreshGroupFund(String groupname)
	{
		   Thread t = new Thread(new Runnable(){
		    public void run()
		    {
		    	
				String queryString = "groupname="+group_name;
				// url
				String url = HttpUtil.BASE_URL+"servlet/GetGroupFundServlet?"+queryString;
				
				// 查询返回结果
				FundResult = HttpUtil.queryStringForPost(url);
				
				 Message msg = new Message();  
			     msg.what = COMPLETED2;  
			     handler.sendMessage(msg); 
		    }
		    });
		    t.start();	
	}
}
