package com.chongdeng.grouppay;



import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.chongdeng.entity.Constant;
import com.chongdeng.util.HttpUtil;
import com.example.group_pay.R;

import java.net.HttpURLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateGroupActivity extends Activity {

	private Spinner MemberListSpinner;
	private Button CancelButton;	
	private Button CreateButton;	
	private CheckboxAdapter listItemAdapter;
	private EditText GroupName;
	private EditText GroupDescription;
	private String TreasurerName = "";
	
	ListView list; 
	String FriendListStr = ""; 
	private static final int COMPLETED = 0;
	private String result = null;	
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) { 
	            	if(result!="" && result.equals("0"))
	            		showDialog("Successeful!");
	            	else
	            		showDialog("Failed!");
	            }   
	        }  
	    }; 
	    
	    
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);		
		  setContentView(R.layout.activity_creategroup);	
		     
	      
		  SetTreauserSpinnerView();
		  SetGoupmemberListView();

		  GroupName = (EditText)findViewById(R.id.group_name);		   
		  GroupDescription = (EditText)findViewById(R.id.group_description);
	      
		  CancelButton = (Button)findViewById(R.id.cancel);
		  CancelButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					finish();
				}
		
	      });
	      
		  CreateButton = (Button)findViewById(R.id.create);
		  CreateButton.setOnClickListener(listener);	
	}	
	
	 private void SetTreauserSpinnerView()
	 {
		 MemberListSpinner = (Spinner)findViewById(R.id.friendlist);	     
		 
		 SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		  String namelist = pre.getString("friends", "");	
		  namelist = "Me;" + namelist; 
			//Toast.makeText(getApplicationContext(), namelist, Toast.LENGTH_LONG).show();
		  String FriendsStr[]=namelist.split(";");	 	 
	     ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1,FriendsStr);	        
	     MemberListSpinner.setAdapter(aa);		 
	 }
	 
	 private void SetGoupmemberListView()
	 {
		    list = (ListView) findViewById(R.id.list);        
	        //存储数据的数组列表
	        ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>();
	    	
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
	 
	 OnClickListener listener=new OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				FriendListStr = "";
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
						FriendListStr += username + ";";					
					}				
				}	
				
				Thread t = new Thread(new Runnable(){
				public void run()
				{
					submit();
					
				    Message msg = new Message();  
					msg.what = COMPLETED;  
					handler.sendMessage(msg);  
				 }
				 });
				 t.start();
				
								
		}
	 };
	 
	 
	 private UrlEncodedFormEntity makeEntity(){
			String name = GroupName.getText().toString();
			String description = GroupDescription.getText().toString();
			TreasurerName = MemberListSpinner.getSelectedItem().toString();	
			SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);			
			String username = pre.getString("name", "");
			if(TreasurerName.equals("Me"))
				TreasurerName = username;
			FriendListStr += username;
			String MemberList = FriendListStr;
			/*
			Toast.makeText(getApplicationContext(), name + ";" + description + ";"  +
					TreasurerName + ";" + MemberList
					, Toast.LENGTH_LONG).show();
		*/
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
						
			params.add(new BasicNameValuePair("group_name", name));
			params.add(new BasicNameValuePair("owner_name", username));
			params.add(new BasicNameValuePair("description", description));
			params.add(new BasicNameValuePair("treasurerName", TreasurerName));
			params.add(new BasicNameValuePair("memberlist", MemberList));
			
			try {
				return new UrlEncodedFormEntity(params,HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}		
			
			return null;
	}
	 
	 private void submit(){			 
		 String url = HttpUtil.BASE_URL+"servlet/CreateGroupServlet";					 
		 HttpPost request = HttpUtil.getHttpPost(url);
		 request.setEntity(makeEntity());					
		 result= HttpUtil.queryStringForPost(request);		    
	}
	 
	 private void showDialog(String msg){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(msg)
			       .setCancelable(false)
			       .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   if(result!="" && result.equals("0"))
			        		   finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show(); 
		}
    
}
