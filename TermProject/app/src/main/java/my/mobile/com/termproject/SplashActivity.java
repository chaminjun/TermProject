package my.mobile.com.termproject;

/**
 * Created by chaminjun on 2016. 11. 22..
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    long Delay = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        Timer RunSplash = new Timer();

        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent myIntent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        };
        RunSplash.schedule(ShowSplash, Delay);
    }
}
