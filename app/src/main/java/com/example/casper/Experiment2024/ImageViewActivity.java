package com.example.casper.Experiment2024;//package com.example.casper.Experiment2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.casper.Experiment2024.R;

public class ImageViewActivity extends AppCompatActivity {

    private int imageIDArrayCurrentIndex=0;

    int[] imageIDArray = {R.drawable.funny_1, R.drawable.funny_2
            , R.drawable.funny_3, R.drawable.funny_4, R.drawable.funny_5
            , R.drawable.funny_6
    };

//
    public ImageViewActivity() {
        imageIDArrayCurrentIndex = 0;
    }

    ImageView imageViewFunny;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_view);

        Button buttonPrevious = (Button)findViewById(R.id.button_previous);
        Button buttonNext =(Button) findViewById(R.id.button_next);

        imageViewFunny= (ImageView)findViewById(R.id.image_view_funny);


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        buttonPrevious.setOnClickListener(new MyButtonClickListener());
        buttonNext.setOnClickListener(new MyButtonClickListener());

    }
    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (((Button) view).getText() == "下一个") {
                imageIDArrayCurrentIndex ++;
                if(imageIDArrayCurrentIndex>imageIDArray.length-1){
                    imageIDArrayCurrentIndex=0;
                }
            } else {
                imageIDArrayCurrentIndex --;
                if(imageIDArrayCurrentIndex<0){
                    imageIDArrayCurrentIndex=imageIDArray.length-1;
                }
            }

            imageViewFunny.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);

        }
    }

}