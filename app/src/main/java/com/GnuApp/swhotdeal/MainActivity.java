package com.GnuApp.swhotdeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;

import com.GnuApp.swhotdeal.SearchActivity;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;
    String TAG = "MainActivity";
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                setContentView(R.layout.activity_main); // theme로 스플래시 화면 구현
//            }
//        }, 2000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });
    }
}