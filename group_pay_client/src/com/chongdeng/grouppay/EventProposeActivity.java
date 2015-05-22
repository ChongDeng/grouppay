package com.chongdeng.grouppay;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.group_pay.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EventProposeActivity extends Activity {
	
	private Spinner MemberListSpinner;
	private CheckboxAdapter listItemAdapter;
	private ListView listView;
	private List<Map<String, Object>> list;
	
	String group_name = "";
	EditText eventnameEditText;
	EditText eventdescEditText;
	Button Cancelbutton;
	Button SubmitButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_event_propose); 
		
		 MemberListSpinner = (Spinner)findViewById(R.id.cost_strategy_list);
		 String StrategyStr[]= {"paid by public fund", "evenly divided among us"};	 	 
	     ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1,StrategyStr);	        
	     MemberListSpinner.setAdapter(aa);	
	     
	     /*
	     Intent intent = this.getIntent();	 	 
	     Bundle b = intent.getBundleExtra("data");	
		 group_name = b.getString("group_name").toString();		
		 
		 eventnameEditText = (EditText)findViewById(R.id.eventnameEditText);
		 eventdescEditText = (EditText)findViewById(R.id.eventdescEditText);
		 
		 SubmitButton = (Button)findViewById(R.id.EventSubmitButton);
		 SubmitButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {	
					
					Submit();
				} 
			});
		 
		 Cancelbutton = (Button)findViewById(R.id.EventCancelButton);
		 Cancelbutton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {	
					
				finish();
				}
			});
			*/
	}
	
	public void Submit()
	{
		/*
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("group_name", group_name));
		params.add(new BasicNameValuePair("event_name", eventnameEditText.getText().toString().trim()));
		params.add(new BasicNameValuePair("event_desc", eventdescEditText.getText().toString().trim()));
		params.add(new BasicNameValuePair("event_cost_strategy",MemberListSpinner.getSelectedItem().toString() ));
		
		Toast.makeText(getApplicationContext(), group_name + eventnameEditText.getText().toString().trim() + 
				eventdescEditText.getText().toString().trim() + MemberListSpinner.getSelectedItem().toString()
				, Toast.LENGTH_LONG).show();
		
		*/
		/*
		params.add(new BasicNameValuePair("owner_name", username));
		params.add(new BasicNameValuePair("description", description));
		params.add(new BasicNameValuePair("treasurerName", TreasurerName));
		params.add(new BasicNameValuePair("memberlist", MemberList));
		*/
	}
}
