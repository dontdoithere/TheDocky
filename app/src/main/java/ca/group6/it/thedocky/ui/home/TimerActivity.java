package ca.group6.it.thedocky.ui.home;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import ca.group6.it.thedocky.TransactionHistory;
import ca.group6.it.thedocky.User;
import ca.group6.it.thedocky.databinding.ActivityTimerBinding;

public class TimerActivity extends AppCompatActivity {

    private ActivityTimerBinding binding;
    float balance= 0;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    String city;


    private static final String TAG = "TimerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
fetchLocation();
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

                int timePassed = (int) (SystemClock.elapsedRealtime() - binding.timer.getBase());

                float price=  ( (timePassed/1000 )* 0.01f);

                balance = balance - price ;
                Log.d(TAG, "onClick: "+(SystemClock.elapsedRealtime() -binding.timer.getBase()  ));
                Log.d(TAG, "onClick: "+price);

                Map<String ,Object> map = new HashMap<>();
                map.put("balance",balance);
                databaseReference.updateChildren(map);
                Toast.makeText(TimerActivity.this, "Docking finished payment withdraw from your balance", Toast.LENGTH_SHORT).show();
                String posterId = FirebaseAuth.getInstance().getUid();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

                Date date = new Date();

                String format = dateFormat.format(date);



                String postId = String.valueOf(System.currentTimeMillis());
                TransactionHistory transactionHistory = new TransactionHistory(price,format,city,postId);
        FirebaseDatabase.getInstance().getReference("TransactionHistory")
                .child(posterId).child(postId)
                .setValue(transactionHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            finish();

                        } else {
                        }

                    }
                });
            }

        });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {

                    Geocoder geocoder = new Geocoder(TimerActivity.this, Locale.getDefault());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addressList.size() > 0) {
city = addressList.get(0).getLocality();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        fetchLocation();
                    }
                }
            }
        });
    }

}