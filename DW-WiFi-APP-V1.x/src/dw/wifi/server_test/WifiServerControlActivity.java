package dw.wifi.server_test;

import java.io.BufferedReader;
import java.io.IOException;

import dw.wifi.common.HeartBeatThread;
import dw.wifi.main.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WifiServerControlActivity extends Activity {
	private ToggleButton toggleLedBtn, toggleBeepBtn;
	private ImageView imageLedView, imageBeepView;
	HeartBeatThread myThread = null;
	static BufferedReader reader = null;
	static String line;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servercontrol);
				
		myThread = new HeartBeatThread();
		Bundle bundle = this.getIntent().getExtras();
		String cmd = bundle.getString("reg_cmd");
//		System.out.println(cmd);
		myThread.user_register(cmd, WifiServerControlActivity.this);
		myThread.start();
//		myThread.connect(WifiServerActivity.this);
//		ServerConnect.connect(WifiServerActivity.this);		
//		ServerConnect.user_register(cmd, WifiServerActivity.this);
		
		imageLedView = (ImageView) findViewById(R.id.imageLedView);
		imageBeepView = (ImageView) findViewById(R.id.imageBeepView);
		
		toggleLedBtn = (ToggleButton) findViewById(R.id.toggleLedBtn);
		toggleBeepBtn = (ToggleButton) findViewById(R.id.toggleBeepBtn);
		
		toggleLedBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		toggleBeepBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		if(myThread == null){
			System.exit(0); 
		}
		else{
			Toast.makeText(WifiServerControlActivity.this,"服务器已连接", Toast.LENGTH_SHORT).show(); 
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("超时.");
			}
			
//			Toast.makeText(WifiServerActivity.this,"注册成功", Toast.LENGTH_SHORT).show(); 
			
		}
		reader = myThread.get_reader();
		Thread th = new Thread(new Runnable() { 

            @Override 
            public void run() { 
            	while(true){
            		if(myThread.get_state()){
						try {
							line = reader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(1);
					}
					else{
						break;
					}
            	}
            } 
        }); 
        th.start(); 
	}
	 @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
	        // 重写handleMessage()方法，此方法在UI线程运行
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case 1:
	            	if(line.equals("No find sn.")){
	            		toggleLedBtn.setEnabled(false);
	            		toggleBeepBtn.setEnabled(false);
	            		Toast.makeText(WifiServerControlActivity.this,"未发现的SN号码", Toast.LENGTH_SHORT).show();
	            	}
	            	if(line.equals("User connect seccess!")){
	            		toggleLedBtn.setEnabled(true);
	            		toggleBeepBtn.setEnabled(true);
	            		Toast.makeText(WifiServerControlActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
	            	}
	                break;
	            }
	        }
	    };
	class ToggleButtonCheckedChangeEvent implements
			ToggleButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (buttonView == toggleLedBtn) {
				if (isChecked) {
					myThread.Led_On();
					imageLedView.setImageResource(R.drawable.ledlight);
				} else {
					myThread.Led_Off();
					imageLedView.setImageResource(R.drawable.ledclose);
				}
			}
			else if (buttonView == toggleBeepBtn) {
				if (isChecked) {
					myThread.Beep_On();
					imageBeepView.setImageResource(R.drawable.beepopen);
				} else {
					myThread.Beep_Off();
					imageBeepView.setImageResource(R.drawable.beepclose);
				}
			}
		}

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myThread.Unconnect();
		Toast.makeText(WifiServerControlActivity.this,"服务器已断开", Toast.LENGTH_SHORT).show(); 
		System.out.println("关闭连接.");
		  
	}
}
