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
	
	private Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fasttest);
		
		cfgThread = new Esp8266Connect();
		cfgThread.recive_para(WIfiFastTestActivity.this);
		cfgThread.start();
		toggleTestBtn = (ToggleButton) findViewById(R.id.toggleTestBtn);
		toggleLedBtn = (ToggleButton) findViewById(R.id.toggleLedBtn);
		toggleBeepBtn = (ToggleButton) findViewById(R.id.toggleBeepBtn);
		
		imageLedView = (ImageView) findViewById(R.id.imageLedView);
		imageBeepView = (ImageView) findViewById(R.id.imageBeepView);
		
		toggleLedBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		toggleBeepBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		toggleTestBtn.setOnCheckedChangeListener(new ToggleButtonCheckedChangeEvent());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(cfgThread.connect_state()){
			toggleLedBtn.setEnabled(true);
			toggleBeepBtn.setEnabled(true);
			Toast.makeText(WIfiFastTestActivity.this,"连接wifi成功", Toast.LENGTH_SHORT).show(); 
		}
		else{
			toggleLedBtn.setEnabled(false);
			toggleBeepBtn.setEnabled(false);
			Toast.makeText(WIfiFastTestActivity.this,"连接失败 请重新尝试", Toast.LENGTH_SHORT).show(); 
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
			else if (buttonView == toggleTestBtn) {
				if (isChecked) {
//					Toast.makeText(WIfiFastTestActivity.this, "Test On", Toast.LENGTH_SHORT).show();
					toast=Toast.makeText(WIfiFastTestActivity.this, "Toast在其他", Toast.LENGTH_SHORT); 
					toast.show(); 
				} else {
//					Toast.makeText(getApplicationContext(), "Test Off", Toast.LENGTH_SHORT).show();
					toast=Toast.makeText(WIfiFastTestActivity.this, "Test Off", Toast.LENGTH_SHORT); 
					toast.show(); 
//					toast.setText("");
//			        toast.setDuration(duration);
//			        toast.show();
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
