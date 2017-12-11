package com.example.alam.spatialtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView again;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int getScore;
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        getScore = intent.getIntExtra("score",0);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        alertDialogBuilder.setTitle("Your Score");
        alertDialogBuilder.setMessage(String.valueOf(getScore));

        alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show().getWindow();
      //  pref = getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        editor = pref.edit();
//        Intent intent = getIntent();
//        int score = intent.getIntExtra("score",0);
        again = (TextView) findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Result.this,Start.class);
                startActivity(intent);
            }
        });

    }

}
