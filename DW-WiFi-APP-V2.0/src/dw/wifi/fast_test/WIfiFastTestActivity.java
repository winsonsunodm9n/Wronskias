package dw.wifi.fast_test;

import dw.wifi.common.Esp8266Connect;
import dw.wifi.main.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class WIfiFastTestActivity extends Activity {
	private ImageButton imageLedBtn, imageBeepBtn;
	Esp8266Connect cfgThread = null;
	public Toast toast;
	private boolean LedBtnFlag = false, BeepBtnFlag = false;
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
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(cfgThread.connect_state()){
			imageBeepBtn.setEnabled(true);
			imageLedBtn.setEnabled(true);
			toast.setText("连接wifi成功");
			toast.show(); 
		}
		else{
			imageBeepBtn.setEnabled(false);
			imageLedBtn.setEnabled(false);
			toast.setText("连接失败 请重新尝试");
			toast.show(); 
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
						cfgThread.Led_On();
					   //重新设置按下时的背景图片  
						((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ledlight));
					}
					else
					{
						cfgThread.Led_Off();
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
							cfgThread.Beep_On();
						   //设置开启时的图片  
							((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepopen));
						}
						else
						{
							cfgThread.Beep_Off();
							((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beepclose));
						}     
		            }  
				return false; 
			}       
		});
	}
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
		if(cfgThread.connect_state()){
			cfgThread.unconnect();
		}
	}

}
