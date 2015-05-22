package com.chongdeng.grouppay;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.Random;

import org.apache.http.util.EncodingUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.chongdeng.entity.Constant;
import com.chongdeng.entity.MyFile;
import com.chongdeng.entity.User;
import com.chongdeng.util.HttpUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.util.EncodingUtils;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.group_pay.R;

public class FriendActivity extends Activity {
// Friend	
	URL url;
	InputStream in;
	String urlStr = HttpUtil.BASE_URL + "servlet/FriendServlet?username="; 
	String AddFriendUrl = HttpUtil.BASE_URL + "servlet/AddFriendServlet";   
	//servlet/AddFriendServlet?username=dc&friendname=lilei
	private static final String TAG = "PAUL";
	private static final String TAG2 = "NUM";
	private static final int COMPLETED = 0;
	private static final int COMPLETED2 = 1;
	private static final int COMPLETED3 = 2; 
	String XmlResult = "";
	String result = "";
	String ste = "";
	String username = "";
	
	private ListView listView;
	List<Map<String, Object>> list;
	SimpleAdapter adapter;
	ArrayList<User> FriendList = new ArrayList<User>();	
	
	private EditText SearchText;
	private Button AddFriendButton;
	private Button RefreshFriendButton; 

    public static final String ENCODING = "UTF-8";
    //定义文件的名称
    //String fileName = "test.txt";
    //MyFile file = new MyFile();	
    String FriendStr = "";
    
   
	      
    
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {	            	
	            	generate_friends_list();   
	            	result = "";
	            	//showDialog("str: " + strtest);            	     
	            }  
	            if (msg.what == COMPLETED2) {
	            	
	            	//showDialog(result);	            	
	            	 if(result.equals("0"))
	            		  showDialog("The user is not existent"); 
	            	 else
	            	 {
	            		 showDialog("Add friend Success");      	    
	            	 }
	            	 result = "";
	            	               	         	     
	            }  
                if (msg.what == COMPLETED3) {                	
                	generate_friends_list(); 
                	 result = "";
                	
	            }   
	        }  
	    };   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent);	
		
		listView = (ListView)findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListenerImpl()); 
		
		SearchText = (EditText)findViewById(R.id.FriendNameInput);
		AddFriendButton = (Button)findViewById(R.id.AddFriendButton);	
		RefreshFriendButton = (Button)findViewById(R.id.RefreshFriendButton);  
		AddFriendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				if(!SearchText.getText().toString().trim().equals(""))
				{
					//Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
					AddFriend();
				}
			}
		});
		
		RefreshFriendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				if(!SearchText.getText().toString().trim().equals(""))
				{
					//Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
					getRefreshedFriendsListView();				
				}				
			}
		});	
        getFriendsListView();
	}
	
	 private void getFriendsListView() 
		{
	    	
	    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
	 		username = pre.getString("name", "");
	 		//urlStr += username;
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
	 
	 private void getRefreshedFriendsListView() 
		{
	    	
	    	SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
	 		username = pre.getString("name", "");
	 		//urlStr += username;
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
	 	    		        msg.what = COMPLETED3;  
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
	    
	    
	    private void generate_friends_list()
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
	    	       for(User obj: FriendList)
	    	       {
	    	    	   Map<String, Object> map = new HashMap<String, Object>(); 
	   		           map.put("name", obj.getUsername());
	   		           map.put("img", Constant.ConValue.image_array[index]);  
	   		           index = (index + 1) % Constant.ConValue.image_array.length;
	   		           
	   		           //map.put("img", R.drawable.f1);  	   		           
	   		          
	   		           //int index = Math.abs(random.nextInt())%image_array.length;
	   		           //map.put("img", image_array[index]);
                   
	   		           
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
			FriendStr = "";		
			 // Create a new XML Pull Parser.
		    XmlPullParserFactory factory;
		    try {
		      factory = XmlPullParserFactory.newInstance();
		      factory.setNamespaceAware(true);
		      XmlPullParser xpp = factory.newPullParser();
		      // Assign a new input stream.
		      xpp.setInput(inputStream, null);
		      int eventType = xpp.getEventType();
		      
		      FriendList.clear();
		      
		      // Continue until the end of the document is reached.
		      while (eventType != XmlPullParser.END_DOCUMENT) {
		    	  //Log.d(TAG, "2.2");
		        // Check for a start tag of the results tag.
		        if (eventType == XmlPullParser.START_TAG &&
		          xpp.getName().equals("friend")) {
		          eventType = xpp.next();
		          String name = "";		        
	        	  
		          // Process each result within the result tag.
		          while (!(eventType == XmlPullParser.END_TAG &&
		            xpp.getName().equals("friend"))) {
		        	   
			            // Check for the name tag within the results tag.
			            if (eventType == XmlPullParser.START_TAG &&
			              xpp.getName().equals("name"))
			            {
			              // Extract the POI name.
			              name = xpp.nextText();		             
			              User u = new User(name);
				          FriendList.add(u);				          
				          FriendStr =  FriendStr + name + ";";
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
		    Log.d(TAG2, "1" + FriendList.size());	
		    
		    SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
			SharedPreferences.Editor editor = pre.edit();		
			editor.putString("friends", FriendStr);
			editor.commit();		    
		}
		
		private void AddFriend()
		{ 		
			int flg = 0;
			for(User obj: FriendList)
	    	{
				if(obj.getUsername().equals(SearchText.getText().toString().trim()))
				{
					flg = 1; 
					break;
				}				
	    	}
			if(flg == 1)
			{
				showDialog("You are already friends");
				flg = 0;
				return;
			}	
				
	 		Thread t = new Thread(new Runnable(){
	 	    	public void run()
	 	    	{
	 	  	    	result = QueryValidUserName();
	 	  	    	
	 	  	        Message msg = new Message();  
			        msg.what = COMPLETED2;  
			        handler.sendMessage(msg); 
	 	     	}
	 	    });
	 	    t.start();	    
		}
		
		// 根据用户名称密码查询
		private String QueryValidUserName( ){
			
			SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
	 		String username = pre.getString("name", "");
	 		String url = AddFriendUrl + "?username=" +  username + "&friendname=" 
	 		               + SearchText.getText().toString().trim();	
	 		
			return HttpUtil.queryStringForPost(url);
	    }
		
		 //向指定的文件中写入指定的数据
	     public void writeFileData(String filename, String message){
	         try {
	            FileOutputStream fout = openFileOutput(filename, MODE_PRIVATE);//获得FileOutputStream
	            //将要写入的字符串转换为byte数组
	            byte[]  bytes = message.getBytes();
	            fout.write(bytes);//将byte数组写入文件
	            fout.close();//关闭文件输出流
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     }
	                        
	     //打开指定文件，读取其数据，返回字符串对象
	     public String readFileData(String fileName){
	         String result="";
	         try {
	            FileInputStream fin = openFileInput(fileName);
	            //获取文件长度
	            int lenght = fin.available();
	            byte[] buffer = new byte[lenght];
	            fin.read(buffer);
	            //将byte数组转换成指定格式的字符串
	            result = EncodingUtils.getString(buffer, ENCODING);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	     }
	
}
