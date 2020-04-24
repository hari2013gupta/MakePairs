package com.hari.makepairs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
//import android.os.Vibrator;

public class HelpActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.help);
//    	_iv_counter=(ImageView)findViewById(R.id.iv_counter);
    }//endoncrea
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (getApplicationContext(), MenuActivity.class);
        startActivity(intent);finish();

    	// TODO Auto-generated method stub
    	super.onBackPressed();
    }
}