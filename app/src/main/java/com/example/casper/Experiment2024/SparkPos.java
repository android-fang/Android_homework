package com.example.casper.Experiment2024;

import android.graphics.Bitmap;
import android.net.Uri;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import java.io.Serializable;

public class SparkPos implements Serializable {
    private LatLng latLng;

    public SparkPos(LatLng lat){
        this.latLng=lat;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }

    public void setLatLng(LatLng lat)
    {
        this.latLng=lat;
    }

}
