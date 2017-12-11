package com.example.alam.spatialtest;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Start extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    LinearLayout progressPage;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        progressPage = (LinearLayout) findViewById(R.id.progressPage);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(3000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int)i*100/(3000/1000));


            }

            @Override
            public void onFinish() {
                progressPage.setVisibility(View.GONE);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.frame_layout, new SpatialFragment()).commit();

            }
        };
        Button hide;
        pref = getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putBoolean("flag",false);
        int count  = 0;
        editor.putInt("count",count);
        editor.putInt("score",count);
        editor.commit();
        final LinearLayout start;
        hide = (Button) findViewById(R.id.hideStartPage);
        start = (LinearLayout) findViewById(R.id.startPage);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.GONE);
                progressPage.setVisibility(View.VISIBLE);
                mCountDownTimer.start();

                }
        });
    }
}
