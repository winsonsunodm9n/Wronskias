package dw.wifi.main;

import dw.wifi.config.WiFiConfigActivity;
import dw.wifi.fast_test.WIfiFastTestActivity;
import dw.wifi.server_test.WifiServerConnectActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends Activity {

	private ImageButton imageFastBtn, imageConfigBtn, imageServerBtn;
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		imageFastBtn = (ImageButton) findViewById(R.id.imageFastBtn);
		imageConfigBtn = (ImageButton) findViewById(R.id.imageConfigBtn);
		imageServerBtn = (ImageButton) findViewById(R.id.imageServerBtn);
		imageFastBtn.getBackground().setAlpha(0);//0~255透明度值
		imageConfigBtn.getBackground().setAlpha(0);//0~255透明度值
		imageServerBtn.getBackground().setAlpha(0);//0~255透明度值
		
		imageFastBtn.setOnClickListener(new ButtonClickEvent());
		imageConfigBtn.setOnClickListener(new ButtonClickEvent());
		imageServerBtn.setOnClickListener(new ButtonClickEvent());
		imageFastBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.fasttest_l_b));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.fasttest_l));     
		            }  
				return false; 
			}       
		});
		imageConfigBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.config_l_b));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.config_l));     
		            }  
				return false; 
			}       
		});
		imageServerBtn.setOnTouchListener(new View.OnTouchListener(){            
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.servertest_l_b));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.servertest_l));     
		            }  
				return false; 
			}       
		});
	}
	// ----------------------------------------------------清除按钮、发送按钮
	class ButtonClickEvent implements View.OnClickListener {
		public void onClick(View v) {

			if(v == imageFastBtn){
				Intent intent = new Intent(LoginActivity.this,WIfiFastTestActivity.class);  
                startActivity(intent);				
			}
			else if(v == imageConfigBtn){
				Intent intent = new Intent(LoginActivity.this,WiFiConfigActivity.class);  
                startActivity(intent);				
			}
			else if(v == imageServerBtn){
				Intent intent = new Intent(LoginActivity.this,WifiServerConnectActivity.class);  
                startActivity(intent);				
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		System.exit(0);
	}
}
