package com.example.casper.Experiment2024;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecordAdd extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri imageUri;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_add);

        Intent getintent=getIntent();
        if(getintent!=null)
        {
            position=getintent.getIntExtra("position",0);
            String date=getintent.getStringExtra("date");
            String comment=getintent.getStringExtra("comment");

            EditText dateEditText=findViewById(R.id.record_date_edittext);
            EditText commentEditText=findViewById(R.id.record_comment_edittext);
            dateEditText.setText(date);
            commentEditText.setText(comment);

            ImageView imageView=findViewById(R.id.record_image_view);
            String uriString=getintent.getStringExtra("uri");
            if(uriString!=null)
            {
                Uri uri=Uri.parse(uriString);
                if (uri!=null)
                {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);
                        imageUri=uri;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }



        }

        Button imageAdd=findViewById(R.id.record_image_button);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });

        Button buttonOk= findViewById(R.id.button_add_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText date= findViewById(R.id.record_date_edittext);
                EditText comment= findViewById(R.id.record_comment_edittext);
                intent.putExtra("date", date.getText().toString());
                intent.putExtra("comment",comment.getText().toString());



                intent.putExtra("image_uri", imageUri.toString());
                intent.putExtra("position",position);

                setResult(Activity.RESULT_OK, intent);
                RecordAdd.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ImageView imageView=findViewById(R.id.record_image_view);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}