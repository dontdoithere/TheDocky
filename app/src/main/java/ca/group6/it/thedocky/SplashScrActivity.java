package ca.group6.it.thedocky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import ca.group6.it.thedocky.ui.login.LoginActivity;
import ca.group6.it.thedocky.ui.login.SignUpActivity;

public class SplashScrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScrActivity.this, LoginActivity.class); //SignUpActivity
                startActivity(intent);
                finish();
            }
        },3000);

    }
}