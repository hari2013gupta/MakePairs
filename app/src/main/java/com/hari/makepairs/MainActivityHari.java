package com.hari.makepairs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivityHari extends Activity implements OnClickListener {
    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private static Object lock = new Object();
    //****************************************OWN VARIABLES
    public String diff;
    public CountDownTimer cdt;
    int turns;
    int secc = 0;
    boolean game_bool = false;
    long millis_cdt = 0;
    MediaPlayer Audio, mp_failed, mp_win;
    boolean bool_sound = true, bool_alert = true;
    //    private WakeLock mWakeLock;
    int nx = 4, ny = 4;//ok_hari
    private int size = 0, Count = 0;
    private Context context;
    private Drawable backImage;
    private int[][] cards;
    private List<Drawable> images;
    private Card firstCard;
    private Card seconedCard;
    private ButtonListener buttonListener;
    private ProgressBar bar;
    private TableLayout mainTable;
    private UpdateCardsHandler handler;
    private TextView mTxtField;
    private ImageView _btn_pause;
    private long TotalTime = 80000, sec = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bool_sound = extras.getBoolean("bool_sound");
        }

//        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//        mWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
        Audio = MediaPlayer.create(this, R.raw.mp_picking_coin);
        mp_failed = MediaPlayer.create(this, R.raw.mp_game_over);
        mp_win = MediaPlayer.create(this, R.raw.mp_picking_coin);
//    	Audio.setLooping(true);
//        	Audio.start();

//        TextView url = ((TextView)findViewById(R.id.myWebSite));
//        Linkify.addLinks(url, Linkify.WEB_URLS);

        backImage = getResources().getDrawable(R.drawable.icon);
       
       /*
       ((Button)findViewById(R.id.ButtonNew)).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			newGame();
		}
	});*/

        buttonListener = new ButtonListener();

        mainTable = (TableLayout) findViewById(R.id.TableLayout03);

        context = mainTable.getContext();
        nx = 3;
        ny = 4;//ok_hari
        if (extras != null) {
            diff = extras.getString("level");//15,20//19,25//22,30//7,11//10,14//11,15//13,18//19,24//
            if (diff.equalsIgnoreCase("diff1")) {
                nx = 3;
                ny = 4;
                TotalTime = TotalTime / 2;
            } else if (diff.equalsIgnoreCase("diff2")) {
                nx = 4;
                ny = 4;
                TotalTime = TotalTime / 2 + TotalTime / 4;
            } else if (diff.equalsIgnoreCase("diff3")) {
                nx = 4;
                ny = 5;
            } else if (diff.equalsIgnoreCase("diff4")) {
                nx = 5;
                ny = 6;
                TotalTime = TotalTime + TotalTime / 2;
            } else if (diff.equalsIgnoreCase("diff5")) {
                nx = 6;
                ny = 6;
                TotalTime = TotalTime * 2;
            } else if (diff.equalsIgnoreCase("diff6")) {
                nx = 6;
                ny = 7;
                TotalTime = TotalTime * 2;
            } else if (diff.equalsIgnoreCase("diff7")) {
                nx = 5;
                ny = 5;
            } else if (diff.equalsIgnoreCase("diff8")) {
                nx = 5;
                ny = 6;
            } else if (diff.equalsIgnoreCase("diff9")) {
                nx = 6;
                ny = 7;
            } else if (diff.equalsIgnoreCase("diff10")) {
                nx = 7;
                ny = 7;
            }
        }
        newGame(nx, ny);
//			else if (diff.equalsIgnoreCase("diff11")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff12")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff13")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff14")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff15")) {nx = 15;ny = 20;
//			}else if (diff.equalsIgnoreCase("diff16")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff17")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff18")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff19")) {nx = 15;ny = 20;}else if (diff.equalsIgnoreCase("diff20")) {nx = 15;ny = 20;}}

//			Typeface font = Typeface.createFromAsset(getAssets(),"fonts/cooprblk.ttf");//akbar.ttf");
        mTxtField = (TextView) findViewById(R.id.tv_timer);
        _btn_pause = (ImageView) findViewById(R.id.btn_pause);
        _btn_pause.setOnClickListener(this);

//			mTxtField.setTypeface(font);mTxtField.setText("");
        mTxtField.setTextColor(Color.GREEN);
        mTxtField.setTextSize(30);

        bar = (ProgressBar) findViewById(R.id.progressHorizontal);
        bar.setProgress(bar.getProgress() * 100);
        bar.setProgress((int) TotalTime);
        bar.setIndeterminate(false);
        Resources res = getResources();
