package dw.wifi.temperature;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

public class TempReciveHandler extends Thread{
	static  boolean run_flags = true;
	TempConnect TempHandler = null;
	EditText DisplayText;
    public void run()  
    {  
    	
    	while(run_flags)
    	{   
    		try {
    			Thread.sleep(500);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		mHandler.sendEmptyMessage(1);
    	}
    }
    @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
	        // 重写handleMessage()方法，此方法在UI线程运行
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case 1:
	            		if(!run_flags)
	            		{
	            			DisplayText.setText("         ");
	            		}
	            		else
	            		{
	            			DisplayText.setText(TempHandler.get_line() + " ℃");
	            		}
	            	break;
	            }
	        }
	    };
   	 public void init_para(TempConnect tempHandler, EditText dispkayText) {
   		TempHandler = tempHandler;	
   		DisplayText = dispkayText;
   		run_flags = true;
 	}
   	public void stop_run() {
		if(run_flags == true){
			run_flags = false;
		}	
	}
}
