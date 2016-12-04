package my.mobile.com.termproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import static my.mobile.com.termproject.RecordFragment1.flag1;
import static my.mobile.com.termproject.RecordFragment2.flag2;

/**
 * Created by chaminjun on 2016. 11. 22..
 */

public class RecordActivity extends AppCompatActivity{

    //fragment를 관리하는 객체
    FragmentManager manager;
    //추가, 삭제, replace를 관리하는 객체
    FragmentTransaction transaction;

    Fragment frag1, frag2;

    Intent gomap1, gomap2;

    Button photo_btn, gallery_btn, save_btn, showmap_btn;
    Button record_btn_1, record_btn_2;
    ImageView imgstart, imgstop, imgreset;

    ImageView gallery_img;
    Chronometer chrono;

    Double latitude = 37.547423;
    Double longitude = 126.932058;

    int hour = 0;
    int minute = 0;
    String photo_str = "";

    Calendar timec;

    private static final int REQ_CODE_PICK_PICTURE = 2;

    static int record_chrono_time_flag = 0;
    static float record_chrono_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        //한 일과 이벤트 각각의 fragment
        manager = (FragmentManager)getFragmentManager();
        frag1 = new RecordFragment1();
        frag2 = new RecordFragment2();

        //한 일과 이벤트에 따라 각기 다른 MapActivity를 보여주도록하는 intent
        gomap1 = new Intent(getApplicationContext(), MapActivity1.class);
        gomap2 = new Intent(getApplicationContext(), MapActivity2.class);

        //Chronometer객체화
        chrono = (Chronometer)findViewById(R.id.chrono);

        //Chronometer ImageView 3개
        imgstart = (ImageView)findViewById(R.id.imgstart);
        imgstop = (ImageView)findViewById(R.id.imgstop);
        imgreset = (ImageView)findViewById(R.id.imgreset);


        //한 일과 이벤트에 따라 다른 fragment를 보여주기 위한 버튼
        record_btn_1 = (Button)findViewById(R.id.record_btn_1);
        record_btn_2 = (Button)findViewById(R.id.record_btn_2);

        //사진찍기, 갤러리 불러오기, 저장하기, 맵으로 보여주기 버튼
        photo_btn = (Button)findViewById(R.id.photo_btn);
        gallery_btn = (Button)findViewById(R.id.gallery_btn);
        save_btn = (Button)findViewById(R.id.save_btn);
        showmap_btn = (Button)findViewById(R.id.showmap_btn);

        //갤러리에서 이미지를 잘 가지고 있는지 확인할 수 있는 ImageView설정
        gallery_img = (ImageView)findViewById(R.id.gallery_img);

        //일반적으로 저장을 못하고 시간이 입력되었을때만 클릭가능하도록 false로 설정 해놓음
        save_btn.setEnabled(false);

        //Chronometer 시작 버튼
        imgstart.setOnClickListener(new View.OnClickListener() {
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
        /*Chronometer 중지 버튼
          중지를 했을때만 저장할 수 있게끔 record_chrono_time_flag를 이때 1로 바꿔주고
          save_btn.setEnabled(true)로 변경
         */
        imgstop.setOnClickListener(new View.OnClickListener() {
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
        //Chronometer 리셋 버튼
        imgreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
            }
        });


        /* 저장버튼 : 위치요청 시작 및 현재 시간 알아옴
                    flag1과 flag2를 통해 어떤 db에 저장할지 결정
         */
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

        //맵으로 보여주기 버튼 : flag1과 flag2를 통해 어떤 MapActivity로 갈지 설정
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

        //사진 찍기 버튼
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);

            }
        });

        //갤러리 불러오기 버튼
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
    //한 일과 이벤트 각각의 버튼에 따라 어떻게 fragment를 보여줄지
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

    //현재 시간의 시, 분, 초를 보여주는 함수
    public void nowTime(){
        timec = Calendar.getInstance();
        hour = timec.get(Calendar.HOUR_OF_DAY);
        minute = timec.get(Calendar.MINUTE);
    }
    //갤러리에서 가져온 이미지를 uri형식으로 저장
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






