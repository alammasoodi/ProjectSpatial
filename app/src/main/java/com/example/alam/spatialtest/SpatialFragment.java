package com.example.alam.spatialtest;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alam on 7/11/17.
 */

public class SpatialFragment extends Fragment {

    private int position = -1;
    private int pos;
    int tryNo = 1;
    boolean isFlag;
    private int clickNumber = 0;
    private int currentPos;
    private int currentPosition = -1;
    private int maxPosition;
    private int gameType;
    SharedPreferences pref;
    int score = 0;
    SharedPreferences.Editor editor;
    private int count = 0;
    private int highLightCount;
    View v;
    TextView title;
    private int studyStyle;
    private TimerTask timeTask;
    private static String TAG = "MainActivity";
    NoRepeatRandom nrr = new NoRepeatRandom(0, 8);
    private GridView gridView;
    //private ViewGroup buttonView;
    private Button start;
    private boolean startClick = false;
    Button nextButton;
    int flowerNo=0,flowerScore=0;
    LinearLayout scoreLayout1, scoreLayout2;
    TextView flower,scoreView;
    private GridViewAdapter adapter;
    private ArrayList<GridViewItem> items;
    private ArrayList<Integer> listOfPositions = new ArrayList<Integer>();
    private ArrayList<Integer> comparePositions = new ArrayList<Integer>();
    private static int resourceArray[] = {
            R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image };
    private static int resourceArray3[] = {
            R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image };
    private static int resourceArray2[] = {
            R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image };
    private static int resourceArraySel[] = {
            R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp,R.drawable.image_disp
    };private static int resourceArraySelWrong[] = {
            R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong,R.drawable.image_disp_wrong    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // set gridview adapter
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_main, container, false);
        pref = getActivity().getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        locateView(v); //define all elements in content views
        setAdapter(gameType);
        return v;
    }
    /**
     * Canceling highlight work when app is not visible
     */
    @Override
    public void onPause() {
        super.onPause();
        cancelTimerTask();
    }

    private void locateView(View v) {
        gridView = (GridView) v.findViewById(R.id.grid_view);
        title = (TextView) v.findViewById(R.id.test_title);
        nextButton = (Button) v.findViewById(R.id.nextGame);
        scoreLayout1 = (LinearLayout)v.findViewById(R.id.score1_layout);
        scoreLayout2 = (LinearLayout)v.findViewById(R.id.score2_layout);
        flower = (TextView) v.findViewById(R.id.flower_no);
        scoreView = (TextView) v.findViewById(R.id.score);
        highlightItems(studyStyle);
    }


    private void highlightItems(int gameType) {
        setAdapter(gameType);
        launchHighlightWork();
    }

    /**
     * Set gridview adapter
     * @param level
     */
    private void setAdapter(int level) {
        items = new ArrayList<GridViewItem>();
        if (level == 1) {
            highLightCount = 6;
            gridView.setNumColumns(3);
            maxPosition = 9;
            createListImageItems(resourceArray);
        }
        else if (level == 0) {
            highLightCount = 3;
            gridView.setNumColumns(2);
            maxPosition = 4;
            createListImageItems(resourceArray2);
        }else if (level == 2) {
            highLightCount = 6;
            gridView.setNumColumns(4);
            maxPosition = 15;
            createListImageItems(resourceArray3);
        }

        adapter = new GridViewAdapter(getActivity(), R.layout.item_grid, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (startClick) {
                    clickNumber = clickNumber + 1;
                    comparePositions.add(pos);
                    boolean flag = false;
                    if (clickNumber < highLightCount) {

                        if (comparePositions.get(clickNumber-1).equals(listOfPositions.get(clickNumber-1))) {
                            adapter.getItem(pos).setCorrectHighLight(true);
                            adapter.notifyDataSetChanged();
                            score = score+1;
                            flower.setText(String.valueOf(score));
                            flowerScore = flowerScore+5;
                            scoreView.setText(String.valueOf(flowerScore));
                        } else {
                            adapter.getItem(pos).setWrongHighLight(true);
                            adapter.notifyDataSetChanged();
                            score = -1;

                        }
                        if (score == highLightCount -1) {
                            title.setText("Well Done");
                            startCounter();

                        }
                        else if(score == -1){
                            if(tryNo==0){
                                title.setText("You completed the sequence");
                            }
                            else {
                                title.setText("Try Again");
                            }
                            startClick = false;
                            startCounter();


                        }

                    }

                }

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (score == highLightCount -1) {
                nextButton.setVisibility(View.VISIBLE);
                scoreLayout1.setVisibility(View.GONE);
                scoreLayout2.setVisibility(View.GONE);
                gameType++;
                startNextLevel(gameType);
                Toast.makeText(getActivity(), "Matched", Toast.LENGTH_SHORT).show();


            }
            else if(score == -1) {
                nextButton.setVisibility(View.VISIBLE);
                scoreLayout1.setVisibility(View.GONE);
                scoreLayout2.setVisibility(View.GONE);
                tryNo--;
                if (gameType != 0) {
                    gameType -- ;
               }
                editor.commit();
                if (tryNo > 0) {
                    startNextLevel(gameType);
                    Toast.makeText(getActivity(), "Tries remaining  "+tryNo, Toast.LENGTH_SHORT).show();
                }
                else {

                    int showScore = flowerScore;
                    Intent intent = new Intent(getActivity(),Result.class);
                    intent.putExtra("score",showScore);
                    startActivity(intent);
                }
            }
        }
    });
    }
    public void startCounter(){
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                nextButton.setVisibility(View.VISIBLE);
                scoreLayout1.setVisibility(View.GONE);
                scoreLayout2.setVisibility(View.GONE);
            }
        }.start();

    }
    public void startNextLevel(int gameLevel){
        title.setText("Tap the flowers in the order they lit up");
        nextButton.setVisibility(View.INVISIBLE);
        score = 0;
        clickNumber = 0;
        count = 0;
        startClick = false;
        listOfPositions = new ArrayList<>();
        comparePositions = new ArrayList<>();
        setAdapter(gameLevel);
    }


    /**
     * create an arraylist of highlight item use for gridview adapter
     * @param array
     */
    private void createListImageItems(int[] array) {
        for (int i = 0; i < array.length; i++) {
            GridViewItem item = new GridViewItem();
            item.setHighLight(false);
            item.setImage(array[i]);
            items.add(item);
        }
    }

    /**
     * Highlight gridview items method
     */
    private void highLight() {
        if (count == 0 ) {
            //set item at position 0 highlight
            //update adapter after that
            pos = randInt(0,maxPosition-1);
            listOfPositions.add(pos);
            adapter.getItem(pos).setHighLight(true);
            adapter.notifyDataSetChanged();
        }
        else if (count < highLightCount) {
            // highlight current item and not highlight previous item
            //and update adapter after that
            try{
                adapter.getItem(pos).setHighLight(false);
            }catch (IndexOutOfBoundsException ie){
                ie.printStackTrace();
            }
            pos = randInt(0,maxPosition-1);
            if(listOfPositions.contains(pos)){
                while (listOfPositions.contains(pos)){
                    pos = randInt(0,maxPosition-1);
                }
            }
            listOfPositions.add(pos);
            adapter.getItem(pos).setHighLight(true);
            adapter.notifyDataSetChanged();
            if(count == highLightCount -1){
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        adapter.getItem(pos).setHighLight(false);
                        adapter.notifyDataSetChanged();
                        startClick = true;
                        title.setText("Tap the flowers in the order they lit up");
                        scoreLayout1.setVisibility(View.VISIBLE);
                        scoreLayout2.setVisibility(View.VISIBLE);
                        flower.setText("0");
                        scoreView.setText(String.valueOf(flowerScore));
                    }
                }.start();
            }
