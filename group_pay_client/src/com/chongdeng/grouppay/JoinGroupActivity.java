package com.chongdeng.grouppay;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import com.chongdeng.entity.Constant;
import com.chongdeng.entity.User;
import com.chongdeng.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.group_pay.R;
public class JoinGroupActivity extends Activity {
	
	String urlStr = HttpUtil.BASE_URL + "servlet/InviteFriendsServlet"; 
	String group_name = "";
	String fiendlist ="";	
	CheckboxAdapter listItemAdapter;
	String InviteResult = "";
	private static final int COMPLETED = 0;
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) 
	        {  
	            if (msg.what == COMPLETED) 
	            { 
	            	if(InviteResult.equals("0"))
	            	{
	            		showDialog("Sucess in inviting");	            		
	            	}
	            	else
	            		showDialog("Failed in inviting");
	            	fiendlist = "";
	        	}           
	        }  
	}; 
	    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_joingroup); 
		
		Intent intent = this.getIntent();	 	 
		Bundle b = intent.getBundleExtra("data");		
		group_name = b.getString("group_name").toString();
		
		 //按钮及事件响应
        Button getValue=(Button)findViewById(R.id.get_value);
        getValue.setOnClickListener(listener);	
        //listview 
        ListView list = (ListView) findViewById(R.id.list);        
        //存储数据的数组列表
        ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>();
    	//String []name={"William","Charles","Linng","Json","Bob","Carli","William","Charles","Linng","Json","Bob","Carli"};
    	//String []id={"1","2","3","4","5","6","7","8","9","10","11","12"};
        SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
 		String namelist = pre.getString("friends", "");	 		
 		//Toast.makeText(getApplicationContext(), namelist, Toast.LENGTH_LONG).show();
 		String name[]=namelist.split(";");	 
 		
 		int index = (int)(Math.random() * Constant.ConValue.image_array.length);
    	for(int i=0; i<name.length; i++){    		
    		HashMap<String, Object> map=new HashMap<String, Object>();
    		//map.put("friend_image", R.drawable.people);
    		map.put("friend_image", Constant.ConValue.image_array[index]);  
	        index = (index + 1) % Constant.ConValue.image_array.length;
    		map.put("friend_username", name[i]);
    		//map.put("friend_id", id[i]);
    		map.put("selected", false);
    		//添加数据
    		listData.add(map);
    	}   	
    	//适配器
    	listItemAdapter = new CheckboxAdapter(this, listData);
    	list.setAdapter(listItemAdapter);
    }
    //事件响应
    OnClickListener listener=new OnClickListener() {		
		@Override
		public void onClick(View v) {
			
			
			// TODO Auto-generated method stub			
			HashMap<Integer, Boolean> state =listItemAdapter.state;			
				
			for(int j=0;j<listItemAdapter.getCount();j++){
				System.out.println("state.get("+j+")=="+state.get(j));
				if(state.get(j)!=null){
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map=(HashMap<String, Object>) listItemAdapter.getItem(j);
					String username=map.get("friend_username").toString();	
					//String id=map.get("friend_id").toString();	
					//options+="\n"+id+"."+username;
					fiendlist += username + ";";					
				}				
			}			
			fiendlist = fiendlist.substring(0, fiendlist.length() - 1);
			
			
			
			Thread t = new Thread(new Runnable(){
			    public void run()
			    {
			    	  InviteResult = InviteFriend(group_name, fiendlist);
			    	
			    	  Message msg = new Message();  
				      msg.what = COMPLETED;  
				      handler.sendMessage(msg); 
			    }
			    });
			    t.start();	
			    
			
			//Toast.makeText(getApplicationContext(), fiendlist, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), group_name, Toast.LENGTH_LONG).show();
			
			
			
			/*
			SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
	 		String username = pre.getString("name", "");			 		
	 		urlStr = urlStr +  username + ";" + options;
		    
		    Thread t = new Thread(new Runnable(){
	 	    	public void run()
	 	    	{
	 	    		 
	 	    		 try {
	 	    		      URL url = new URL(urlStr);
	 	    		     
	 	    		      // Create a new HTTP URL connection
	 	    		      URLConnection connection = url.openConnection();
	 	    		      HttpURLConnection httpConnection = (HttpURLConnection)connection;
	 	    		 
	 	    		      int responseCode = httpConnection.getResponseCode();
	 	    		     
	 	    		      if (responseCode == HttpURLConnection.HTTP_OK) {
	 	    		        in = httpConnection.getInputStream(); 	    		     	        
	 	    		        processStream(in); 	  	    		        
	 	    		        
	 	    		        Message msg = new Message();  
	 	    		        msg.what = COMPLETED;  
	 	    		        handler.sendMessage(msg);
	 	    		      }
	 	    		     
	 	    		    }
	 	    		    catch (MalformedURLException e) {
	 	    		      Log.d(TAG, "Malformed URL Exception.", e);
	 	    		    }
	 	    		    catch (IOException e) {
	 	    		      Log.d(TAG, "IO Exception.", e);
	 	    		    }
	 	    		 
	 	    	}
	 	    });
	 	    t.start();	    	
	    */
			
			//显示选择内容
			//Toast.makeText(getApplicationContext(), options, Toast.LENGTH_LONG).show();
			
			
			
					
	 		

		}
	};	
	
	String InviteFriend(String group_name, String fiendlist)
	{
		urlStr  += "?group_name=" + group_name + "&fiendlist=" + fiendlist; 
		//Toast.makeText(getApplicationContext(), urlStr, Toast.LENGTH_LONG).show();
		return HttpUtil.queryStringForPost(urlStr);		
	}
	
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	    if(InviteResult.equals("0"))
		            	{
		            		finish();            		
		            	}
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}