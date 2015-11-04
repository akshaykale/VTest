package com.theark.vtest;

import java.util.ArrayList;
import java.util.List;

import android.R.transition;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherDashboard extends ActionBarActivity{

	//main
	Button bt_host_test_t, bt_prev_tests_t, bt_result_t;
	
	HostFragment hostFrag;
	ResultFragment resultFrag;
	StudTempFrag stutemp;
	//FragmentManager fm;
	//FragmentTransaction transaction;

	private int mContainerId = R.id.teacherdashLay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_teacher);
		
		init();
		
		hostFrag = new HostFragment();
		resultFrag = new ResultFragment();
		stutemp = new StudTempFrag();
		//fm = getSupportFragmentManager();
		//transaction = fm.beginTransaction();
		replaceFragment(stutemp);
		
		HostTest();
		PreviousTest();
		Result();
		
		//transaction.commit();
		
		
	}


	private void init() {
		//main
		bt_host_test_t = (Button) findViewById(R.id.bt_host_Test_t);
		bt_prev_tests_t = (Button) findViewById(R.id.bt_prev_tests_t);
		bt_result_t = (Button) findViewById(R.id.bt_result_tests_t);
		
		
	}
	
	private void HostTest() {
		bt_host_test_t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//transaction.add(R.id.teacherdashLay, hostFrag, "HostFragment");
				replaceFragment(hostFrag);
			}
		});
	}
	
	private void Result() {
		bt_result_t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//transaction.replace(R.id.teacherdashLay, resultFrag);//(R.id.teacherdashLay, resultFrag, "ResultFragment");
				//transaction.commit();
				replaceFragment(resultFrag);
			}
		});
	}

	private void PreviousTest() {
		bt_prev_tests_t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(stutemp);
			}
		});
	}
	
	public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();;     
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(mContainerId , fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();   
}

	
}
		
