package com.chongdeng.grouppay;

import com.chongdeng.entity.Constant.ConValue;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import com.example.group_pay.R;
public class MainTabActivity extends TabActivity{
	
	//定义TabHost对象
	private TabHost tabHost;
	
	//定义RadioGroup对象
	private RadioGroup radioGroup;
	
	private ActionBar actionBar;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*
        actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);  
        actionBar.show();
        */
        
        setContentView(R.layout.main_tab);
        
        initView();
        
        initData();
    }
	
	/**
	 * 初始化组件
	 */
	private void initView(){ 
		//实例化TabHost，得到TabHost对象
		tabHost = getTabHost();
		
		//得到Activity的个数
		int count = ConValue.mTabClassArray.length;				
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(ConValue.mTextviewArray[i]).setContent(getTabItemIntent(i));
			//将Tab按钮添加进Tab选项卡中 
			tabHost.addTab(tabSpec);
		}
		
		//实例化RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
	}
	
	/**
	 * 初始化组件
	 */
	private void initData() {
		// 给radioGroup设置监听事件
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioButton0:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[0]);
					break;
				case R.id.RadioButton1:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[1]);
					break;
				case R.id.RadioButton2:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[2]);
					break;
				/*
				case R.id.RadioButton4:
					tabHost.setCurrentTabByTag(ConValue.mTextviewArray[4]);
					break;
					*/
				}
			}
		});
		((RadioButton) radioGroup.getChildAt(0)).toggle();
	}
	
	/**
	 * 给Tab选项卡设置内容（每个内容都是一个Activity）
	 */
	private Intent getTabItemIntent(int index){
		Intent intent = new Intent(this, ConValue.mTabClassArray[index]);	
		return intent;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.temp, menu);
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		       case R.id.tab1_add_friend:
				// 当ActionBar图标被点击时调用
			    	Toast.makeText(getApplicationContext(), "add_friend", Toast.LENGTH_LONG).show();
					break;
			    	//Intent intent = new Intent(MainTabActivity.this,CreateGroupActivity.class);					
					//startActivityForResult(intent, 1);
			
			    case R.id.tab1_refresh:
			    	Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_LONG).show();
					break;
		 */	
		
			case android.R.id.home:
				// 当ActionBar图标被点击时调用
				Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
				break;		
		    case R.id.add_friend:
			// 当ActionBar图标被点击时调用
		    	Toast.makeText(getApplicationContext(), "add_friend", Toast.LENGTH_LONG).show();
				break;
		    	//Intent intent = new Intent(MainTabActivity.this,CreateGroupActivity.class);					
				//startActivityForResult(intent, 1);
			  
		    case R.id.create_group:
		    	Toast.makeText(getApplicationContext(), "create_group", Toast.LENGTH_LONG).show();
				break;	
		    case R.id.refresh:
		    	Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_LONG).show();
				break;	
				
		
		    
	    }
		
		
		
		return super.onOptionsItemSelected(item);
	}
}
