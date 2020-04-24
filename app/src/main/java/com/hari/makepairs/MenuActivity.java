package com.hari.makepairs;

import android.R.bool;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	public CountDownTimer cdt;
//    Button _Button3,_Button4,_Button5;
	Button _btn_play, _btn_rzone, _btn_help, _btn_about, _btn_sound; 
	String getURL;
	int backButtonCount = 0;
	boolean bool_sound=true;
	MediaPlayer mp;
//	,URL,bottomURL;View topAdd,bottomAdd;Handler handler;
//	ImageButton Rzone;
//	final int []imageAddArray={R.drawable.banner_ucweb,R.drawable.banner_olx,R.drawable.banner_freeapps,R.drawable.banner_topandroid,R.drawable.banner_dealslelo,R.drawable.banner_flipcart};
//	final int []imageBottomArray={R.drawable.banner_freeapps,R.drawable.banner_topandroid,R.drawable.banner_dealslelo,R.drawable.banner_olx,R.drawable.banner_flipcart,R.drawable.banner_ucweb};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        setContentView(R.layout.menu);
//***************Sound functionatliy     
   	 SharedPreferences s = getSharedPreferences("SFX", 0);
     bool_sound = s.getBoolean("SOUND", true);     // How many times we have loaded this app, we copy this to getViewCount
     
//        SharedPreferences.Editor editor = getSharedPreferences("com.pro.makepairs", MODE_PRIVATE).edit();
//        editor.putBoolean("sound", true);
//        editor.commit();
//        
//        SharedPreferences sharedPrefs = getSharedPreferences("com.example.xyle", MODE_PRIVATE);
        _btn_sound = (Button)findViewById(R.id.btn_settings);
        if(bool_sound == true)
        	_btn_sound.setBackgroundResource(R.drawable.btn_sound_on);
        else if(bool_sound == false) 
        	_btn_sound.setBackgroundResource(R.drawable.btn_sound_off);
//****************end sound functionality        
        mp=MediaPlayer.create(this, R.raw.mp_picking_coin);
///////////////////hari_banner
        
//----------------------hhari-----------
//topAdd=(View)findViewById(R.id.topBanner);
//
//bottomAdd=(View)findViewById(R.id.bottomBanner);
//
//		handler = new Handler();
//		
//       Runnable runnable = new Runnable() {
//         int j=0;
//         public void run() {
//      	   topAdd.setBackgroundResource(imageAddArray[j]);
//             bottomAdd.setBackgroundResource(imageBottomArray[j]);
//             	if(j==0){
//             		URL="http://down3.ucweb.com/ucbrowser/en/?bid=444&pub=tnj%40hariom-CASH&title=&url=&version=2";
//             		bottomURL="http://freeappstore.in/";
//             	}else if(j==1){
//             		URL="http://hasoffers.ymtrack.com/aff_c?offer_id=12422&aff_id=14788";
//             		bottomURL="http://appmyfriend.com/";
//             	}else if(j==2){
//             		URL="http://freeappstore.in/";
//             		bottomURL="http://www.dealslelo.com/";
//             	}else if(j==3){
//             		URL="http://appmyfriend.com/";
//             		bottomURL="http://hasoffers.ymtrack.com/aff_c?offer_id=12422&aff_id=14788";
//         		}else if(j==4){
//         			URL="http://www.dealslelo.com/";
//         			bottomURL="http://yeahmobi.go2cloud.org/aff_c?offer_id=15692&aff_id=14788";
//         			
//         		}else if(j==5){
//         			URL="http://yeahmobi.go2cloud.org/aff_c?offer_id=15692&aff_id=14788";
//               		bottomURL="http://down3.ucweb.com/ucbrowser/en/?bid=444&pub=tnj%40hariom-CASH&title=&url=&version=2";
//         			
//           	}
//          	j++;
//          	if(j>imageBottomArray.length-1){
//          	
//          		j=0;
//          	}
//              handler.postDelayed(this, 6000);  //for interval...
//          	System.out.println("--------j----//"+j);
//          }
//      };	            	
//      handler.postDelayed(runnable, 500); //for initial delay..
////_________harisep
//      
//      topAdd.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//
//				Context context=v.getContext();
//      		Intent thingyToInstall=new Intent(Intent.ACTION_VIEW);
//      		thingyToInstall.setDataAndType(Uri.parse(URL), null);
//      		context.startActivity(thingyToInstall);
//			}
//		});
//      
//    bottomAdd.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Context context=v.getContext();
//   		Intent thingyToInstall=new Intent(Intent.ACTION_VIEW);
//   		thingyToInstall.setDataAndType(Uri.parse(bottomURL), null);
//   		context.startActivity(thingyToInstall);
//		}
//	});

    _btn_play = (Button)findViewById(R.id.btn_play);
    _btn_play.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
    		      Intent intent=new Intent(getApplicationContext(), ModeActivity.class);
    	             intent.putExtra("bool_sound", bool_sound);
		      startActivity(intent);finish();if(bool_sound)mp.start();
			}
    	});

    _btn_rzone = (Button)findViewById(R.id.btn_rzone);
    _btn_rzone.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {

			Intent intent=new Intent(getApplicationContext(), MenuActivity.class);
		      startActivity(intent);finish();if(bool_sound)mp.start();
			}
    	});

    _btn_help = (Button)findViewById(R.id.btn_help);
    _btn_help.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(getApplicationContext(), HelpActivity.class);
		      startActivity(intent);finish();if(bool_sound)mp.start();
			}
    	});

    _btn_about = (Button)findViewById(R.id.btn_about);
    _btn_about.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(getApplicationContext(), AboutActivity.class);
		      startActivity(intent);finish();if(bool_sound)mp.start();
			}
    	});

    _btn_sound.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
