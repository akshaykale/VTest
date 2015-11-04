package com.theark.vtest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultFragment extends Fragment {

	TextView lv_res;
	String mes = "";
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View viewResult = inflater.inflate(R.layout.result_fragment, container, false);
		
		init(viewResult);
		
		Toast.makeText(getActivity(), getIpAddress(), 1).show();
		
		try {
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lv_res.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lv_res.setText(mes);
			}
		});
		
		return viewResult;
	}

	private void init(View viewResult) {
		lv_res = (TextView) viewResult.findViewById(R.id.lv_result_t);
		lv_res.setMovementMethod(new ScrollingMovementMethod());
	}
	
	private void startServer() throws IOException {
		//Socket clientSocket = null;
		ServerSocket serverSocket = null;
		//String mes = "";
		try{
		serverSocket = new ServerSocket(5671); 
		System.out.println("server started....");
		
		while(true){
			Log.d("haha", "detected");
			Socket clientSocket = serverSocket.accept();
			Log.d("haha", "detected");
			if(clientSocket.getInputStream() !=null){
				Scanner in1 = new Scanner(clientSocket.getInputStream());
				
				while(in1.hasNext()){
					
					
						mes=in1.nextLine();
						Log.d("result_t", ""+mes);
					
				}
				Log.d("result_t", "not null");
			}
			lv_res.append("\n"+mes);
			Log.d("result_t", "msg" + mes);
		}
		
		
		}catch (Exception e) {
		    // handle exception
		} finally {
		    if (serverSocket != null) {
		        serverSocket.close();
		    }
		}
	}
	
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
}