//	        Rect bounds = bar.getProgressDrawable().getBounds();
//	         bar.setProgressDrawable(res.getDrawable(R.drawable.ic_launcher));
//	         bar.setProgressDrawable(R.Co);
//	         bar.getIndeterminateDrawable().setColorFilter(0xFFFF0000,android.graphics.PorterDuff.Mode.MULTIPLY);
//	        Drawable drawable = bar.getProgressDrawable();
//	        drawable.setColorFilter(new LightingColorFilter(0xFF000000, Color.GREEN));

        secc = (int) (TotalTime / 1000);
        bar.setMax(secc);

        try {
            cdt = new CountDownTimer(TotalTime, sec) {
                long now = 0, secs, mins;

                public void onTick(long millisUntilFinished) {
                    now = millisUntilFinished / sec;
                    secs = now % 60;
                    mins = now / 60;
                    secc = (int) (secs + mins * 60);
                    bar.setProgress(secc);
                    mTxtField.setTextColor(Color.GREEN);
                    mTxtField.setText("" + formatDigits(mins) + ":" + formatDigits(secs));
                    millis_cdt = millisUntilFinished;
                }

                public void onFinish() {
                    if (bool_sound) mp_failed.start();
                    mTxtField.setTextColor(Color.RED);
                    mTxtField.setText("Times Up!");
                    bar.setProgress(0);
//				    	  mWakeLock.acquire();
//						     if(!((Activity) context).isFinishing())
//						     {
//						    	 gameOverDialog();//
                    if (!isFinishing()) {
//						    	            AlertWin("Level Failed!");
                        if (game_bool == false) {
                            if (bool_alert) {
                                bool_alert = false;
                                AlertCustomGameOver();
                            }
                        }
                    }
//						    	 }			     
                }
            }.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//*********************************end hari code
//       	 Spinner s = (Spinner) findViewById(R.id.Spinner01);
//	        ArrayAdapter adapter = ArrayAdapter.createFromResource(
//	                this, R.array.type, android.R.layout.simple_spinner_item);
//	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	        s.setAdapter(adapter);
//	        
//	        s.setOnItemSelectedListener(new OnItemSelectedListener(){
//	        	
//	    	  @Override
//	    	  public void onItemSelected(
//	    			  android.widget.AdapterView<?> arg0, 
//	    			  View arg1, int pos, long arg3){
//	    		  
//	    		  ((Spinner) findViewById(R.id.Spinner01)).setSelection(0);
//	    		  
//	  			int x,y;
//	  			
//	  			switch (pos) {
//				case 1:
//					x=4;y=4;
//					break;
//				case 2:
//					x=4;y=5;
//					break;
//				case 3:
//					x=4;y=6;
//					break;
//				case 4:
//					x=5;y=6;
//					break;
//				case 5:
//					x=8;y=8;
//					break;
//				default:
//					return;
//				}
//	  			newGame(x,y);
//	  			
//	  		}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				
//			}
//
//	  	});
    }//endOncrea

    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;
        size = 0;
        Count = 0;
        bool_alert = true;
        cards = new int[COL_COUNT][ROW_COUNT];

        mainTable.removeView(findViewById(R.id.TableRow01));
        mainTable.removeView(findViewById(R.id.TableRow02));

        TableRow tr = ((TableRow) findViewById(R.id.TableRow03));
        tr.removeAllViews();

        mainTable = new TableLayout(context);
        tr.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard = null;
        loadCards();

        turns = 0;
        ((TextView) findViewById(R.id.tv1)).setText("Tries: " + turns);
    }

    private void loadImages() {
        images = new ArrayList<Drawable>();

        images.add(getResources().getDrawable(R.drawable.card1));
        images.add(getResources().getDrawable(R.drawable.card2));
        images.add(getResources().getDrawable(R.drawable.card3));
        images.add(getResources().getDrawable(R.drawable.card4));
        images.add(getResources().getDrawable(R.drawable.card5));
        images.add(getResources().getDrawable(R.drawable.card6));
        images.add(getResources().getDrawable(R.drawable.card7));
        images.add(getResources().getDrawable(R.drawable.card8));
        images.add(getResources().getDrawable(R.drawable.card9));
        images.add(getResources().getDrawable(R.drawable.card10));
        images.add(getResources().getDrawable(R.drawable.card11));
        images.add(getResources().getDrawable(R.drawable.card12));
        images.add(getResources().getDrawable(R.drawable.card13));
        images.add(getResources().getDrawable(R.drawable.card14));
        images.add(getResources().getDrawable(R.drawable.card15));
        images.add(getResources().getDrawable(R.drawable.card16));
        images.add(getResources().getDrawable(R.drawable.card17));
        images.add(getResources().getDrawable(R.drawable.card18));
        images.add(getResources().getDrawable(R.drawable.card19));
        images.add(getResources().getDrawable(R.drawable.card20));
        images.add(getResources().getDrawable(R.drawable.card21));
    }

    private void loadCards() {
        try {
            size = ROW_COUNT * COL_COUNT;

            Log.i("loadCards()", "size=" + size);

            ArrayList<Integer> list = new ArrayList<Integer>();

            for (int i = 0; i < size; i++) {
                list.add(new Integer(i));
            }

            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) {
                int t = 0;

                if (i > 0) {
                    t = r.nextInt(i);
                }

                t = list.remove(t).intValue();
                cards[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

//	    		Log.i("loadCards(i)","======"+(i));
//	    	    Log.i("loadCards(i)", "i=="+(i)+" card["+(i%COL_COUNT)+
//	    				"]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        } catch (Exception e) {
            Log.e("loadCards()", e + "");
        }
    }

    private TableRow createRow(int y) {
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    private View createImageButton(int x, int y) {
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100 * x + y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    //**********************************starting custom alert code*******************************************//
    public void AlertCustomPaused() {
        final Dialog dialog = new Dialog(MainActivityHari.this);

        //--------hari_extras2
//	    View title = getWindow().findViewById(android.R.id.title);
//	    View titleBar = (View) title.getParent();
//	    titleBar.setBackgroundColor(Color.BLACK);
//	    titleBar.setVisibility(View.VISIBLE);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_paused);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_custom);
        dialog.setCancelable(false);
        dialog.setTitle("**Make Pairs**");

        //adding text dynamically
//	    TextView txt = (TextView) dialog.findViewById(R.id.textView);
//	    txt.setText("Game Paused..");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//	    image.setImageResource(R.drawable.ic_launcher);
        image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

        //adding button click event
        ImageView _dismissButtonResume = (ImageView) dialog.findViewById(R.id.buttonResume);
        ImageView _buttonReplay = (ImageView) dialog.findViewById(R.id.buttonReplay);
        ImageView _buttonMainMenu = (ImageView) dialog.findViewById(R.id.buttonMainMenu);
//	    dismissButton.setBackgroundResource(R.drawable.btn_resume);
        _dismissButtonResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_alert = true;
                cdt.resume();//Audio.start();
                dialog.dismiss();
            }
        });
        _buttonReplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bool_alert = true;
                cdt.cancel();
                cdt.mStopTimeInFuture = cdt.mStopTimeInFuture + 20000;
                newGame(nx, ny);
                cdt.start();
                dialog.dismiss();
            }
        });
        _buttonMainMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//	        	cdt.onFinish();
                bool_alert = false;
                cdt.cancel();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    public void AlertCustomGameOver() {//***************************************
        final Dialog dialog = new Dialog(MainActivityHari.this);

        //--------hari_extras2

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_gameover);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_custom);
        dialog.setCancelable(false);
        dialog.setTitle("**Make Pairs**");

        //adding text dynamically
