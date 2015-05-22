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

import com.chongdeng.entity.Group;
import com.chongdeng.entity.MyFile;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.example.group_pay.R;
public class GroupActivity extends Activity {
// Group
	URL url;
	InputStream in;
	String urlStr = HttpUtil.BASE_URL + "servlet/GroupServlet?username="; 
	String username = "";
	private static final String TAG = "PAUL";
	private static final int COMPLETED = 0; 
	private static final int COMPLETED2 = 1; 
	String XmlResult = "";
	
	private ListView listView;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	ArrayList<Group> GroupList = new ArrayList<Group>();
	
	private EditText SearchText;
	private Button AddGroupButton;
	private Button RefreshGoupButton;

	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {	
	            	Log.d(TAG, "AAA");
	            	generate_groups_list();            	
	            	//showDialog("str: " + GroupList.size());            	     
	            }  
	         
	        }  
	    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_buddy);
		
		listView = (ListView)findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListenerImpl());
		
		
		AddGroupButton = (Button)findViewById(R.id.AddGroupButton); 
		AddGroupButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	 			
				Intent intent = new Intent(GroupActivity.this,CreateGroupActivity.class);					
				startActivityForResult(intent, 1);					
			}
		});
		
		RefreshGoupButton = (Button)findViewById(R.id.RefreshGroupButton); 
		RefreshGoupButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	 			
				
				/*
					SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
			 		String namelist = pre.getString("friends", "");			 		
				    showDialog("cont: " + namelist);
					*/
				
				showDialog("size: " + GroupList.size());
				
			}
		});
		
		
		getGroupsListView();      
		
	}
	
	private void getGroupsListView() 
	{
    	
    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
 		username = pre.getString("name", "");
 		
 		//showDialog(urlStr);
 		
 		Thread t = new Thread(new Runnable(){
 	    	public void run()
 	    	{
 	    		 
 	    		 try {
 	    		      url = new URL(urlStr + username);
 	    		     
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
    	
	  }
    
    
    private void generate_groups_list()
	{
    	
    	list = new ArrayList<Map<String, Object>>();
   	    adapter = new SimpleAdapter(this,getData(),R.layout.row, 
                new String[]{"name","img"}, 
                new int[]{R.id.title,R.id.img}); 
		listView.setAdapter(adapter);  
		Log.d(TAG, "333");
	}
    
    private List<Map<String, Object>> getData() 
    { 		        
    	       for(Group obj: GroupList)
    	       {
    	    	   Map<String, Object> map = new HashMap<String, Object>(); 
   		           map.put("name", obj.getName());
   		           map.put("description", obj.getDescription());
   		           map.put("OwnerName", obj.getOwnerName());
   		           map.put("TreasurerName", obj.getTreasurerName());
   		           map.put("fund", obj.getFund());
   		           map.put("img", R.drawable.circle);   	   		         
   		           /*
   		           if(obj.equals("family"))
   		        	 map.put("img", R.drawable.family);   	
   		           else if(obj.equals("students"))
   		        	map.put("img", R.drawable.student);   
   		           else   		        	   
   		             map.put("img", R.drawable.circle);
   		             */
   		       
   		           list.add(map); 
    	       }    	       
    	       return list;     
    } 	
   
    
    private class OnItemClickListenerImpl implements OnItemClickListener {


		@SuppressWarnings("unchecked")
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) { 
			
			Map<String, Object> obj = (Map<String, Object>) GroupActivity.this.adapter.getItem(position);
			String group_name = obj.get("name").toString();
		    String description = obj.get("description").toString();
		    String OwnerName = obj.get("OwnerName").toString();
		    String TreasurerName = obj.get("TreasurerName").toString();
		    String fund = obj.get("fund").toString();
		    
		    Bundle b = new Bundle();
			b.putString("name", group_name);
			b.putString("description", description);
			b.putString("OwnerName", OwnerName);
			b.putString("TreasurerName", TreasurerName);
			b.putString("fund", fund);
			Intent intent = new Intent(GroupActivity.this,GroupItemInfoActivity.class);			
			intent.putExtra("data", b);  //b是Bundle类			
			startActivity(intent); 
		    
			//Toast.makeText(getApplicationContext(), group_name, Toast.LENGTH_LONG).show();
			
			/*
			Map<String, Object> obj = (Map<String, Object>) CircleActivity.this.adapter.getItem(position);
			String circle_name = obj.get("name").toString();			
			
			
			ArrayList<String> SpecifiedCircle = new ArrayList<String>();
			for(Quake obj2: earthquakes)
			{
				if(obj2.getCircle_name().equals(circle_name))
				{
					SpecifiedCircle.add(obj2.getCircle_name());
					SpecifiedCircle.add(obj2.getPeople_name());
					SpecifiedCircle.add(obj2.getProfile_pic());
					SpecifiedCircle.add(obj2.getOccupation());
					SpecifiedCircle.add(obj2.getOrganization());
					SpecifiedCircle.add(obj2.getAboutme());					
				}
			}			  
			
			Bundle b = new Bundle();			
			b.putStringArrayList("SpecifiedCircle", SpecifiedCircle);
			
			Intent intent = new Intent(CircleActivity.this,PersonsInCircleActivity.class);			
			intent.putExtra("data", b);  //b是Bundle类			
			startActivity(intent);
 */
		 }
	}
		
   
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show(); 
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
	      
	      GroupList.clear();
	      
	      // Continue until the end of the document is reached.
	      while (eventType != XmlPullParser.END_DOCUMENT) {
	    	  //Log.d(TAG, "2.2");
	        // Check for a start tag of the results tag.
	        if (eventType == XmlPullParser.START_TAG &&
	          xpp.getName().equals("group")) {
	          eventType = xpp.next();
	          String name = "";		        
        	  String description = "";
        	  String OwnerName = "";
        	  String TreasurerName = "";
        	  String fund = "";
        	  
	          // Process each result within the result tag.
	          while (!(eventType == XmlPullParser.END_TAG &&
	            xpp.getName().equals("group"))) {
	        	   
		            // Check for the name tag within the results tag.
		            if (eventType == XmlPullParser.START_TAG &&
		              xpp.getName().equals("name"))
		            {
		              // Extract the POI name.
		              name = xpp.nextText();	
		              Log.d(TAG, "XXX" + name);
		            }	   
		            if (eventType == XmlPullParser.START_TAG &&
				              xpp.getName().equals("description"))
				    {
				       // Extract the POI name.
		               description = xpp.nextText();			                  
				    }
		            if (eventType == XmlPullParser.START_TAG &&
				              xpp.getName().equals("OwnerName"))
				    {
				        // Extract the POI name.
		            	OwnerName = xpp.nextText();			                  
				    }
		            if (eventType == XmlPullParser.START_TAG &&
				              xpp.getName().equals("TreasurerName"))
				    {
				        // Extract the POI name.
		            	TreasurerName = xpp.nextText();			                  
				    }	  
		            if (eventType == XmlPullParser.START_TAG &&
				              xpp.getName().equals("Fund"))
				    {
				        // Extract the POI name.
		            	fund = xpp.nextText();			                  
				    }	  
		            
	                // Move on to the next tag.
	                eventType = xpp.next();
	                
	            }
	            Group g = new Group(name, description, OwnerName, TreasurerName, fund);			              
	            GroupList.add(g); 
             
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
	  
	
		   
	}

	 
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {
		 Log.d(TAG, "111");
		 getGroupsListView();  
	 }
	 
	
	 /*
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
			getMenuInflater().inflate(R.menu.group_title, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			
			       case R.id.tab2_create_group:
					// 当ActionBar图标被点击时调用
				    	Toast.makeText(getApplicationContext(), "create", Toast.LENGTH_LONG).show();
						break;				    	
				
				    case R.id.tab2_refresh:
				    	Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_LONG).show();
						break;		
		    }
			
			
			
			return super.onOptionsItemSelected(item);
		}
		 
		*/
	
}
