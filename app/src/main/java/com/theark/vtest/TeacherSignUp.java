package com.theark.vtest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TeacherSignUp extends Activity{

	
	private static final String SP_TEACHER_INFO = null;

	public static final String SIGNUP_AS = "sign_up_as?";
	public static final String TEACHER_INFO_NAME = "TEACHER_INFO_NAME";
	public static final String TEACHER_INFO_EMAIL = "TEACHER_INFO_EMAIL";
	public static final String TEACHER_INFO_SUB = "TEACHER_INFO_SUB";
	public static final String TEACHER_INFO_DEP = "TEACHER_INFO_DEP";
	
	EditText et_name_t,et_dep_t,et_sub_t, et_email_t;
	Button bt_signup_t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_signup);
		
		/*SharedPreferences sp = getSharedPreferences(Welcome.SP_NAME, MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(SIGNUP_AS,"t");
		editor.commit();*/
		
		init();
	
	}

	private void init() {
		et_name_t = (EditText) findViewById(R.id.et_name_t);
		et_dep_t = (EditText) findViewById(R.id.et_dep_t);
		et_sub_t = (EditText) findViewById(R.id.et_sub_t);
		et_email_t = (EditText) findViewById(R.id.et_email_t);
		
		//bt_signup_t = (Button) findViewById(R.id.bt_signup_t);
	}
	
	public void signupTeacher(View v){
		SharedPreferences sp = getSharedPreferences(Welcome.SP_NAME, MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(SIGNUP_AS,"t");
		editor.commit();
		
		SharedPreferences sp_ = getSharedPreferences(SP_TEACHER_INFO, MODE_PRIVATE);
		Editor editor_ = sp_.edit();
		editor_.putString(TEACHER_INFO_NAME,et_name_t.getText().toString());
		editor_.putString(TEACHER_INFO_DEP,et_dep_t.getText().toString());
		editor_.putString(TEACHER_INFO_SUB,et_sub_t.getText().toString());
		editor_.putString(TEACHER_INFO_EMAIL,et_email_t.getText().toString());
		editor_.commit();
		
		Intent intent = new Intent(TeacherSignUp.this,TeacherDashboard.class);
		startActivity(intent);
		finish();
	}
}
