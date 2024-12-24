// MainActivity.java
package com.example.casper.Experiment2024;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
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

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ArrayList<Record> recordList = new ArrayList<>();
    RecordAdapter recordAdapter=new RecordAdapter(recordList);
    private static final int PICK_IMAGE_REQUEST = 1;

    ActivityResultLauncher<Intent> addRecordLauncher;
    ActivityResultLauncher<Intent> changeRecordLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 设置布局管理器

        recordList.add(new Record("青椒",imgToBitmap.drawableToBitmap(this, R.drawable.baicai),"1.5"));
        recordList.add(new Record("萝卜",imgToBitmap.drawableToBitmap(this, R.drawable.luobo) ,"2.5"));
        recordList.add(new Record("白菜", imgToBitmap.drawableToBitmap(this, R.drawable.qingjiao),"3.5"));

        recyclerView.setAdapter(recordAdapter); // 设置适配器，其中productList是一个包含商品数据的List

        registerForContextMenu(recyclerView);

        addRecordLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String date = data.getStringExtra("date"); // 获取返回的数据
                        String comment = data.getStringExtra("price"); // 获取返回的数据
                        String imageUri=data.getStringExtra("image_uri");
                        Uri uri=Uri.parse(imageUri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                            Record record=new Record(date, bitmap,comment);
                            record.setImageUri(uri);
                            recordList.add(record);
                            recordAdapter.notifyItemInserted(recordList.size());

                        } catch (IOException e) {
                           e.printStackTrace();
                        }




                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );

        changeRecordLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String date = data.getStringExtra("date"); // 获取返回的数据
                        String comment = data.getStringExtra("comment"); // 获取返回的数据
                        String imageUri=data.getStringExtra("image_uri");
                        int position=data.getIntExtra("position",0);
                        Uri uri=Uri.parse(imageUri);
                        Record record=recordList.get(position);
                        if(uri!=null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                                record.setImage(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        record.setDate(date);
                        record.setComments(comment);
                        recordAdapter.notifyItemChanged(position);


                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //item.getOrder()
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(MainActivity.this, RecordAdd.class);
                addRecordLauncher.launch(intent);

                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordList.remove(item.getOrder());
                        recordAdapter.notifyItemRemoved(item.getOrder());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
            case 2:
                Intent intentChange = new Intent(MainActivity.this,RecordAdd.class);
                Record record= recordList.get(item.getOrder());

                intentChange.putExtra("date",record.getDate());
                intentChange.putExtra("comment",record.getComments());
                intentChange.putExtra("position",item.getOrder());

                intentChange.putExtra("uri",record.getImageUri());

                changeRecordLauncher.launch(intentChange);

                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }



}

