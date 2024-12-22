// MainActivity.java
package com.example.casper.Experiment2024;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str=getString(R.string.app_name);
        TextView textView = findViewById(R.id.textView);
        textView.setText(R.string.view_name2);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {

        });
    }
}