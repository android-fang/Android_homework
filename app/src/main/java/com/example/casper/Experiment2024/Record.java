package com.example.casper.Experiment2024;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

public class Record {
    private String date;
    private Bitmap image;
    private String comments;
    private Uri imageUri=null;

    public Record(String date,Bitmap img,String com){
        this.date=date;
        this.image=img;
        this.comments=com;
    }

    public Uri getImageUri()
    {
        return imageUri;
    }

    public void setImageUri(Uri uri)
    {
        this.imageUri=uri;
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

    public void setDate(String str)
    {
        date=str;
    }

    public void setComments(String str)
    {
        comments=str;
    }

    public void setImage(Bitmap bit)
    {
        image=bit;
    }
}
