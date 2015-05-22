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
	
	// ������¼��ȡ����ť
	private Button cancelBtn,loginBtn;
	// �����û��������������
	private EditText userEditText,pwdEditText;
	
	private Handler handler = new Handler() {  
	       @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == COMPLETED) {  
	            		        		
	            
	        		if(result!=null&&result.equals("0")){
	        			showDialog("Uername or Password is Wrong��please retry��");
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
		// ���ñ���
		setTitle("Paypal-User Login");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.login_system_new);
		// ͨ��findViewById����ʵ�������
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		// ͨ��findViewById����ʵ�������
		loginBtn = (Button)findViewById(R.id.loginButton);
		// ͨ��findViewById����ʵ�������
		userEditText = (EditText)findViewById(R.id.userEditText);
		// ͨ��findViewById����ʵ�������
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
	// ��¼����
	private void login(){
		// ����û�����
		final String username = userEditText.getText().toString();
		// �������
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
	
	// ���û���Ϣ���浽�����ļ�
	private void saveUserMsg(String msg){
		
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
	
		editor.putString("name", msg);
		editor.commit();
	}
	
	// ��֤����
	private boolean validate(){
		String username = userEditText.getText().toString();
		if(username.equals("")){
			showDialog("Please input username��");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			showDialog("Please input password��");
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
	// �����û����������ѯ
	private String query(String account,String password){
		// ��ѯ����
		String queryString = "username="+account+"&password="+password;
		// url
		String url = HttpUtil.BASE_URL+"servlet/LoginServlet?"+queryString;
		Log.d(TAG, "### url: " + url);
		// ��ѯ���ؽ��
		return HttpUtil.queryStringForPost(url);
    }
}