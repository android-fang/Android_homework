package com.example.casper.Experiment2024;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class Record {
    private String date;
    private Bitmap image;
    private String comments;

    public Record(String date,Bitmap img,String com){
        this.date=date;
        this.image=img;
        this.comments=com;
    }

    public String getDate()
    {
        return date;
    }

    public String getComments()
    {
        return comments;
    }

    public Bitmap getImage()
    {
        return image;
    }
}
