package ca.group6.it.thedocky.ui.home;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import ca.group6.it.thedocky.User;
import ca.group6.it.thedocky.databinding.ActivityTimerBinding;

public class TimerActivity extends AppCompatActivity {

    private ActivityTimerBinding binding;
    float balance= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users")
                .child(FirebaseAuth.getInstance().getUid());

        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceUser.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                balance = user.balance;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.starRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.timer.start();

            }
        });

        binding.stopDockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                balance -=0.01;

                Map<String ,Object> map = new HashMap<>();
                map.put("balance",balance);
                databaseReference.updateChildren(map);
                Toast.makeText(TimerActivity.this, "Docking finish payment withdraw from your balance", Toast.LENGTH_SHORT).show();
               finish();
            }
        });

    }


}