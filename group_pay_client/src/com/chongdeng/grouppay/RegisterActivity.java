package com.chongdeng.grouppay;


import com.chongdeng.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.group_pay.R;
public class RegisterActivity extends Activity {
	
	    // 声明登录、取消按钮
		private Button registerBtn;
		// 声明用户名、密码输入框
		private EditText emailEditText, nameEditText, pwdEditText;
		
		String result;
		private static final int COMPLETED = 0;
		private static final String TAG = "PAUL";
		
		private Handler handler = new Handler() { 
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {  
	            		        		
	            
	        		if(result!=null&&result.equals("0")){
	        			showDialog("Success"); 
	        			
	        		}else{
	        			        
	        			showDialog("Register failed");
	        		}  				    	
	            }  
	        }  
	    }; 
	    
		
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        //setContentView(R.layout.activity_register);
	        setContentView(R.layout.activity_register_2);
	        initView();
	      
	        
	    }
	 
	 private void initView()
	 {
		            emailEditText =  (EditText) findViewById(R.id.reg_email);
		            nameEditText =  (EditText) findViewById(R.id.reg_fullname);	 
		            pwdEditText =  (EditText) findViewById(R.id.reg_password);	 
		            
			        
			        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);	        
			        // Listening to Login Screen link
			        loginScreen.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View arg0) {
							// Switching to Login Screen/closing register screen
							finish();
						}
					});
			        
			        registerBtn = (Button) findViewById(R.id.btnRegister);	        
			        // Listening to Login Screen link
			        registerBtn.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View arg0) {
							if(validate()){
								register();
							}
						}
					});
			        
	 }
	 
	    // 验证方法
		private boolean validate(){
			
			String email = emailEditText.getText().toString();
			if(email.equals("")){
				showDialog("Please input email！");
				return false;
			}
			
			String username = nameEditText.getText().toString();
			if(username.equals("")){
				showDialog("Please input username！");
				return false;
			}
			String pwd = pwdEditText.getText().toString();
			if(pwd.equals("")){
				showDialog("Please input password！");
				return false;
			}
			return true;
		}
		
		private void showDialog(String msg){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(msg)
			       .setCancelable(false)
			       .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   if(result!=null&&result.equals("0"))
			        	       finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		private void register()
		{
			final String email = emailEditText.getText().toString();
			// 获得用户名称
			final String username = nameEditText.getText().toString();
			// 获得密码
			final String pwd = pwdEditText.getText().toString();
			
			    Thread t = new Thread(new Runnable(){
			    public void run()
			    {
			    	result = register(email, username, pwd);
			    	Log.d(TAG, "result is ##########: " + result);
			    	
			    	  Message msg = new Message();  
				      msg.what = COMPLETED;  
				      handler.sendMessage(msg); 
			    }
			    });
			    t.start();	
			
		}
		
		// 根据用户名称密码查询
		private String register(String email, String account,String password){
			// 查询参数
			String queryString = "email=" + email + "&username="+account+"&password="+password;
			// url
			String url = HttpUtil.BASE_URL+"servlet/RegisterServlet?"+queryString;
			Log.d(TAG, "### url: " + url);
			// 查询返回结果
			return HttpUtil.queryStringForPost(url);
	    }
}
