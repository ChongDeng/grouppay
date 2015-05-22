package com.chongdeng.grouppay;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chongdeng.util.HttpUtil;
import com.example.group_pay.R;

public class LoginActivity extends Activity { 
	
	String result;
	private static final int COMPLETED = 0;
	private static final String TAG = "PAUL";
	
	// 声明登录、取消按钮
	private Button cancelBtn,loginBtn;
	// 声明用户名、密码输入框
	private EditText userEditText,pwdEditText;
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {  
	            		        		
	            
	        		if(result!=null&&result.equals("0")){
	        			showDialog("Uername or Password is Wrong，please retry！");
	        		}else{
	        			saveUserMsg(result);	        			
	        			
	        			//Intent intent = new Intent(LoginActivity.this,MainActivity.class);
						Intent intent = new Intent(LoginActivity.this,MainTabActivity.class);
						startActivity(intent);
	        		}  		
			    	
	            }  
	        }  
	    }; 
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("Paypal-User Login");
		// 设置当前Activity界面布局
		setContentView(R.layout.login_system_new);
		// 通过findViewById方法实例化组件
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		// 通过findViewById方法实例化组件
		loginBtn = (Button)findViewById(R.id.loginButton);
		// 通过findViewById方法实例化组件
		userEditText = (EditText)findViewById(R.id.userEditText);
		// 通过findViewById方法实例化组件
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate()){
					login();
				}
			}
		});
		
		 TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
	        
	     // Listening to register new account link
	     registerScreen.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// Switching to Register screen
					Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
					startActivity(i);
				}
	      });
	}
	// 登录方法
	private void login(){
		// 获得用户名称
		final String username = userEditText.getText().toString();
		// 获得密码
		final String pwd = pwdEditText.getText().toString();
		
		    Thread t = new Thread(new Runnable(){
		    public void run()
		    {
		    	result=query(username,pwd);
		    	Log.d(TAG, "result is ##########: " + result);
		    	
		    	  Message msg = new Message();  
			      msg.what = COMPLETED;  
			      handler.sendMessage(msg); 
		    }
		    });
		    t.start();	
		
	}
	
	// 将用户信息保存到配置文件
	private void saveUserMsg(String msg){
		
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
	
		editor.putString("name", msg);
		editor.commit();
	}
	
	// 验证方法
	private boolean validate(){
		String username = userEditText.getText().toString();
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
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	// 根据用户名称密码查询
	private String query(String account,String password){
		// 查询参数
		String queryString = "username="+account+"&password="+password;
		// url
		String url = HttpUtil.BASE_URL+"servlet/LoginServlet?"+queryString;
		Log.d(TAG, "### url: " + url);
		// 查询返回结果
		return HttpUtil.queryStringForPost(url);
    }
}