// MainActivity.java
package com.example.casper.Experiment2024;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView number_view=findViewById(R.id.numberview);

        Button button1=findViewById(R.id.button1);
        button1.setOnClickListener(v ->
        {
            str+="1";
            number_view.setText(str);
        });

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(v ->
        {
            str+="2";
            number_view.setText(str);
        });
        Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(v ->
        {
            str+="3";
            number_view.setText(str);
        });
        Button button4=findViewById(R.id.button4);
        button4.setOnClickListener(v ->
        {
            str+="4";
            number_view.setText(str);
        });
        Button button5=findViewById(R.id.button5);
        button5.setOnClickListener(v ->
        {
            str+="5";
            number_view.setText(str);
        });
        Button button6=findViewById(R.id.button6);
        button6.setOnClickListener(v ->
        {
            str+="6";
            number_view.setText(str);
        });
        Button button7=findViewById(R.id.button7);
        button7.setOnClickListener(v ->
        {
            str+="7";
            number_view.setText(str);
        });
        Button button8=findViewById(R.id.button8);
        button8.setOnClickListener(v ->
        {
            str+="8";
            number_view.setText(str);
        });
        Button button9=findViewById(R.id.button9);
        button9.setOnClickListener(v ->
        {
            str+="9";
            number_view.setText(str);
        });


        AlertDialog.Builder Dialog_Builder = new AlertDialog.Builder(this);

        // 设置对话框的标题
        Dialog_Builder.setTitle("标题");

        // 设置对话框的消息
        Dialog_Builder.setMessage("是否清空数字？");

        // 设置确定按钮及其响应函数
        Dialog_Builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里处理确定按钮的点击事件
                Toast.makeText(MainActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
                str="";
                number_view.setText(str);
            }
        });

        // 设置取消按钮及其响应函数
        Dialog_Builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里处理取消按钮的点击事件
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();

            }
        });


        Button button_clean = findViewById(R.id.buttonview_clean);
        button_clean.setOnClickListener(v -> {
            AlertDialog dialog=Dialog_Builder.create();
            dialog.show();

        });


        Button button_input = findViewById(R.id.buttonview_input);
        button_input.setOnClickListener(v -> {

            Toast.makeText(this,"You have inputed:"+str,Toast.LENGTH_SHORT).show();
        });


    }
}