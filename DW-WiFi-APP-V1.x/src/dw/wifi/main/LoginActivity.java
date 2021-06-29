package dw.wifi.main;

import dw.wifi.config.WiFiConfigActivity;
import dw.wifi.fast_test.WIfiFastTestActivity;
import dw.wifi.server_test.WifiServerConnectActivity;
import dw.wifi.temperature.TempTestActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends Activity {

	private Button btnServerTest;
	private Button btnWifiCfg;
	private Button btnFastTest;
	private Button btnTempTest;
//	private ImageButton imageFastBtn;
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btnServerTest = (Button) findViewById(R.id.btnServerTest);
		btnWifiCfg = (Button) findViewById(R.id.btnWifiCfg);
		btnFastTest = (Button) findViewById(R.id.btnFastTest);	
//		imageFastBtn = (ImageButton) findViewById(R.id.imageFastBtn);
		
		btnServerTest.setOnClickListener(new ButtonClickEvent());
		btnWifiCfg.setOnClickListener(new ButtonClickEvent());
		btnFastTest.setOnClickListener(new ButtonClickEvent());
//		imageFastBtn.setOnClickListener(new ButtonClickEvent());
//		imageFastBtn.setOnTouchListener(new View.OnTouchListener(){            
//		    public boolean onTouch(View v, MotionEvent event) {               
//		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
//		               //重新设置按下时的背景图片  
//		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.android_btn_pressed));                              
//		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
//		                //再修改为抬起时的正常图片  
//		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.android_btn));     
//		            }  
//		            return false;       
//		    }       
//		});
	}
	// ----------------------------------------------------清除按钮、发送按钮
	class ButtonClickEvent implements View.OnClickListener {
		public void onClick(View v) {

			if (v == btnServerTest) {
				Intent intent = new Intent(LoginActivity.this,WifiServerConnectActivity.class);  
                startActivity(intent);	
			}
			else if(v == btnWifiCfg){
				Intent intent = new Intent(LoginActivity.this,WiFiConfigActivity.class);  
                startActivity(intent);				
			}
			else if(v == btnFastTest){
				Intent intent = new Intent(LoginActivity.this,WIfiFastTestActivity.class);  
                startActivity(intent);				
			}
//			else if(v == imageFastBtn){
//				Intent intent = new Intent(LoginActivity.this,WIfiFastTestActivity.class);  
//                startActivity(intent);				
//			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		System.exit(0);
	}
}
