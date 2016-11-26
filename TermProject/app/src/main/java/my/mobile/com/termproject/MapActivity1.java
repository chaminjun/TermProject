package my.mobile.com.termproject;

/**
 * Created by chaminjun on 2016. 11. 22..
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class MapActivity1 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    MyDB mydb1 = new MyDB(this);
    ArrayList<MyDataBaseIntent> mbi = new ArrayList<>();
    String category1[] = {"공부", "회의", "운동", "식사", "etc"};
    private PolylineOptions polylineOptions = new PolylineOptions();
    Double latitude = 37.547423;
    Double longitude = 126.932058;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        // 일부 단말의 문제로 인해 초기화 코드 추가
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng curPoint = new LatLng(latitude, longitude);

        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        mydb1.showMyMap(mbi);

        for (int i = 0; i < mbi.size(); i++) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(mbi.get(i).latitude, mbi.get(i).longitude));
            marker.title(category1[mbi.get(i).category]);
            marker.snippet(mbi.get(i).whatido);
            marker.draggable(true);

            map.addMarker(marker);
        }
        if(ShowActivity.querycount == 0){
            polylineOptions.color(Color.RED);
            polylineOptions.width(5);
            try{
                for(int i = 0; i < mbi.size(); i++) {
                    polylineOptions.add(new LatLng(mbi.get(i).latitude, mbi.get(i).longitude));
                    map.addPolyline(polylineOptions);
                }
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        mydb1.close();
    }
}
