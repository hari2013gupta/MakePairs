package com.hari.makepairs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GameOver extends Activity {
//	MediaPlayer Audio;
	private String level = "diff1";
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.game_over);
	    	Bundle extras = getIntent().getExtras();
	    	
//	    	Audio = MediaPlayer.create(this, R.raw.mp_game_over);
//	        	Audio.start();

	        if (extras != null){	
	    		level = extras.getString("level");
	    	}
	    	final ImageView PlayAgain = (ImageView) findViewById(R.id.btn_playagain);
	    	final ImageView MainMenu = (ImageView) findViewById(R.id.btn_mainmenu);
////////////////////	    	
	    	PlayAgain.setOnClickListener(new View.OnClickListener() { 
	        	public void onClick(View v) {
	                Intent intent = new Intent (GameOver.this, MainActivityHari.class);
	                intent.putExtra("level", level);startActivity(intent);finish();
//	                Audio.stop();
	            }
	         });
	         
	         MainMenu.setOnClickListener(new View.OnClickListener() {
	        	 public void onClick(View v) {
		                Intent intent = new Intent (GameOver.this, MenuActivity.class);
		                startActivity(intent);finish();
//	        		 Audio.stop();
		         }
	         });
		    
	 }//endoncre
		@Override
		protected void onDestroy() {
			super.onDestroy();
//			Audio.release();
//			doUnbindService();
		}

	 @Override
	public void onBackPressed() {
         Intent intent = new Intent (GameOver.this, ModeActivity.class);
         startActivity(intent);  
         finish();
		 
		super.onBackPressed();
	}
}