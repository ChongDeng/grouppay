package com.chongdeng.entity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyTool {

	public static void showDialog(String msg, Activity a){
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
