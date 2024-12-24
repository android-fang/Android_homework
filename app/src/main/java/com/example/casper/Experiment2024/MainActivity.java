// MainActivity.java
package com.example.casper.Experiment2024;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 设置布局管理器
        ArrayList<Record> recordList = new ArrayList<>();
        recordList.add(new Record("青椒",imgToBitmap.drawableToBitmap(this, R.drawable.baicai),"1.5"));
        recordList.add(new Record("萝卜",imgToBitmap.drawableToBitmap(this, R.drawable.luobo) ,"2.5"));
        recordList.add(new Record("白菜", imgToBitmap.drawableToBitmap(this, R.drawable.qingjiao),"3.5"));

        recyclerView.setAdapter(new RecordAdapter(recordList)); // 设置适配器，其中productList是一个包含商品数据的List


        registerForContextMenu(recyclerView);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //item.getOrder()
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,""+item.getOrder(),Toast.LENGTH_SHORT).show();


                break;
            case 1:
                Toast.makeText(this,""+item.getOrder(),Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,""+item.getOrder(),Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }



}

