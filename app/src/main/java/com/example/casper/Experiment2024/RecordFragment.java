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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class RecordFragment extends Fragment {
    public RecordFragment()
    {

    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    ImageView imageView;
    ArrayList<Record> recordList;
    RecordAdapter recordAdapter;
    private static final int PICK_IMAGE_REQUEST = 1;


    ActivityResultLauncher<Intent> addRecordLauncher;
    ActivityResultLauncher<Intent> changeRecordLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.record_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // 设置布局管理器

        recordList=new DataBank().LoadRecords(requireActivity());
        if(recordList.isEmpty())
        {
            recordList.add(new Record("青椒",
                    imgToBitmap.drawableToBitmap
                            (requireContext(), R.drawable.baicai),"1.5"));
            new DataBank().SaveRecords(requireActivity(),recordList);

            Toast.makeText(requireActivity(),"加载成功---"+recordList.size(),Toast.LENGTH_SHORT).show();

        }

        recordAdapter=new RecordAdapter(recordList);
        recyclerView.setAdapter(recordAdapter); // 设置适配器，其中productList是一个包含商品数据的List

        registerForContextMenu(recyclerView);



        addRecordLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String date = data.getStringExtra("date"); // 获取返回的数据
                        String comment = data.getStringExtra("comment"); // 获取返回的数据
                        String imageUri=data.getStringExtra("image_uri");
                        Uri uri=Uri.parse(imageUri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);

                            Record record=new Record(date, bitmap,comment);
                            record.setImageUri(uri);
                            recordList.add(record);
                            recordAdapter.notifyItemInserted(recordList.size());
                            new DataBank().SaveRecords(requireActivity(),recordList);
                            Toast.makeText(requireActivity(),"保存成功---"+recordList.size(),Toast.LENGTH_SHORT).show();

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
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                                record.setImage(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        record.setDate(date);
                        record.setComments(comment);
                        recordAdapter.notifyItemChanged(position);
                        new DataBank().SaveRecords(requireActivity(),recordList);
                        Toast.makeText(requireActivity(),"保存成功---"+recordList.size(),Toast.LENGTH_SHORT).show();


                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
        return rootView;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //item.getOrder()
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(requireActivity(), RecordAdd.class);
                addRecordLauncher.launch(intent);

                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordList.remove(item.getOrder());
                        recordAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().SaveRecords(requireActivity(),recordList);
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
                Intent intentChange = new Intent(requireActivity(),RecordAdd.class);
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

