package com.example.casper.Experiment2024;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    ArrayList<SparkPos> Pos;
    TencentMap tencentMap;
    LatLng clickPos;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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

    private com.tencent.tencentmap.mapsdk.maps.TextureMapView mapView = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        Pos=new SparkPosBank().LoadPos(requireActivity());
        for (int i = 0; i < Pos.size(); i++) {
            addMarker(Pos.get(i).getLatLng());
        }
        tencentMap= mapView.getMap();


        // 设置地图点击监听器
        tencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 在这里处理点击事件，latLng是点击位置的经纬度
                clickPos=latLng;


                // 你可以在这里添加自定义逻辑，比如显示标记、弹出对话框等
            }
        });

        // Set long click listener
        tencentMap.setOnMapLongClickListener(new TencentMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                showOptionsMenu(clickPos);

            }
        });

        Button mapButtonLeft=rootView.findViewById(R.id.mapButtonLeft);
        mapButtonLeft.setOnClickListener(v ->
        {

            //设置一个新的地图中心点标注
            LatLng newLatLng = tencentMap.getCameraPosition().target;

            newLatLng.longitude -=0.02;
            //把地图变换到指定的状态,生成一个把地图移动到指定的经纬度到屏幕中心的状态变化对象
            tencentMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
        });

        Button mapButtonRight=rootView.findViewById(R.id.mapButtonRight);
        mapButtonRight.setOnClickListener(v ->
        {

            //设置一个新的地图中心点标注
            LatLng newLatLng = tencentMap.getCameraPosition().target;

            newLatLng.longitude +=0.02;
            //把地图变换到指定的状态,生成一个把地图移动到指定的经纬度到屏幕中心的状态变化对象
            tencentMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
        });

        Button mapButtonUp=rootView.findViewById(R.id.mapButtonUp);
        mapButtonUp.setOnClickListener(v ->
        {

            //设置一个新的地图中心点标注
            LatLng newLatLng = tencentMap.getCameraPosition().target;

            newLatLng.latitude +=0.02;
            //把地图变换到指定的状态,生成一个把地图移动到指定的经纬度到屏幕中心的状态变化对象
            tencentMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
        });

        Button mapButtonDown=rootView.findViewById(R.id.mapButtonDown);
        mapButtonDown.setOnClickListener(v ->
        {

            //设置一个新的地图中心点标注
            LatLng newLatLng = tencentMap.getCameraPosition().target;

            newLatLng.latitude -=0.02;
            //把地图变换到指定的状态,生成一个把地图移动到指定的经纬度到屏幕中心的状态变化对象
            tencentMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
        });


        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



    private void showOptionsMenu(LatLng pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("选择操作")
                .setItems(new String[]{"点亮", "取消"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 点亮
                                addMarker(pos);
                                break;
                            case 1: // 取消
                                removeMarker(pos);
                                break;


                        }
                    }
                });
        builder.show();
    }

    private Map<LatLng, Marker> markers = new HashMap<>();

    private void addMarker(LatLng latLng) {
        if (!markers.containsKey(latLng)) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.qiqiu)); // 替换为你的标记图片
            Marker marker = tencentMap.addMarker(markerOptions);
            markers.put(latLng, marker);
            new SparkPosBank().SavePos(getActivity(),Pos);
        }
    }

    private void removeMarker(LatLng latLng) {
        Marker marker = markers.remove(latLng);
        if (marker != null) {
            marker.remove();
            for (int i = 0; i < Pos.size(); i++) {
                if(Pos.get(i).getLatLng()==latLng)
                {
                    Pos.remove(i);
                    return;
                }
            }

            new SparkPosBank().SavePos(getActivity(),Pos);
        }
    }


}