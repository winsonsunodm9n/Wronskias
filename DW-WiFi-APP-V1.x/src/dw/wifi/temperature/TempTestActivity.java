package dw.wifi.temperature;

import dw.wifi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TempTestActivity extends Activity {
	private ToggleButton toggleTemperature;
	private EditText tempText;
	TempConnect cfgThread = null;
	TempReciveHandler tempThread = null;
	static int control_count = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temptest);
		
		control_count = 0;
		
		cfgThread = new TempConnect();
		cfgThread.start();
		
		toggleTemperature = (ToggleButton) findViewById(R.id.toggleTemperature);
		tempText = (EditText) findViewById(R.id.tempText);
		
		toggleTemperature.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(cfgThread.connect_state()){
			toggleTemperature.setEnabled(true);
			Toast.makeText(TempTestActivity.this,"连接wifi成功", Toast.LENGTH_SHORT).show(); 
		}
		else{
			toggleTemperature.setEnabled(false);
			Toast.makeText(TempTestActivity.this,"连接失败 请重新尝试", Toast.LENGTH_SHORT).show(); 
		}
	}        
	class ToggleButtonCheckedChangeEvent implements
	ToggleButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (buttonView == toggleTemperature) {
				if (isChecked) {
					cfgThread.Temp_On();
					if(control_count == 0)
					{
						control_count = 1;
						tempThread = new TempReciveHandler();
						tempThread.init_para(cfgThread, tempText);
						tempThread.start();
					}
					tempText.setText("        ");
					
				} else {
					cfgThread.Temp_Off();
					if(control_count == 1)
					{
						control_count = 0;
						tempThread.stop_run();
					}
					tempText.setText("        ");
				}
			}
			
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cfgThread.unconnect();
	}
}
