package dw.wifi.fast_test;

import dw.wifi.common.Esp8266Connect;
import dw.wifi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WIfiFastTestActivity extends Activity {
	private ToggleButton toggleLedBtn, toggleBeepBtn, toggleTestBtn;
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
