package com.chongdeng.grouppay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.group_pay.R;
public class GroupEventInfoActivity extends Activity {
	
	Button submit_receipt_button;
	Button propose_event_button;
	Button treasurer_charge_button;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_group_event_info); 
		
		init();
	}
	
	private void init()
	{
		propose_event_button = (Button)findViewById(R.id.propose_event_button); 
		propose_event_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				Intent intent = new Intent(GroupEventInfoActivity.this,EventProposeActivity.class);				
				startActivity(intent);
			}
		});
		
		
		submit_receipt_button = (Button)findViewById(R.id.submit_receipt_button); 
		submit_receipt_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				Intent intent = new Intent(GroupEventInfoActivity.this,ReportExpenseActivity.class);				
				startActivity(intent);
				
				
			}
		});
		
		treasurer_charge_button = (Button)findViewById(R.id.treasurer_charge_button); 
		treasurer_charge_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				
				//Intent intent = new Intent(GroupEventInfoActivity.this,GroupEventInfoActivity.class);				
				//startActivity(intent);
			}
		});
	}
}
