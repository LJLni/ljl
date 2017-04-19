package com.example.administrator.yoursecret.Discover;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.administrator.yoursecret.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    private MapView mapView;
    private BaiduMap mBaiduMap;

    LocationManager locationManager;

    Marker markerA;
    Marker markerB;
    Marker markerC;
    Marker markerD;
    Marker markerE;
    Marker markerF;

    LatLng llA=new LatLng(31.544715, 120.365398);
    LatLng llB=new LatLng(31.545715, 120.366398);
    LatLng llC=new LatLng(31.546715, 120.367398);
    LatLng llD=new LatLng(31.547715, 120.368398);
    LatLng llE=new LatLng(31.548715, 120.369398);
    LatLng llF=new LatLng(31.543715, 120.364398);

    BitmapDescriptor bitmap;

    private InfoWindow mInfoWindow;
    private View mPopup;

    Marker markerMy=null;


    public DiscoverFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mapView = (MapView) getActivity().findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();

        //卫星图
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        final MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(14);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_anchor);


        getLocation();

        MarkerOptions ooA=new MarkerOptions().position(llA).icon(bitmap).zIndex(19).draggable(true);
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
        markerA=(Marker)mBaiduMap.addOverlay(ooA);
        ooA=new MarkerOptions().position(llB).icon(bitmap);
        markerB=(Marker)mBaiduMap.addOverlay(ooA);
        ooA=new MarkerOptions().position(llC).icon(bitmap);
        markerC=(Marker)mBaiduMap.addOverlay(ooA);
        ooA=new MarkerOptions().position(llD).icon(bitmap);
        markerD=(Marker)mBaiduMap.addOverlay(ooA);
        ooA=new MarkerOptions().position(llE).icon(bitmap);
        markerE=(Marker)mBaiduMap.addOverlay(ooA);
        ooA=new MarkerOptions().position(llF).icon(bitmap);
        markerF=(Marker)mBaiduMap.addOverlay(ooA);

        markerVisiable(markerA);
        markerVisiable(markerB);
        markerVisiable(markerC);
        markerVisiable(markerD);
        markerVisiable(markerE);
        markerVisiable(markerF);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(llA);//设置圆心坐标
        circleOptions.fillColor(0x906495ED);//圆的填充颜色
        circleOptions.radius(150);//设置半径
        //circleOptions.stroke(new Stroke(5, 0xAA00FF00));//设置边框
        mBaiduMap.addOverlay(circleOptions);

        mPopup= LayoutInflater.from(getActivity()).inflate(R.layout.popuplayou,null);
        //Bitmap bitmap=new BitmapDescriptorFactory.fromResource(R.drawable.chunfen);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if(marker==markerA||marker==markerB||
                        marker==markerC||marker==markerD||
                        marker==markerE||marker==markerF){
                    mPopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBaiduMap.hideInfoWindow();
                        }
                    });


                    TextView textView=(TextView)mPopup.findViewById(R.id.title);
                    ImageView imageView=(ImageView)mPopup.findViewById(R.id.image);
                    TextView textView1=(TextView)mPopup.findViewById(R.id.desc);
                    Button button1=(Button)mPopup.findViewById(R.id.buplish);
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO
                        }
                    });
                    Button button2=(Button)mPopup.findViewById(R.id.check);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO
                        }
                    });
                    textView.setText("主题：muhoosb");
                    Bitmap image= BitmapFactory.decodeResource(getResources(),R.drawable.chunfen);
                    imageView.setImageBitmap(image);
                    textView1.setText("描述：muhoo is a reall sb");
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(mPopup,ll,-47);
                    mBaiduMap.showInfoWindow(mInfoWindow);

                }
                return true;
            }
        });


    }

    public void getLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            }
        } else {
            return;
        }

        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            updataView(location);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updataView(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                    try {
                        updataView(locationManager.getLastKnownLocation(provider));
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onProviderDisabled(String provider) {
                    updataView(null);
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void Mark(double Latitude, double Longitude) {

        LatLng point = new LatLng(Latitude, Longitude);
        //坐标转换
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(point);
        LatLng desLatLng = converter.convert();

        if (markerMy==null) {
            //BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
            //MarkerOptions oob = new MarkerOptions().position(desLatLng).icon(bdA).zIndex(19);
            CircleOptions circleOptions = new CircleOptions().center(desLatLng).fillColor(0x906495ED).radius(150);
            markerMy = (Marker) mBaiduMap.addOverlay(circleOptions);
        }else{
            markerMy.setPosition(desLatLng);
        }
        MapStatusUpdate mapStatusUpdatePoint = MapStatusUpdateFactory.newLatLng(desLatLng);
        mBaiduMap.setMapStatus(mapStatusUpdatePoint);

    }

    public void updataView(Location location) {
        if (location != null) {
            mapView.refreshDrawableState();
            Mark(location.getLatitude(), location.getLongitude());
        } else {
            //Mark(11.123456, 12.123456);
            Toast.makeText(getContext(), "无GPS", Toast.LENGTH_SHORT).show();

        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case 123:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission Granted
                        getLocation();
                    } else {
                        // Permission Denied
                        Toast.makeText(getContext(), "Location Denied", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
        //设置marker对象的可见性
        public void markerVisiable(Marker marker){
            float[] result=new float[1];
            Location.distanceBetween(31.544715, 120.365398,marker.getPosition().latitude,marker.getPosition().longitude,result);
            if(result[0]>150){
                marker.setVisible(false);
            }
        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
