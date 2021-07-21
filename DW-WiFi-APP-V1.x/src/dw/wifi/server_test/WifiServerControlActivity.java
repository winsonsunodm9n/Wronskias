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
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WifiServerControlActivity extends Activity {
	private ImageButton imageLedBtn, imageBeepBtn;
	private ToggleButton toggleLedBtn, toggleBeepBtn;
	private ImageView imageLedView, imageBeepView;
	HeartBeatThread myThread = null;
	static BufferedReader reader = null;
	static String line;
	private boolean LedBtnFlag = false, BeepBtnFlag = false;
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
		
		imageLedBtn = (ImageButton) findViewById(R.id.imageLedBtn);
		imageBeepBtn = (ImageButton) findViewById(R.id.imageBeepBtn);
		imageLedBtn.getBackground().setAlpha(0);//0~255透明度值
		imageBeepBtn.getBackground().setAlpha(0);//0~255透明度值
		
		imageLedBtn.setOnClickListener(new ButtonClickEvent());
		imageBeepBtn.setOnClickListener(new ButtonClickEvent());
		
//		imageLedView = (ImageView) findViewById(R.id.imageLedView);
//		imageBeepView = (ImageView) findViewById(R.id.imageBeepView);
//		
//		toggleLedBtn = (ToggleButton) findViewById(R.id.toggleLedBtn);
//		toggleBeepBtn = (ToggleButton) findViewById(R.id.toggleBeepBtn);
		
//		toggleLedBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
//		toggleBeepBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
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
		
		imageLedBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					//nothing 
				}else if(event.getAction() == MotionEvent.ACTION_UP){   
					LedBtnFlag = !LedBtnFlag;
					if(LedBtnFlag == true)
					{
						myThread.Led_On();
					   //重新设置按下时的背景图片  
						((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ledlight));
					}
					else
					{
						myThread.Led_Off();
						((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ledclose));
					}
				}  
				return false; 
			}       
		});
		imageBeepBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //nothing                             
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		            	BeepBtnFlag = !BeepBtnFlag;
						if(BeepBtnFlag == true)
						{
							myThread.Beep_On();
						   //设置开启时的图片  
							((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepopen));
						}
						else
						{
							myThread.Beep_Off();
							((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepclose));
						}     
		            }  
				return false; 
			}       
		});
		
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
	            		imageLedBtn.setEnabled(false);
	            		imageBeepBtn.setEnabled(false);
	            		Toast.makeText(WifiServerControlActivity.this,"未发现的SN号码", Toast.LENGTH_SHORT).show();
	            	}
	            	if(line.equals("User connect seccess!")){
	            		imageLedBtn.setEnabled(true);
	            		imageBeepBtn.setEnabled(true);
	            		Toast.makeText(WifiServerControlActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
	            	}
	                break;
	            }
	        }
	    };
//	class ToggleButtonCheckedChangeEvent implements
//			ToggleButton.OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			if (buttonView == toggleLedBtn) {
//				if (isChecked) {
//					myThread.Led_On();
//					imageLedView.setImageResource(R.drawable.ledlight);
//				} else {
//					myThread.Led_Off();
//					imageLedView.setImageResource(R.drawable.ledclose);
//				}
//			}
//			else if (buttonView == toggleBeepBtn) {
//				if (isChecked) {
//					myThread.Beep_On();
//					imageBeepView.setImageResource(R.drawable.beepopen);
//				} else {
//					myThread.Beep_Off();
//					imageBeepView.setImageResource(R.drawable.beepclose);
//				}
//			}
//		}
//
//	}
	class ButtonClickEvent implements View.OnClickListener {
		public void onClick(View v) {

			if(v == imageLedBtn){
				//nothing
			}
			else if(v == imageBeepBtn){
				//nothing
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
