package com.hari.makepairs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
//import android.os.Vibrator;

public class SplashActivity extends Activity {
	CountDownTimer cdCountDownTimer;int TotalTime=6000,sec=1000;
//	ImageView _iv_counter;
	TextView _tv_counter;
//	int ctr[]={R.drawable.loading1,R.drawable.loading2,R.drawable.loading3};
	int i=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.splash);
//    	_iv_counter=(ImageView)findViewById(R.id.iv_counter);
    	_tv_counter=(TextView)findViewById(R.id.tv_counter);
    	//**************************
		try{
			cdCountDownTimer = new CountDownTimer(TotalTime, sec) {
			 long now = 0, secs, mins;

			 public void onTick(long millisUntilFinished) {
				 now = millisUntilFinished / sec;
				 secs = now % 60;mins = now/60;
//				 mTxtField.setTextColor(Color.GREEN);
//				 _tv_counter.setText(""+formatDigits(mins)+":"+formatDigits(secs));
				 if(i>2)i=0;
				 if(i==0)_tv_counter.setText(".");
				 else if(i==1)_tv_counter.setText("..");
				 else if(i==2)_tv_counter.setText("...");
				 i++;
//			    	  _iv_counter.setBackgroundResource(ctr[i]);
//			    	  if(i==ctr.length-1)i=0;else i++;
//			     millis_cdt=millisUntilFinished;
			 }
			 
			      public void onFinish() {
//			    	  mTxtField.setTextColor(Color.RED);
//					     if(!((Activity) context).isFinishing())
//					     {
//					    	 gameOverDialog();//
					    	            if(!isFinishing()){
					    					 _tv_counter.setText("...");
					    	    			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
					    	    	        startActivity(intent);finish();
//					  			    	  _iv_counter.setBackgroundResource(ctr[i]);
							                }
					    	            }
			   }.start();
 }catch(Exception ex){ex.printStackTrace();}

    	//**************************
//    	Thread countdownSplash = new Thread() {
//    		@Override 
//    		public void run() {
//    			try {
////    				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//    			
////    				v.vibrate(500);
//    				sleep(4500);
//    				
//				} catch (InterruptedException e) {
//					// do nothing
//				}
////    			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
////    	        startActivity(intent);finish();
//    		};
//    	};
//    	countdownSplash.start();
    }//endoncrea
}