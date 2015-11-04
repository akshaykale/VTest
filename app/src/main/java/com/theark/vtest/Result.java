package com.theark.vtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends Activity{

	String roll = "";
	String per = "";
	
	Button bt_sub;
	TextView tv_per;
	EditText et_ip;
	
	//cli
	Socket client;
	PrintWriter printWriter;
	String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_dialog);
	
		roll = getIntent().getStringExtra("st_roll");
		per = getIntent().getStringExtra("st_per");
		String m = getIntent().getStringExtra("outoff");
		String pers = per;
		per += " %";
		
		bt_sub = (Button) findViewById(R.id.bt_exit);
		tv_per = (TextView) findViewById(R.id.tv_per);
		et_ip = (EditText) findViewById(R.id.et_ip_res);
		
		bt_sub.setText(m);
		
		tv_per.setText(per);
		
		if(Float.parseFloat(pers) >= 40.0){
			findViewById(R.id.iv_smiley).setVisibility(View.VISIBLE);
			findViewById(R.id.iv_dout).setVisibility(View.INVISIBLE);
		}else{
			findViewById(R.id.iv_smiley).setVisibility(View.INVISIBLE);
			findViewById(R.id.iv_dout).setVisibility(View.VISIBLE);
		}
		
		bt_sub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				final int port = 2489;
				final String ip = et_ip.getText().toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							client = new Socket(ip,port);
							
							printWriter = new PrintWriter(client.getOutputStream());
							printWriter.write("hi");
							printWriter.flush();
							printWriter.close();
							client.close();
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				/*
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
	            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            shareIntent.setType("text/plain");
	            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, I got "+per+" in This Test");
	            startActivity(shareIntent);
				*/
			}
		});
	}
	
	

	 
}