//	    TextView txt = (TextView) dialog.findViewById(R.id.textView);
//	    txt.setText("Game Paused..");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//	    image.setImageResource(R.drawable.ic_launcher);
        image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

        //adding button click event
        ImageView _buttonReplay = (ImageView) dialog.findViewById(R.id.buttonReplay);
        ImageView _buttonMainMenu = (ImageView) dialog.findViewById(R.id.buttonMainMenu);
//	    dismissButton.setBackgroundResource(R.drawable.btn_resume);
        _buttonReplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                cdt.mStopTimeInFuture = cdt.mStopTimeInFuture + 20000;
                newGame(nx, ny);
                cdt.start();
                dialog.dismiss();
            }
        });
        _buttonMainMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//	        	cdt.onFinish();
                bool_alert = false;
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    public void AlertCustomGameWin() {//***************************************
        final Dialog dialog = new Dialog(MainActivityHari.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_gamewin);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_custom);
        dialog.setCancelable(false);
        dialog.setTitle("**Make Pairs**");
        //adding text dynamically
//	    TextView txt = (TextView) dialog.findViewById(R.id.textView);
//	    txt.setText("Game Paused..");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//	    image.setImageResource(R.drawable.ic_launcher);
        image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

        //adding button click event
        ImageView _buttonNextReplay = (ImageView) dialog.findViewById(R.id.buttonReplay);
        ImageView _buttonMainMenu = (ImageView) dialog.findViewById(R.id.buttonMainMenu);
