package com.example.alam.spatialtest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int position = -1;
    private int pos;
    private int clickNumber = 0;
    private int currentPos;
    private int currentPosition = -1;
    private int maxPosition;
    private int count = 0;
    private int imageNo;
    private int studyStyle;
    private TimerTask timeTask;
    private static String TAG = "MainActivity";
    NoRepeatRandom nrr = new NoRepeatRandom(0, 8);
    private GridView gridNumber;
    //private ViewGroup buttonView;
    private Button start;
    private boolean startClick = false;
    private GridViewAdapter adapter;
    private ArrayList<GridViewItem> items;
    private TextView test_title;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locateView(); //define all elements in content views
        setAdapter(2); // set gridview adapter
    }

    /**
     * Canceling highlight work when app is not visible
     */
    @Override
    protected void onPause() {
        super.onPause();
        cancelTimerTask();
    }

    private void locateView() {
        gridNumber = (GridView) findViewById(R.id.grid_view);


        //buttonView = (LinearLayout) findViewById(R.id.fragment_button);
        replaceButtonLayout();
    }

    /**
     * set Button Start view from other xml
     */

    private void replaceButtonLayout() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_button, null, false);
        start = (Button) view.findViewById(R.id.btn_start);
        if (start.getText().toString()
                .equals(getResources().getString(R.string.start))) {
            //position = currentPosition;
            pos = currentPos;
            setAdapter(studyStyle);
            launchHighlightWork();
            start.setText(getResources().getString(R.string.stop));

        } else {
            Log.i(TAG, "current position" + position);
            currentPos = pos;
            //currentPosition = position;
            cancelTimerTask();
            setAdapter(studyStyle);
            highLight();
            start.setText(getResources().getString(R.string.start));
        }
        //start.setOnClickListener(startCountingOnClick()); //set onclick event for start button
        //buttonView.addView(view);
    }

    /**
     * Set gridview adapter
     * @param studyCase
     */
    private void setAdapter(int studyCase) {
        items = new ArrayList<GridViewItem>();
        if (studyCase == 0) {
            imageNo = 6;
            maxPosition = 9;
            createListImageItems(resourceArray);
        }
        else if (studyCase == 1) {
            imageNo = 3;
            gridNumber.setNumColumns(2);
            maxPosition = 4;
            createListImageItems(resourceArray2);
        }else if (studyCase == 2) {
            imageNo = 6;
            gridNumber.setNumColumns(4);
            maxPosition = 15;
            createListImageItems(resourceArray3);
        }

        adapter = new GridViewAdapter(this, R.layout.item_grid, items);
        gridNumber.setAdapter(adapter);

        gridNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (startClick) {
                    clickNumber = clickNumber + 1;
                    comparePositions.add(pos);
                    boolean flag = false;
                    if (clickNumber < imageNo) {
                        if (comparePositions.size() == listOfPositions.size()) {
                            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < listOfPositions.size(); i++) {
                                if (comparePositions.get(i).equals(listOfPositions.get(i))) {
                                    flag = true;
                                } else {
                                    flag = false;
                                    break;
                                }

                            }
                        }
//                ImageView imageView = (ImageView) view;
//                if (clickNumber < 6) {
//                    if (comparePositions.get(clickNumber - 1).equals(listOfPositions.get(clickNumber - 1))) {
//                        view.setBackgroundColor(R.drawable.image_disp);              }
//
//                } else {
//                       view.setBackgroundColor(R.drawable.image_disp_wrong);              }
                        if (flag == true) {
                            Toast.makeText(MainActivity.this, "Matched", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not Matched", Toast.LENGTH_SHORT).show();

                        }
                        adapter.getItem(pos).setHighLight(true);
                        adapter.notifyDataSetChanged();
                        //view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                }
            }
        });
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
        else if (count < imageNo) {
            // highlight current item and not highlight previous item
            //and update adapter after that

            adapter.getItem(pos).setHighLight(false);
            pos = randInt(0,maxPosition-1);
            if(listOfPositions.contains(pos)){
                while (listOfPositions.contains(pos)){
                    pos = randInt(0,maxPosition-1);
                }
            }
            listOfPositions.add(pos);
            adapter.getItem(pos).setHighLight(true);
            adapter.notifyDataSetChanged();
            if(count == imageNo-1){
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        adapter.getItem(pos).setHighLight(false);
                        adapter.notifyDataSetChanged();
                        startClick = true;
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

//            position = -1;
//            currentPosition = -1;
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
//            if (position < maxPosition) {
//                publishProgress(Integer.valueOf(position));
//            }
            if (count< imageNo) {
                publishProgress(Integer.valueOf(count));
            }
            Log.i(TAG, "do in background");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
//            if (position < maxPosition) {
//                highLight();
//            }
            if (count < imageNo) {
                highLight();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            if (position < maxPosition) {
//                position = position + 1;
            if (count < imageNo) {
                count = count +1;
            } else {

            }
            Log.d(TAG, "pos " + position);
            Log.i(TAG, "onProgressUpdate");
        }
    }

    /**
     * Start button event
     * @return
     */
    private View.OnClickListener startCountingOnClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (start.getText().toString()
                        .equals(getResources().getString(R.string.start))) {
                    //position = currentPosition;
                    pos = currentPos;
                    setAdapter(studyStyle);
                    launchHighlightWork();
                    start.setText(getResources().getString(R.string.stop));

                } else {
                    Log.i(TAG, "current position" + position);
                    currentPos = pos;
                    //currentPosition = position;
                    cancelTimerTask();
                    setAdapter(studyStyle);
                    highLight();
                    start.setText(getResources().getString(R.string.start));
                }
            }
        };
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
class NoRepeatRandom
{
    private int[] number = null;
    private int N = -1;
    private int size = 0;
    public NoRepeatRandom(int minVal, int maxVal)
    {
        N = (maxVal - minVal) + 1;
        number = new int[N];
        int n = minVal;
        for(int i = 0; i < N; i++)
            number[i] = n++;
        size = N;
    }

    public void Reset() { size = N; }

    // Returns -1 if none left
    public int GetRandom()
    {
        if(size <= 0) return -1;
        int index = size * (int)Math.random();
        int randNum = number[index];

        // Swap current value with current last, so we don't actually
        // have to remove anything, and our list still contains everything
        // if we want to reset
        number[index] = number[size-1];
        number[--size] = randNum;

        return randNum;
    }
}
