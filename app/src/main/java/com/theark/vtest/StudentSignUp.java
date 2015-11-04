package com.theark.vtest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentSignUp extends Activity{

	public static final String SP_STUDENT_INFO = "stusignup";

	public static final String SIGNUP_AS = "sign_up_as?";
	public static final String STUDENT_INFO_NAME = "STUDENT_INFO_NAME";
	public static final String STUDENT_INFO_EMAIL = "STUDENT_INFO_EMAIL";
	public static final String STUDENT_INFO_ROLL = "STUDENT_INFO_ROLL";
	public static final String STUDENT_INFO_DEP = "STUDENT_INFO_DEP";
	
	EditText et_name_s,et_dep_s,et_roll_s, et_email_s;
	Button bt_signup_s;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.student_signup);
	
		init();
	}
	
	

	private void init() {
		et_name_s = (EditText) findViewById(R.id.et_name_s);
		et_dep_s = (EditText) findViewById(R.id.et_dep_s);
		et_roll_s = (EditText) findViewById(R.id.et_roll_s);
		//et_email_s = (EditText) findViewById(R.id.et_email_t);
		
		//bt_signup_t = (Button) findViewById(R.id.bt_signup_t);
	}
	
	public void signupStudent(View v){
		SharedPreferences sp = getSharedPreferences(Welcome.SP_NAME, MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(SIGNUP_AS,"s");
		editor.commit();
		
		SharedPreferences sp_ = getSharedPreferences(SP_STUDENT_INFO, MODE_PRIVATE);
		Editor editor_ = sp_.edit();
		editor_.putString(STUDENT_INFO_NAME,et_name_s.getText().toString());
		editor_.putString(STUDENT_INFO_DEP,et_dep_s.getText().toString());
		editor_.putString(STUDENT_INFO_ROLL,et_roll_s.getText().toString());
		//editor_.putString(STUDENT_INFO_EMAIL,et_name_s.getText().toString());
		editor_.commit();
		
		Intent intent = new Intent(StudentSignUp.this,StudentDashboard.class);
		startActivity(intent);
		finish();
	}
}