//	    dismissButton.setBackgroundResource(R.drawable.btn_resume);
        _buttonNextReplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game_bool = true;
                cdt.cancel();
                game_bool = false;
//	        	Audio.stop();Audio.release();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivityHari.class);
                intent.putExtra("level", diff);
                intent.putExtra("bool_sound", bool_sound);
                startActivity(intent);
                finish();

//	        	newGame(nx,ny);
////	        	cdt.onFinish();
//	        	cdt.cancel();
//				  cdt.mStopTimeInFuture=cdt.mStopTimeInFuture+20000;
//	        	cdt.start();
            }
        });
        _buttonMainMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//	        	game_bool=true;cdt.onFinish();game_bool=false;
//	        	Audio.stop();Audio.release();
                bool_alert = false;
                cdt.cancel();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    //**************************************hari_end_all_alerts*************************************//
    private String formatDigits(long num) {
        return (num < 10) ? "0" + num : new Long(num).toString();
    }

    @Override
    public void onPause() {
        if (bool_alert) {
            pauseGame();
            bool_alert = false;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//=====================================================================================		
// Life Cycle Handling (Phone calls, pause etc)
//=====================================================================================		

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Audio.release();
        mp_failed.release();
        mp_win.release();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    //===================================================================
// Back Button
//===================================================================
    @Override
    public void onClick(View v) {
        if (bool_alert) {
            pauseGame();
            bool_alert = false;
        }
    }

    protected void pauseGame() {
        if (cdt != null) {
            try {
                AlertCustomPaused();
//			b4.setBackgroundResource(R.drawable.btn_resume);
                cdt.pause();//onPause();
//    Audio.pause();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


//@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//	super.onRestoreInstanceState(savedInstanceState);	}
//	
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//	super.onSaveInstanceState(outState);      	} 

    protected void GameWinActivity() {
        Intent i = new Intent(MainActivityHari.this, GameWin.class);
        i.putExtra("level", diff);
        i.putExtra("bool_sound", bool_sound);
        startActivity(i);
        finish();
    }

    protected void GameOverActivity() {
        Intent i = new Intent(MainActivityHari.this, GameOver.class);
        i.putExtra("level", diff);
        i.putExtra("bool_sound", bool_sound);
        startActivity(i);
        finish();
    }

    ///////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
//    	mWakeLock.acquire();
//    	super.onBackPressed();
        if (bool_alert) {
            pauseGame();
            bool_alert = false;
        }
//    	cdt.onFinish();//Audio.stop();
//    	Intent i=new Intent(getApplicationContext(),ModeActivity.class);startActivity(i);finish();
    }

    //******************************************************OnClickListener()
    class ButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            synchronized (lock) {
                if (firstCard != null && seconedCard != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard((Button) v, x, y);
//    			System.out.println("-------"+size+" ROW_COUNT="+ROW_COUNT+" COL_COUNT="+COL_COUNT);
            }
        }

        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if (firstCard == null) {
                firstCard = new Card(button, x, y);
            } else {

                if (firstCard.x == x && firstCard.y == y) {
                    return; //the user pressed the same card
                }

                seconedCard = new Card(button, x, y);
                turns++;
                ((TextView) findViewById(R.id.tv1)).setText("Tries: " + turns);
//		    	 System.out.println("Tries="+turns);
                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        } catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                };
                Timer t = new Timer(false);
                t.schedule(tt, 250);
            }
//System.out.println("tries2="+turns+" size=");
        }
    }

    //******************************************************End OnClickListener()
    class UpdateCardsHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]) {
                if (bool_sound) Audio.start();
                firstCard.button.setVisibility(View.INVISIBLE);
                seconedCard.button.setVisibility(View.INVISIBLE);
//    				Log.i("Count==",""+Count);Log.i("Size==",""+size);
                if (Count == size / 2 - 1) {
//    					mp_failed.start();
                    cdt.pause();
                    if (bool_alert) {
                        AlertCustomGameWin();
                        bool_alert = false;
                    }
                } else Count++;
            } else {
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
            }
            firstCard = null;
            seconedCard = null;
        }
    }
}