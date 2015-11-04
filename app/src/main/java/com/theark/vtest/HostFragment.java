package com.theark.vtest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HostFragment extends FragmentA {


	public EditText et_que,et_opt_a,et_opt_b,et_opt_c,et_opt_d,et_ans;
	Button bt_add,bt_saveque,bt_showque,bt_sendque;
	
	ArrayList<Question> queList ;
	
	ServerSocket serverSocket;
	
	String message="";	
	
	//get the results
	String mes = "";
	
	JSONArray jsonQueArray;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View hostView = inflater.inflate(R.layout.host_fragment, container, false);
		
		init(hostView);
		
		AddQuestion();
		ShowQuestions();
		SendQueToStudent();
		
		return hostView;
	}

	 @Override
	public void onDestroy() {
	  super.onDestroy();

	  if (serverSocket != null) {
	   try {
	    serverSocket.close();
	    Log.d("TEACHER FR", "socket closed");
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    Log.d("TEACHER FR", "fail to close socket");
	   }
	  }
	 }

	private void init(View hostView) {
		et_que = (EditText) hostView.findViewById(R.id.et_que_t);
		et_opt_a = (EditText) hostView.findViewById(R.id.et_opt_a_t);
		et_opt_b = (EditText) hostView.findViewById(R.id.et_opt_b_t);
		et_opt_c = (EditText) hostView.findViewById(R.id.et_opt_c_t);
		et_opt_d = (EditText) hostView.findViewById(R.id.et_opt_d_t);
		et_ans = (EditText) hostView.findViewById(R.id.et_ans_t);
		bt_add = (Button) hostView.findViewById(R.id.bt_add_que);
		//bt_saveque = (Button) hostView.findViewById(R.id.bt_saveque_t);
		bt_showque = (Button) hostView.findViewById(R.id.bt_showque_t);
		bt_sendque = (Button) hostView.findViewById(R.id.bt_send_t);
		
		queList = new ArrayList<Question>();
		jsonQueArray = new JSONArray();
	}
	
	private void AddQuestion() {
		bt_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Question que = new Question();
				
				que.setQuestion(et_que.getText().toString());
				que.setOption_a(et_opt_a.getText().toString());
				que.setOption_b(et_opt_c.getText().toString());
				que.setOption_c(et_opt_c.getText().toString());
				que.setOption_d(et_opt_d.getText().toString());
				que.setAnswer(et_ans.getText().toString());
				
				queList.add(que);
				//Toast.makeText(getActivity(), "Question Added!!", Toast.LENGTH_SHORT).show();
				
				JSONObject obj = new JSONObject();
				try {
					obj.put("question", et_que.getText().toString());
					obj.put("option_a", et_opt_a.getText().toString());
					obj.put("option_b", et_opt_b.getText().toString());
					obj.put("option_c", et_opt_c.getText().toString());
					obj.put("option_d", et_opt_d.getText().toString());
					obj.put("answer", et_ans.getText().toString());
					jsonQueArray.put(obj);
				} catch (JSONException e) {
					Log.d("Teacher HostFrag", "JSON Exep");
					e.printStackTrace();
				}

				et_que.setText(""); et_ans.setText("");et_opt_a.setText("");
				et_opt_b.setText("");et_opt_c.setText("");et_opt_d.setText("");
				
				Log.d("######", "added");
				//Toast.makeText(getApplicationContext(), "dfg", 1).show();
			}
		});
		
	}
	
	private void ShowQuestions() {
		bt_showque.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String qs="";
				for(Question qq : queList){
					qs += qq.getQuestion() +"\na) " +qq.getOption_a()+"\nb) " +qq.getOption_b()
							+"\nc) " +qq.getOption_c()+"\nd)" +qq.getOption_d()
							+"\nAns: " +qq.getAnswer() +"\n-----------\n";
				}
				final Dialog dd = new Dialog(getActivity());
				dd.setContentView(R.layout.que_list);
				dd.setCancelable(true);
				((TextView) dd.findViewById(R.id.tv_q)).setMovementMethod(new ScrollingMovementMethod());
				((TextView) dd.findViewById(R.id.tv_q)).setText(qs);
				dd.findViewById(R.id.bt_close).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dd.dismiss();
					}
				});
				dd.show();
			}
		});
	}

	private void SendQueToStudent() {
		bt_sendque.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), ""+jsonQueArray.toString(), 1).show();
				Dialog d = new Dialog(getActivity());
				d.setContentView(R.layout.sendtestlay_dialod);
				d.setCancelable(true);
				TextView tv_ip = (TextView) d.findViewById(R.id.tv_IPAdr);
				tv_ip.setText("IP: "+getIpAddress());
				final EditText et_testID = (EditText) d.findViewById(R.id.et_testID_t);
				Button bt_post = (Button) d.findViewById(R.id.bt_post);
				bt_post.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//add to prev tests list
						if(!et_testID.getText().toString().equals(""))
						{
							SharedPreferences sp = getActivity().getSharedPreferences("com.theark.vtest.prevtests", 1);
							Editor e = sp.edit();
							e.putString(et_testID.getText().toString(), getQuestionsList());
							String tid = sp.getString("tids", "");
							if(!tid.contains(et_testID.getText().toString())){
								tid += et_testID.getText().toString() + "*";
								e.putString("tids", tid);
								//Toast.makeText(getActivity(), "once", 1).show();

							}
							e.commit();
							
						  Thread socketServerThread = new Thread(new SocketServerThread());
						  Log.d("post_que", "server started");
						  socketServerThread.start();
						  Toast.makeText(getActivity(), "Test is Online", 1).show();
						}else {
							Toast.makeText(getActivity(), "Enter Test ID", 1).show();

						}
					}
				});
				
				d.show();
			}
		});
	}
	
	private String getQuestionsList() {
		/*String q = "hey!!";
		for(Question qq : queList){
			if( ! qq.getQuestion().equals(null))
				q += qq.getQuestion()+"\n";
		}*/
		Log.d("JSON", jsonQueArray.toString());
		return jsonQueArray.toString();
	}

	
	
	////////networking
	 private String getIpAddress() {
		  String ip = "";
		  try {
		   Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
		     .getNetworkInterfaces();
		   while (enumNetworkInterfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = enumNetworkInterfaces
		      .nextElement();
		    Enumeration<InetAddress> enumInetAddress = networkInterface
		      .getInetAddresses();
		    while (enumInetAddress.hasMoreElements()) {
		     InetAddress inetAddress = enumInetAddress.nextElement();

		     if (inetAddress.isSiteLocalAddress()) {
		      ip += "SiteLocalAddress: " 
		        + inetAddress.getHostAddress() + "\n";
		     }
		     
		    }

		   }

		  } catch (SocketException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   ip += "Something Wrong! " + e.toString() + "\n";
		  }

		  return ip;
		 }
	 
	 
	 
	 private class SocketServerReplyThread extends Thread {
			
		  private Socket hostThreadSocket;
		  int cnt;
		
		  SocketServerReplyThread(Socket socket, int c) {
		   hostThreadSocket = socket;
		   cnt = c;
		  }
		
		  @Override
		  public void run() {
			   OutputStream outputStream;
			   //InputStream is;
			   String msgReply = getQuestionsList();
			
			   try {
				    outputStream = hostThreadSocket.getOutputStream();
				             PrintStream printStream = new PrintStream(outputStream);
				             printStream.print(msgReply);
				             printStream.close();
				             Log.d("got", "innnnn");
		
			   } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			    //message += "Something wrong! " + e.toString() + "\n";
			   }
			
		  }

		

	 }
	 
	 
	 private class SocketServerThread extends Thread {
			
		  static final int SocketServerPORT = 2489;
		  int count = 0;
		
		  @Override
		  public void run() {
			   try {
				    serverSocket = new ServerSocket(SocketServerPORT);
				    
			    	 while (true) {
				    	Socket socket = serverSocket.accept();
				    	count++;
				    	message += "#" + count + " from " + socket.getInetAddress()
				    			+ ":" + socket.getPort() + "\n";
				
				    	/*if(socket.getInputStream() !=null){
							Scanner in1 = new Scanner(socket.getInputStream());
							
							while(in1.hasNext()){
								
								
									mes=in1.nextLine();
									Log.d("result_t", ""+mes);
								
							}
							Log.d("result_t", "not null" + mes);
							
					    	SharedPreferences spsp = getActivity().getSharedPreferences("res", 0);
					    	Editor eded = spsp.edit();
					    	eded.putString("result", ""+mes);
					    	eded.commit();
				    	
				    	}*/
				    	

				    	
				    	Log.d("akshaykale", ""+message);
				    	
				    	SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
				    			socket, count);
				    	socketServerReplyThread.run();
				
				    	
				    	
				   	}
				  } catch (IOException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
			   }
		  }

	 }
	 
	 
	 
	 
	    private class ClientTask implements Runnable {
	        private final Socket clientSocket;

	        private ClientTask(Socket clientSocket) {
	            this.clientSocket = clientSocket;
	        }

	        @Override
	        public void run() {
	            System.out.println("Got a client !");
	            try {
	            /* Get Data From Client */
	                BufferedReader reader = new BufferedReader(
	                        new InputStreamReader(clientSocket.getInputStream()));
	                String clientData = "";
	                clientData = reader.readLine();
	                System.out.println("Data From Client :" + clientData);

	            /* Send Data To Client */

	                //Code

	                clientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
}