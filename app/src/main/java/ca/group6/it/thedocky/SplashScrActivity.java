package ca.group6.it.thedocky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.group6.it.thedocky.ui.login.LoginActivity;
import ca.group6.it.thedocky.ui.login.SignUpActivity;

public class SplashScrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser == null){

                Intent intent = new Intent(SplashScrActivity.this, LoginActivity.class); //LoginActivity
                startActivity(intent);
                finish();}else {
                    Intent intent = new Intent(SplashScrActivity.this, MainActivity.class);//LoginActivity
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }
}