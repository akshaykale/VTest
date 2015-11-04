package com.theark.vtest;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.theark.vtest.JSON.QueJSONParser;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class StudentDashboard extends ActionBarActivity{
	
	Button bt_joinTest;
	EditText et_hostIp;
	
	ListView lv_questionList;
	
	Socket socket = null;
	Socket socket_send = null;
	List<Question> queList;
	
	int status_send_result = 0;
	String st_result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_student);
		
		init();
		
		bt_joinTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.iv_stst).setVisibility(View.INVISIBLE);
				//findViewById(R.id.iv_t).setMinimumHeight(1);
				//findViewById(R.id.imageaaView1).destroyDrawingCache();
				JoinTestListener();				
			}
		});
		
	}

	private void init() {
		lv_questionList = (ListView) findViewById(R.id.lv_ques_s);
		et_hostIp = (EditText) findViewById(R.id.et_hostIP_s);
		//et_hostIp.setText("192.168.43.1");
		bt_joinTest = (Button) findViewById(R.id.bt_join_s);
	}
	
	private void JoinTestListener() {
		String hostIP = et_hostIp.getText().toString();
		
		MyClientTask myClientTask = new MyClientTask(
			       hostIP,
			       2489);
		Log.d("student", "client started");
			     myClientTask.execute();
		
	}
	
	public void ShowQuestionsInList(String result){
		queList = QueJSONParser.parseFeed(result);
		lv_questionList.setAdapter(new MyAdapter(getApplicationContext()));
		
		lv_questionList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				
			}
		});
	}
	
	private void EvaluateTest() {
		status_send_result = 1;
		float marks = 0;
		for(Question q : queList){
			if(q.getAnswer().equals(q.getS_ans())){
				marks ++;
			}
		}
		String m = ""+(int)marks+"/"+queList.size();
		float per = marks/queList.size() ;
		SharedPreferences sp = getSharedPreferences(StudentSignUp.SP_STUDENT_INFO, MODE_PRIVATE);
		String roll = sp.getString(StudentSignUp.STUDENT_INFO_ROLL, "0");
		st_result = "marks:" + roll+":"+marks;
		//Toast.makeText(getApplicationContext(), st_result, 1).show();
		//Toast.makeText(getApplicationContext(), ""+status_send_result, 0).show();
		//JoinTestListener();
		Intent intent = new Intent(StudentDashboard.this,Result.class);
		intent.putExtra("st_roll", roll );
		intent.putExtra("st_per", ""+per * 100);
		intent.putExtra("outoff", m);
		startActivity(intent);
		finish();
		
	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {
        	EvaluateTest();
        }
        return super.onOptionsItemSelected(item);
    }
	
	 @Override
	public void onDestroy() {
	  super.onDestroy();

	  if (socket != null) {
	   try {
	    socket.close();
	    Log.d("Student FR", "socket closed");
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    Log.d("student FR", "fail to close socket");
	   }
	  }
	  if (socket_send != null) {
		   try {
		    socket_send.close();
		    Log.d("student FR", "socket closed");
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    Log.d("Student FR", "fail to close socket");
		   }
		  }
		  
	 }

	/////////// 
	public class MyClientTask extends AsyncTask<String	,String,String> {
	  
	  String dstAddress;
	  int dstPort;
	  String response = "";
	  
	  MyClientTask(String addr, int port){
	   dstAddress = addr;
	   dstPort = port;
	  }

	  @Override
	  protected String doInBackground(String... arg0) {
	   
		  
		  Log.d("Student", "in background "+dstAddress+" "+dstPort);
		  if(socket != null){
			  socket = null;
		  }
		  
		  try {
			  Log.d("Student", "client socket creating...");
			  socket = new Socket(dstAddress, dstPort);
			  Log.d("Student", "client socket created");
			  ByteArrayOutputStream byteArrayOutputStream = 
					  	new ByteArrayOutputStream(1024);
			  byte[] buffer = new byte[1024];
			  
			  int bytesRead;
			  InputStream inputStream = socket.getInputStream();
		
             while ((bytesRead = inputStream.read(buffer)) != -1){
                 byteArrayOutputStream.write(buffer, 0, bytesRead);
                 response += byteArrayOutputStream.toString("UTF-8");
             }
             
             			  
		  } catch (UnknownHostException e) {
			  e.printStackTrace();
			  response = "UnknownHostException: " + e.toString();
			  Log.d("Student", "UnknownHostException");
		  } catch (Exception e) {
			  e.printStackTrace();
			  response = "IOException: " + e.toString();
			  Log.d("Student", "IOException");
		  }finally{
			  Log.d("Student", "failed");
			  if(socket != null){
				  try {
					  socket.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
		  }
	   return response;
	  }

	  @Override
	  protected void onPostExecute(String result) {
	   //Toast.makeText(getApplicationContext(), result, 1).show();
	   
	   ShowQuestionsInList(result);
		  //textResponse.setText(response);
	   super.onPostExecute(result);
	  }
	  
	 }
	
	
	public class MyClientTask_SENDRES extends AsyncTask<String	,String,String> {
		
		  String dstAddress;
		  int dstPort;
		  String response = "";
		  String stu_result="";
		  
		  MyClientTask_SENDRES(String addr, int port,String r){
		   dstAddress = addr;
		   dstPort = port;
		   stu_result = r;
		  }

		  @Override
		  protected String doInBackground(String... arg0) {
			  
			  Log.d("Student send", "in background send "+dstAddress+" "+dstPort);
			  if(socket_send != null){
				  socket_send = null;
			  }
			  try {
				  socket = null;
				  Log.d("Student send", "client socket creating...");
				  socket_send = new Socket(dstAddress, dstPort);
				  Log.d("Student send", "client socket created");
				  /*ByteArrayOutputStream byteArrayOutputStream = 
						  	new ByteArrayOutputStream(1024);
				  byte[] buffer = new byte[1024];
		    
				  buffer = stu_result.getBytes();
				  */
				  ////////////////
				  OutputStream outputStream = null;
				   		
				   try {
					    outputStream = socket_send.getOutputStream();
					             PrintStream printStream = new PrintStream(outputStream);
					             printStream.print(stu_result);
					             printStream.close();
					
					    			
				   } catch (IOException e) {
				    Log.d("student send", "er");
				    e.printStackTrace();
				    //message += "Something wrong! " + e.toString() + "\n";
				   }
				  
				  
				  //////////
				  
				  /*int bytesRead;
				  InputStream inputStream = socket.getInputStream();
		    
		             while ((bytesRead = inputStream.read(buffer)) != -1){
		                 byteArrayOutputStream.write(buffer, 0, bytesRead);
		                 response += byteArrayOutputStream.toString("UTF-8");
		             }*/
			  } catch (UnknownHostException e) {
				  e.printStackTrace();
				  //response = "UnknownHostException: " + e.toString();
				  Log.d("Student", "UnknownHostException");
			  } catch (Exception e) {
				  e.printStackTrace();
				  //response = "IOException: " + e.toString();
				  Log.d("Student", "IOException");
			  }finally{
				  Log.d("Student send", "failed");
				  if(socket_send != null){
					  try {
						  socket_send.close();
					  } catch (IOException e) {
						  e.printStackTrace();
					  }
				  }
			  }
		   return response;
		  }

		  @Override
		  protected void onPostExecute(String result) {
		   //Toast.makeText(getApplicationContext(), result, 1).show();
		   
		   //ShowQuestionsInList(result);
			  //textResponse.setText(response);
		   super.onPostExecute(result);
		  }
		  
		 }

	
	
	/////////////////listView
    class MyAdapter extends BaseAdapter {

    	Context context;
    	
    	public MyAdapter(Context c) {
    		this.context = c;
    	}
    	
		@Override
		public int getCount() {
			return queList.size();
		}

		@Override
		public Object getItem(int position) {
			return queList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater)
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.question_view, parent,false);
			
			TextView tv_que = (TextView) row.findViewById(R.id.tv_que_s);
			final RadioButton rb_a = (RadioButton) row.findViewById(R.id.rb_opt_a_s);
			final RadioButton rb_b = (RadioButton) row.findViewById(R.id.rb_opt_b_s);
			final RadioButton rb_c = (RadioButton) row.findViewById(R.id.rb_opt_c_s);
			final RadioButton rb_d = (RadioButton) row.findViewById(R.id.rb_opt_d_s);
			
			rb_a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(rb_a.isChecked()){
						rb_a.setChecked(true);
						queList.get(position).setS_ans("a");
					}
				}
			});
			rb_b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(rb_b.isChecked()){
						rb_b.setChecked(true);
						queList.get(position).setS_ans("b");
					}
				}
			});
			rb_c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(rb_c.isChecked()){
						rb_c.setChecked(true);
						queList.get(position).setS_ans("c");
					}
				}
			});
			rb_d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(rb_d.isChecked()){
						rb_d.setChecked(true);
						queList.get(position).setS_ans("d");
					}
				}
			});
			
			
			if( !queList.isEmpty()){
				tv_que.setText(queList.get(position).getQuestion());
				rb_a.setText(queList.get(position).getOption_a());
				rb_b.setText(queList.get(position).getOption_b());
				rb_c.setText(queList.get(position).getOption_c());
				rb_d.setText(queList.get(position).getOption_d());
				
			}else{
				//Toast.makeText(context, "ds", 1).show();
			}
			return row;
		}
    	
    }

}
