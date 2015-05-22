package com.chongdeng.grouppay;


import java.io.IOException;
import com.example.group_pay.R;
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
import com.chongdeng.entity.Group;
import com.chongdeng.entity.User;
import com.chongdeng.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

public class GroupItemInfoActivity extends Activity {
	TextView GroupNameView;
	TextView DescriptionView;
	TextView OwnerView;
	TextView TreauserNameView;
	Button JoinGroupButton; 
	Button GroupFundDetailButton;
	Button GroupEventDetailButton;
	
	
	private ListView listView;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	ArrayList<User> MemberList = new ArrayList<User>();
	
	URL url;
	InputStream in;
	String urlStr = HttpUtil.BASE_URL + "servlet/GroupMembersServlet?groupname=";	
	private static final String TAG = "YES";
	private static final int COMPLETED = 0; 
	String group_name = "";
	String fund = "";
	String OwnerName = "";
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {	            	
	            	generate_members_list();            	
	            	//showDialog("str: " + GroupList.size());            	     
	            }  
	        }   
	    };  
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_groupiteminfo); 
		
		listView = (ListView)findViewById(R.id.listView);
		
		GroupNameView = (TextView)findViewById(R.id.textView_name);
		DescriptionView = (TextView)findViewById(R.id.textView_description); 
		OwnerView = (TextView)findViewById(R.id.textView_owner); 
		TreauserNameView = (TextView)findViewById(R.id.textView_treasurer);		
		JoinGroupButton = (Button)findViewById(R.id.InviteFriendsButton); 
		JoinGroupButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				Bundle b = new Bundle();			
				b.putString("group_name", group_name);
				Intent intent = new Intent(GroupItemInfoActivity.this,JoinGroupActivity.class);
				intent.putExtra("data", b);  //b是Bundle类	
				//startActivity(intent);
				startActivityForResult(intent, 1);
			}
		});
		
        Intent intent = this.getIntent();	 	 
		Bundle b = intent.getBundleExtra("data");
		
		
		group_name = b.getString("name").toString();
		fund = b.getString("fund").toString();
	    String description = b.getString("description").toString();
	    OwnerName = b.getString("OwnerName").toString();
	    String TreasurerName = b.getString("TreasurerName").toString();
	
	    GroupNameView.setText("Name: \t" + group_name);  
		DescriptionView.setText("Description: \t" + description);  
		OwnerView.setText("Owner Name: \t" + OwnerName);		
		TreauserNameView.setText("Treasurer Name: \t" + TreasurerName);	  
		
		getMembersListView();  
		
		GroupFundDetailButton = (Button)findViewById(R.id.GroupFunDetailButton); 
		GroupFundDetailButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				Bundle b = new Bundle();			
				b.putString("group_name", group_name);
				b.putString("fund", fund);
				b.putString("OwnerName", OwnerName);
				Intent intent = new Intent(GroupItemInfoActivity.this,GroupFundActivity.class);			
				intent.putExtra("data", b);  //b是Bundle类	
				//startActivity(intent);
				startActivity(intent);
			}
		});
		
		GroupEventDetailButton = (Button)findViewById(R.id.GroupEventDetailButton); 
		GroupEventDetailButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				Bundle b = new Bundle();			
				b.putString("group_name", group_name);
				Intent intent = new Intent(GroupItemInfoActivity.this,GroupEventInfoActivity.class);
				intent.putExtra("data", b); 
				startActivity(intent);
			}
		});
		
	}
	
	 private void generate_members_list()
		{
	    	
	    	list = new ArrayList<Map<String, Object>>();
	   	    adapter = new SimpleAdapter(this,getData(),R.layout.row, 
	                new String[]{"name","img"}, 
	                new int[]{R.id.title,R.id.img}); 
			listView.setAdapter(adapter);    	
		}
	    
	    private List<Map<String, Object>> getData() 
	    { 		   
	    	       int index = (int)(Math.random() * Constant.ConValue.image_array.length);
	    	       for(User obj: MemberList)
	    	       {
	    	    	   Map<String, Object> map = new HashMap<String, Object>(); 
	   		           map.put("name", obj.getUsername());
	   		           //map.put("img", R.drawable.f1);   	   		         
	   		           map.put("img", Constant.ConValue.image_array[index]);  
	   		           index = (index + 1) % Constant.ConValue.image_array.length;
	   		           
	   		           list.add(map); 
	    	       }    	       
	    	       return list;     
	    } 	
	   
	    
	
	private void getMembersListView() 
	{		
 		Thread t = new Thread(new Runnable(){
 	    	public void run()
 	    	{
 	    		 
 	    		 try {
 	    		      url = new URL(urlStr + group_name);
 	    		      Log.d(TAG, "url: " + urlStr + group_name);
 	    		      // Create a new HTTP URL connection
 	    		      URLConnection connection = url.openConnection();
 	    		      HttpURLConnection httpConnection = (HttpURLConnection)connection;
 	    		 
 	    		      int responseCode = httpConnection.getResponseCode();
 	    		      Log.d(TAG, "### 1");
 	    		      if (responseCode == HttpURLConnection.HTTP_OK) {
 	    		        in = httpConnection.getInputStream(); 	
 	    		       Log.d(TAG, "### 2");
 	    		        processStream(in); 	  	    		        
 	    		       Log.d(TAG, "### 3");
 	    		      Log.d(TAG, "### 4 " + MemberList.size());
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
    	
	  }
	
	private void processStream(InputStream inputStream) 
	{
		 // Create a new XML Pull Parser.
	    XmlPullParserFactory factory;
	    try {
	      factory = XmlPullParserFactory.newInstance();
	      factory.setNamespaceAware(true);
	      XmlPullParser xpp = factory.newPullParser();
	      // Assign a new input stream.
	      xpp.setInput(inputStream, null);
	      int eventType = xpp.getEventType();
	      
	      MemberList.clear();
	      
	      // Continue until the end of the document is reached.
	      while (eventType != XmlPullParser.END_DOCUMENT) {
	    	  //Log.d(TAG, "2.2");
	        // Check for a start tag of the results tag.
	        if (eventType == XmlPullParser.START_TAG &&
	          xpp.getName().equals("member")) {
	          eventType = xpp.next();
	          String name = "";		        
        	  
	          // Process each result within the result tag.
	          while (!(eventType == XmlPullParser.END_TAG &&
	            xpp.getName().equals("member"))) {
	        	   
		            // Check for the name tag within the results tag.
		            if (eventType == XmlPullParser.START_TAG &&
		              xpp.getName().equals("name"))
		            {
		              // Extract the POI name.
		              name = xpp.nextText();		             
		              User u = new User(name);
		              MemberList.add(u);        
		            }	           
	                // Move on to the next tag.
	                eventType = xpp.next();
	            }
	              
             
	          // Do something with each POI name.
	          }
	        // Move on to the next result tag.
	        eventType = xpp.next();
	      }	      
	      
	    } catch (XmlPullParserException e) {
	      Log.d("PULLPARSER", "XML Pull Parser Exception", e);
	    } catch (IOException e) {
	      Log.d("PULLPARSER", "IO Exception", e);
	    }
	    
	    Log.d(TAG, "mem: " + MemberList.size());
	    	   
	}
	
	 @Override
	   protected void onActivityResult(
	    		int requestCode, int resultCode, Intent data) {    	
		    getMembersListView();  
	    }    
	


}