//            if(count == 5){
//                adapter.getItem(pos).setHighLight(false);
//                adapter.notifyDataSetChanged();
//            }

        } else {
            //canceling time task and reset position

            startClick = true;
            //cancelTimerTask();
            count = 0;
            start.setText(getResources().getString(R.string.start));

        }
    }

    /**
     * Run this task for highlight items
     */
    private void launchHighlightWork() {
        Log.v(TAG, "callAsynchronousTask");
        final Handler handler = new Handler();
        Timer timer = new Timer();
        timeTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            HiglightTask higlightBackgroundTask = new HiglightTask();
                            higlightBackgroundTask.execute();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };

        timer.schedule(timeTask, 1000, 2000);
    }

    /**
     * This asynctask use for highlight
     *
     */
    private class HiglightTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (count< highLightCount) {
                publishProgress(Integer.valueOf(count));
            }
            Log.i(TAG, "do in background");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (count < highLightCount) {
                highLight();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (count < highLightCount) {
                count = count +1;
            } else {

            }
            Log.d(TAG, "pos " + position);
            Log.i(TAG, "onProgressUpdate");
        }
    }

    /**
     * Canceling the time task, so stopping highlight.
     */
    private void cancelTimerTask() {
        if (timeTask != null) {
            timeTask.cancel();
        }
    }
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
