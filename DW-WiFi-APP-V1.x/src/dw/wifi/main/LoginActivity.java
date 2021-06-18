package dw.wifi.main;

import dw.wifi.config.WiFiConfigActivity;
import dw.wifi.fast_test.WIfiFastTestActivity;
import dw.wifi.server_test.WifiServerConnectActivity;
import dw.wifi.temperature.TempTestActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button btnServerTest;
	private Button btnWifiCfg;
	private Button btnFastTest;
	private Button btnTempTest;
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btnServerTest = (Button) findViewById(R.id.btnServerTest);
		btnWifiCfg = (Button) findViewById(R.id.btnWifiCfg);
		btnFastTest = (Button) findViewById(R.id.btnFastTest);	
//		btnTempTest = (Button) findViewById(R.id.btnTempTest);
		
		btnServerTest.setOnClickListener(new ButtonClickEvent());
		btnWifiCfg.setOnClickListener(new ButtonClickEvent());
		btnFastTest.setOnClickListener(new ButtonClickEvent());
//		btnTempTest.setOnClickListener(new ButtonClickEvent());
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
//			else if(v == btnTempTest){
//				Intent intent = new Intent(LoginActivity.this,TempTestActivity.class);  
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
