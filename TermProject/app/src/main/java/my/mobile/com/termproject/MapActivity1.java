package my.mobile.com.termproject;

/**
 * Created by chaminjun on 2016. 11. 22..
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapActivity1 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    MyDB mydb1 = new MyDB(this);
    ArrayList<MyDataBaseIntent> mbi = new ArrayList<>();
    String category1[] = {"공부", "회의", "운동", "식사", "etc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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
        map = googleMap;

        mydb1.showMyMap(mbi);

        for (int i = 0; i < mbi.size(); i++) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(mbi.get(i).latitude, mbi.get(i).longitude));
            marker.title(category1[mbi.get(i).category]);
            marker.snippet(mbi.get(i).whatido);
            marker.draggable(true);

            map.addMarker(marker);
        }
        mydb1.close();

    }
}
