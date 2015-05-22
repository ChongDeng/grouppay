package com.chongdeng.entity;


import com.chongdeng.grouppay.GroupActivity;
import com.chongdeng.grouppay.MeActivity;

import com.chongdeng.grouppay.FriendActivity;
import com.chongdeng.grouppay.MoreActivity;
import com.example.group_pay.R;


public class Constant {

	
	public static final class ConValue{
		
		/**
		 * Tab选项卡的图标
		 */
		/*
		public static int   mImageViewArray[] = {R.drawable.tab_icon1,
											     R.drawable.tab_icon2,
											     R.drawable.tab_icon3,
											  
											     R.drawable.tab_icon5};
		*/
		
		public static int   mImageViewArray[] = {R.drawable.friend_icon,
		     R.drawable.group_icon,
		     R.drawable.me_icon,
		  
		     R.drawable.tab_icon5};

		/**
		 * Tab选项卡的文字
		 */
		public static String mTextviewArray[] = {"主页", "关于", "设置", "搜索", "更多"};
		
		
		/**
		 * 每一个Tab界面
		 */
		public static Class mTabClassArray[]= {FriendActivity.class,
			                                   GroupActivity.class,
			                                   MeActivity.class,
			                                   //MoreActivity.class,
											  };
		
		public static int image_array[] = {R.drawable.f1, R.drawable.f2, R.drawable.f3, 
	             R.drawable.f4, R.drawable.f5, R.drawable.f6,
	             R.drawable.f7, R.drawable.f8, R.drawable.f9};
	}
}