//			mp.start();
//			 AlertCustomExit();
    		 SharedPreferences s = getSharedPreferences("SFX", 0);
    		 SharedPreferences.Editor edit = s.edit();
    		 
		    if (bool_sound==true) {
	      		 bool_sound=false;
	    		 _btn_sound.setBackgroundResource(R.drawable.btn_sound_off);
	    		 edit.putBoolean("SOUND", bool_sound);
	      		 edit.commit();

//		        SharedPreferences.Editor editor = getSharedPreferences("com.pro.makepairs", MODE_PRIVATE).edit();
//		        editor.putBoolean("SOUND", true);
//		        editor.commit();
		    }
		    else if(bool_sound == false){
	      		 bool_sound=true;
	    		 _btn_sound.setBackgroundResource(R.drawable.btn_sound_on);
	    		 if(bool_sound)mp.start();
	    		 edit.putBoolean("SOUND", bool_sound);
	      		 edit.commit();

//		        SharedPreferences.Editor editor = getSharedPreferences("com.pro.makepairs", MODE_PRIVATE).edit();
//		        editor.putBoolean("SOUND", false);
//		        editor.commit();
		    }
//			showAlert("Dear Users, If you like our app please give us Rating of 5 stars.");
		}
    });
//  	----------------------hhari-----------
  	        //for initial delay..

//////////////endbanner
//    _Button3 = (Button)findViewById(R.id.Button3x3);
//
//    _Button3.setOnClickListener(new OnClickListener() {
//  		
//  		@Override
//  		public void onClick(View v) {
//
//  			Intent Activity3 = new Intent(getApplicationContext(), 
//
//  					MainActivity3.class);
//  		      startActivity(Activity3);
//  		}
//
//    });
//
//    
//    _Button4 = (Button)findViewById(R.id.Button4x4);
//    
//    _Button4.setOnClickListener(new OnClickListener() {
//  		
//  		@Override
//  		public void onClick(View v) {
//  		    
//  			Intent Activity4 = new Intent(getApplicationContext(), 
//
//  					MainActivity4.class);
//  		      startActivity(Activity4);
//  		}
//  	
//    });
//
//    _Button5 = (Button)findViewById(R.id.Button5x5);
//    
//    
//    _Button5.setOnClickListener(new OnClickListener() {
//  		
//  		@Override
//  		public void onClick(View v) {
//  	
//  			Intent Activity5 = new Intent(getApplicationContext(), 
//
//  					MainActivity5.class);
//  		      startActivity(Activity5);
//  		}
//  	
//    });

////////hari_ex////////////////////////////
    
    }
    //hariJ_all_functiona=s
	public void showAlert(String msg){
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
    	//hari_exp
//      View title = getWindow().findViewById(android.R.id.custom);
//      View titleBar = (View) title.getParent();
//      titleBar.setBackgroundColor(Color.BLACK);
//      titleBar.setVisibility(View.VISIBLE);
  	//hari_exp_end_of the code
        builder1.setTitle("Mouse Maze");
    	builder1.setMessage(msg);
        builder1.setCancelable(true);
//        builder1.setIcon(R.drawable.ic_launcher);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
        		getURL="https://play.google.com/store/apps/details?id=com.appsd.mousemaze";
//        		Intent intent=new Intent(getApplicationContext(), getURL);
//    			startActivity(intent);  

        		Intent browserIntent = new Intent(
        				Intent.ACTION_VIEW, Uri.parse(getURL));
        		startActivity(browserIntent);//mp.stop();
//        		System.out.println("----------------exti---------------");//UC wb
//        		Context context=view.getContext();
//        		Intent thingyToInstall=new Intent(Intent.ACTION_VIEW);
//        		thingyToInstall.setDataAndType(Uri.parse(getURL), null);
//        		context.startActivity(thingyToInstall);
            }
        });
        
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
                dialog.cancel();//mp.stop();
