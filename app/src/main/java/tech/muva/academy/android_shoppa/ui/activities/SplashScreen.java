package tech.muva.academy.android_shoppa.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.auth.LoginActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class SplashScreen extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();
    private SharedPreferencesConfig sharedPreferencesConfig;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        status = sharedPreferencesConfig.readBuyerStatus();




        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                try {
                    if ( !status.contentEquals("Active")){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else {

                        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SplashScreen.this,"Welcome To Shoppy",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ignored)
                {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 2 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
