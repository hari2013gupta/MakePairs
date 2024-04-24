package com.hari.makepairs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

class Tile {
    public int x;
    public int y;
    public int[][] grid;
    public Tile(int[][] grid, int x, int y) {
        this.x = x;
        this.y=y;
        this.grid = grid;
    }
}
public class MainActivity1 extends Activity implements OnClickListener {
    final String TAG = MainActivity1.class.getSimpleName();
    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private static final Object lock = new Object();
    //****************************************OWN VARIABLES
//    public String diff;
//    private ButtonListener buttonListener;
    int nx = 4, ny = 4;//ok_hari
    private int size = 0, Count = 0;
    List<String> input_strings = new ArrayList<>();
    private int[][] grid= new int[input_strings.size()][];
    private Tile firstTile, secondTile;
    private UpdateCardsHandler handler;

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new UpdateCardsHandler();
//        buttonListener = new ButtonListener();

        newGame(nx, ny);
//        tileGame();
    }//endOncrea

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void tileGame() {
//
//        String arg0 = "11";
//        List<String> input_strings = new ArrayList<>();
//
//        String line = "11";
//        boolean first_arg = true;
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//            while ((line = br.readLine()) != null) {
//                if (line.equals("")) {
//                    continue;
//                }
//
//                if(first_arg) {
//                    arg0 = line;
//                    first_arg = false;
//                } else {
//                    input_strings.add(line);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        String[] tokens;
//        int[][] grid = new int[input_strings.size()][];
//        for(int i = 0; i < input_strings.size(); i++) {
//            tokens = input_strings.get(i).split(" ");
//            grid[i] = new int[tokens.length];
//            for(int j = 0; j < tokens.length; j++) {
//                grid[i][j] = Integer.parseInt(tokens[j]);
//            }
//        }
//        tokens = arg0.split(" ");
//        int row = Integer.parseInt(tokens[0]);
//        int col = Integer.parseInt(tokens[1]);
//
//        System.out.println(row + " " + col);
////     System.out.println(disappear(grid, row, col));
//
//    }

    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;
        size = 0;
        Count = 0;
        grid = new int[COL_COUNT][ROW_COUNT];

        for (int y = 0; y < ROW_COUNT; y++) {
            for (int x = 0; x < COL_COUNT; x++) {
                System.out.println("X=" + x + "Y=" + y);
                System.out.println("setId=" + (100 * x + y));
                grid[x][y] = x*y;
            }
        }
        firstTile = null;
        loadCards();
    }

    private void loadCards() {
        try {
            size = ROW_COUNT * COL_COUNT;

            Log.i("loadCards()", "size=" + size);

            ArrayList<Integer> list = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                list.add(i);
            }

            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) {
                int t = 0;

                if (i > 0) {
                    t = r.nextInt(i);
                }

                t = list.remove(t);
                grid[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

	    		Log.i("loadCards(i)","======"+(i));
//	    	    Log.i("loadCards(i)", "i=="+(i)+" card["+(i%COL_COUNT)+
//	    				"]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        } catch (Exception e) {
            Log.e("loadCards()", e + "");
        }
    }

    @Override
    public void onClick(View v) {

    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            synchronized (lock) {
                if (firstTile != null && secondTile != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard(x, y);
//    			System.out.println("-------"+size+" ROW_COUNT="+ROW_COUNT+" COL_COUNT="+COL_COUNT);
            }
        }
        private void turnCard(int x, int y) {
            Log.i(TAG, "Printing and Turn Card:" + x + "-" +y);
            Log.i(TAG, "Card:" + grid[x][y]);

            if (firstTile == null) {
                firstTile = new Tile(grid,x, y);
            } else {

                if (firstTile.x == x && firstTile.y == y) {
                    return; //the user pressed the same card
                }

                secondTile = new Tile(grid, x, y);
                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        } catch (Exception e) {
                            Log.e("E1", Objects.requireNonNull(e.getMessage()));
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
        public void handleMessage(@NonNull Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (grid[secondTile.x][secondTile.y] == grid[firstTile.x][firstTile.y]) {
                System.out.println("firstTile.button.setVisibility(View.INVISIBLE");
                System.out.println("secondTile.button.setVisibility(View.INVISIBLE");
//    				Log.i("Count==",""+Count);Log.i("Size==",""+size);
                if (Count != size / 2 - 1) {
                    Count++;
                }
            } else {
                Log.i(TAG, "card1.backgroundImage.reset");
                Log.i(TAG, "card2.backgroundImage.reset");
            }
            firstTile = null;
            secondTile = null;
        }
    }
}