package my.mobile.com.termproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import static my.mobile.com.termproject.RecordFragment1.flag1;
import static my.mobile.com.termproject.RecordFragment2.flag2;

/**
 * Created by chaminjun on 2016. 11. 22..
 */

public class RecordActivity extends AppCompatActivity{

    FragmentManager manager;            //fragment를 관리하는 객체
    FragmentTransaction transaction;    //추가, 삭제, replace를 관리하는 객체

    Fragment frag1, frag2;

    Intent gomap1, gomap2;

    Button photo_btn, gallery_btn, save_btn, showmap_btn;
    Button record_btn_1, record_btn_2;
    Button btnstart, btnstop, btnreset;

    ImageView gallery_img;
    Chronometer chrono;

    Double latitude = 37.547423;
    Double longitude = 126.932058;

    int hour = 0;
    int minute = 0;
    String photo_str = "";

//    MyDB mydb1 = new MyDB(this);
//    MyDB mydb2 = new MyDB(this);

    Calendar timec;

    private static final int REQ_CODE_PICK_PICTURE = 2;

    static int record_chrono_time_flag = 0;
    static float record_chrono_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        manager = (FragmentManager)getFragmentManager();
        frag1 = new RecordFragment1();
        frag2 = new RecordFragment2();

        gomap1 = new Intent(getApplicationContext(), MapActivity1.class);
        gomap2 = new Intent(getApplicationContext(), MapActivity2.class);

        btnstart = (Button)findViewById(R.id.btnstart);
        btnstop = (Button)findViewById(R.id.btnstop);
        btnreset = (Button)findViewById(R.id.btnreset);
        record_btn_1 = (Button)findViewById(R.id.record_btn_1);
        record_btn_2 = (Button)findViewById(R.id.record_btn_2);
        photo_btn = (Button)findViewById(R.id.photo_btn);
        gallery_btn = (Button)findViewById(R.id.gallery_btn);
        save_btn = (Button)findViewById(R.id.save_btn);
        showmap_btn = (Button)findViewById(R.id.showmap_btn);

        gallery_img = (ImageView)findViewById(R.id.gallery_img);

        chrono = (Chronometer)findViewById(R.id.chrono);

        save_btn.setEnabled(false);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stoppedMilliseconds = 0;

                String chronoText = chrono.getText().toString();
                String array[] = chronoText.split(":");
                if (array.length == 2) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                            + Integer.parseInt(array[1]) * 1000;
                } else if (array.length == 3) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                            + Integer.parseInt(array[1]) * 60 * 1000
                            + Integer.parseInt(array[2]) * 1000;
                }

                chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
                chrono.start();
                save_btn.setEnabled(false);
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.stop();
                float stoppedMilliseconds = 0;

                String chronoText = chrono.getText().toString();
                String array[] = chronoText.split(":");
                if (array.length == 2) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60
                            + Integer.parseInt(array[1]);
                } else if (array.length == 3) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60
                            + Integer.parseInt(array[1]) * 60
                            + Integer.parseInt(array[2]);
                }
                record_chrono_time_flag = 1;
                record_chrono_time = stoppedMilliseconds;
                save_btn.setEnabled(true);
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
                nowTime();
                save_btn.setEnabled(true);
                if(flag1 == 1 && flag2 == 0) {
                    MainActivity.mydb1.insert(hour, minute, latitude, longitude, RecordFragment1.spinnernum1,
                            RecordFragment1.edit01.getText().toString(), record_chrono_time, photo_str);
                    MainActivity.mydb1.close();
                    RecordFragment1.edit01.setText("");
                }else if(flag1 == 0 && flag2 == 1){
                    MainActivity.mydb2.insert(hour, minute, latitude, longitude, RecordFragment2.spinnernum2,
                            RecordFragment2.edit02.getText().toString(), record_chrono_time, photo_str);
                    MainActivity.mydb2.close();
                    RecordFragment2.edit02.setText("");
                }
            }
        });
        showmap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == 1 && flag2 == 0) {
                    startActivity(gomap1);
                }else if(flag1 == 0 && flag2 == 1){
                    startActivity(gomap2);
                }
            }
        });
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);

            }
        });

        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });
        startLocationService();
        checkDangerousPermissions();
    }
    public void rOnclick(View v) {
        switch (v.getId()) {
            case R.id.record_btn_1:
                transaction = manager.beginTransaction();       //transaction 시작하겠다.
                transaction.remove(frag1);
                transaction.remove(frag2);
                transaction.add(R.id.record_linear, frag1);         //fragment 추가하겠다.
                transaction.commit();                           //transaction을 실행해라.
                break;
            case R.id.record_btn_2:
                transaction = manager.beginTransaction();
                transaction.remove(frag1);
                transaction.remove(frag2);
                transaction.add(R.id.record_linear, frag2);
                transaction.commit();
                break;
        }
    }
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            // GPS 기반 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크 기반 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void nowTime(){
        timec = Calendar.getInstance();
        hour = timec.get(Calendar.HOUR_OF_DAY);
        minute = timec.get(Calendar.MINUTE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PICK_PICTURE) {
            Uri iuri;
            try{
                iuri= data.getData();
                photo_str = iuri.toString();
                MediaStore.Images.Media.getBitmap( getContentResolver(), iuri);
                gallery_img.setImageURI(iuri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

}






