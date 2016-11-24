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


public class MapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    MyDB mydb2 = new MyDB(this);
    ArrayList<MyDataBaseIntent> mbi = new ArrayList<>();
    String category2[] = {"공연", "시위", "광고", "대회", "etc"};

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
        map = googleMap;

        mydb2.showMyMap(mbi);

        for (int i = 0; i < mbi.size(); i++) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(mbi.get(i).latitude, mbi.get(i).longitude));
            marker.title(category2[mbi.get(i).category]);
            marker.snippet(mbi.get(i).whatido);
            marker.draggable(true);

            map.addMarker(marker);
        }
        mydb2.close();

    }
}
