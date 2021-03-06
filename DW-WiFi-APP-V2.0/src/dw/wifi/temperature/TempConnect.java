package dw.wifi.temperature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import android.content.Context;

public class TempConnect extends Thread{
	static String ServerIp = "192.168.4.1";
	static int ServerPort = 80;
	static Socket socket = null;
	static PrintWriter writer = null;
	static BufferedReader reader = null;
	static String register_cmd;
	Thread thread;
	static int count = 0;
	static String line;
	static  boolean connect_flags = false;
	static Context WsActivity;
	static int recive_count = 0;
    @Override  
    public void run()  
    {  
    	try {
			socket = new Socket(ServerIp, ServerPort);
			if(socket == null){
				System.out.println("连接失败.");
				connect_flags = false;
			}
			else
			{
				//writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				connect_flags = true;
			}		
		} catch (IOException e) {
			System.out.println("连接失败.");
		} 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	while(connect_flags)
    	{
    		if(!connect_flags)
    		{
    			//System.out.println("退出连接wifi模组进程.");
    			break;
    		}
    		try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
//    		if(line.equals("Led open OK")){
//    			mHandler.sendEmptyMessage(1);
//    		}
//    		 if(line.equals("Led close OK")){
//    			mHandler.sendEmptyMessage(2);
//    		}
    	}
    } 
	public void unconnect() {
		try {
			if(connect_flags == true){
				writer.close();
				reader.close();
				socket.close();
				connect_flags = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void Temp_On() {
		if(connect_flags == true){
			writer.print("$wifi$/D03/1\r\n");
			writer.flush();
		}
	}
	public void Temp_Off() {
		if(connect_flags == true){
			writer.print("$wifi$/D03/0\r\n");
			writer.flush();
		}
	}
	public boolean connect_state() {
			return connect_flags;			
	}
	public String get_line()
    {
		return line;
    }
}
