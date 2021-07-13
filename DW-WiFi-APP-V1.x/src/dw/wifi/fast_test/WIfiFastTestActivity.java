package dw.wifi.fast_test;

import dw.wifi.common.Esp8266Connect;
import dw.wifi.config.WiFiConfigActivity;
import dw.wifi.main.LoginActivity;
import dw.wifi.main.R;
import dw.wifi.server_test.WifiServerConnectActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WIfiFastTestActivity extends Activity {
	private ToggleButton toggleLedBtn, toggleBeepBtn, toggleTestBtn;
	private ImageButton imageLedBtn, imageBeepBtn;
	private ImageView imageLedView, imageBeepView;
	Esp8266Connect cfgThread = null;
	public Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fasttest);
		
		toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
		
		cfgThread = new Esp8266Connect();
		cfgThread.recive_para(WIfiFastTestActivity.this);
		cfgThread.start();
		
		imageLedBtn = (ImageButton) findViewById(R.id.imageLedBtn);
		imageBeepBtn = (ImageButton) findViewById(R.id.imageBeepBtn);
		imageLedBtn.getBackground().setAlpha(0);//0~255透明度值
		imageBeepBtn.getBackground().setAlpha(0);//0~255透明度值
		
		imageLedBtn.setOnClickListener(new ButtonClickEvent());
		imageBeepBtn.setOnClickListener(new ButtonClickEvent());
		
		toggleLedBtn = (ToggleButton) findViewById(R.id.toggleLedBtn);
		toggleBeepBtn = (ToggleButton) findViewById(R.id.toggleBeepBtn);
		
		imageLedView = (ImageView) findViewById(R.id.imageLedView);
		imageBeepView = (ImageView) findViewById(R.id.imageBeepView);
		
		toggleLedBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		toggleBeepBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(cfgThread.connect_state()){
			toggleLedBtn.setEnabled(true);
			toggleBeepBtn.setEnabled(true);
			toast.setText("连接wifi成功");
			toast.show(); 
		}
		else{
			toggleLedBtn.setEnabled(false);
			toggleBeepBtn.setEnabled(false);
			toast.setText("连接失败 请重新尝试");
			toast.show(); 
		}
		
		imageLedBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ledlight));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ledclose));     
		            }  
				return false; 
			}       
		});
		imageBeepBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepopen));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepclose));     
		            }  
				return false; 
			}       
		});
	}
	// ----------------------------------------------------清除按钮、发送按钮
	class ButtonClickEvent implements View.OnClickListener {
			public void onClick(View v) {

				if(v == imageLedBtn){
					cfgThread.Led_On();
				}
				else if(v == imageBeepBtn){
				}
			}
		}
	class ToggleButtonCheckedChangeEvent implements
	ToggleButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (buttonView == toggleLedBtn) {
				if (isChecked) {
					cfgThread.Led_On();
					imageLedView.setImageResource(R.drawable.ledlight);
				} else {
					cfgThread.Led_Off();
					imageLedView.setImageResource(R.drawable.ledclose);
				}
			}
			else if (buttonView == toggleBeepBtn) {
				if (isChecked) {
					cfgThread.Beep_On();
					imageBeepView.setImageResource(R.drawable.beepopen);
				} else {
					cfgThread.Beep_Off();
					imageBeepView.setImageResource(R.drawable.beepclose);
				}
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(cfgThread.connect_state()){
			cfgThread.unconnect();
		}
	}

}