//                System.runFinalizersOnExit(true);
//                System.exit(0);finish();
		        Intent intent = new Intent(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_HOME);
		        
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        startActivity(intent);
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
//	public void AlertCustom(){
//	    final Dialog dialog = new Dialog(MenuActivity.this);
//	    
////	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    dialog.setContentView(R.layout.custom_dialog);
//	    dialog.setCancelable(true);
//	    dialog.setTitle("Mouse Maze");
//
//	    //adding text dynamically
//	    TextView txt = (TextView) dialog.findViewById(R.id.textView);
//	    txt.setText("Dear Users, If you like our app please give us Rating of 5 stars.");
//
//	    ImageView image = (ImageView)dialog.findViewById(R.id.image);
//	    image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));
//
//	    //adding button click event
//	    Button dismissButton = (Button) dialog.findViewById(R.id.button);
//	    Button NoButton = (Button) dialog.findViewById(R.id.button_no);
//	    dismissButton.setBackgroundResource(R.drawable.btn_help);
//	    NoButton.setBackgroundResource(R.drawable.btn_help);
//	    dismissButton.setOnClickListener(new OnClickListener() {
//	        @Override
//	        public void onClick(View v) {
//
//	            dialog.dismiss();
//	        }
//	    });
//	    NoButton.setOnClickListener(new OnClickListener() {
//	        @Override
//	        public void onClick(View v) {
//
//	            dialog.dismiss();
//	        }
//	    });
//
//	    dialog.show();
//	}
////////////////////////////on_exit_custom_prompt
	public void AlertCustomExit(){//***************************************
	    final Dialog dialog = new Dialog(MenuActivity.this);
	    
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    dialog.setContentView(R.layout.custom_exit);
	    dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_exit);
	    dialog.setCancelable(false);
	    dialog.setTitle("**Make Pairs**");
	    //adding text dynamically
//	    TextView txt = (TextView) dialog.findViewById(R.id.textView);
//	    txt.setText("Game Paused..");

	    ImageView image = (ImageView)dialog.findViewById(R.id.image);
//	    image.setImageResource(R.drawable.ic_launcher);
	    image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

	    //adding button click event
	    ImageView _btn_yes = (ImageView) dialog.findViewById(R.id.btn_yes);
	    ImageView _btn_no = (ImageView) dialog.findViewById(R.id.btn_no);
//	    dismissButton.setBackgroundResource(R.drawable.btn_resume);
	    _btn_yes.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	        	Audio.stop();Audio.release();
		        Intent intent = new Intent(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_HOME);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        startActivity(intent);finish();
	            dialog.dismiss();
	        }
	    });
	    _btn_no.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	        	Audio.stop();Audio.release();
	            dialog.dismiss();
	            
	        }
	    });
	    dialog.show();
	}
//**************************************hari_end_all_alerts*************************************//

/////////////////////////////end_exit=onBackpressed
	@Override
	protected void onDestroy() {
		mp.release();
		super.onDestroy();
	}
//	 @Override
	 public void onBackPressed() {
//	     super.onBackPressed();
		 AlertCustomExit();
//		 AlertCustom();
//			showAlert("Dear Users, If you like our App please give us Rating of 5 stars.");
//			if(backButtonCount >= 1)
//		    {
////			     overridePendingTransition(R.anim.disappear, R.anim.appear);
//		        Intent intent = new Intent(Intent.ACTION_MAIN);
//		        intent.addCategory(Intent.CATEGORY_HOME);
//		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		        startActivity(intent);finish();
//		    }
//		    else
//		    {
//		        Toast.makeText(this, "Press again to exit.", Toast.LENGTH_SHORT).show();
//		        
//				try{
//					cdt = new CountDownTimer(2000, 1000) {
//						 long now = 0, secs, mins;
//
//						 public void onTick(long millisUntilFinished) {
//							 now = millisUntilFinished / sec;
//							 secs = now % 60;mins = now/60;
//						 }
//						      public void onFinish() {
////								     if(!((Activity) context).isFinishing())
////								     {
//								    	            if(!isFinishing()){
//								    	            	
//										                }
//								    	            }
////								    	 }			     
//						   }.start();
//			 }catch(Exception ex){ex.printStackTrace();}
		        
//		        backButtonCount++;
//		    }
	 }
}
