//Semen Dyakonov. N01391812
//Binay Garlapati. N01368870
//Nissan Rayappu N01435235
//
package ca.group6.it.thedocky.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.group6.it.thedocky.MoreInfo;
import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.User;
import ca.group6.it.thedocky.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference reference;
    private FirebaseUser user;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private TextView weightData;

    private Button moreInfoBtn;

    private TextView lcdData;
    double latitude;
    double longitude;
    private String userID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //Assign weight_data to variable
        View view =binding.getRoot();
        weightData = view.findViewById(R.id.weight_data);
        lcdData = view.findViewById(R.id.lcd_data);
        moreInfoBtn = view.findViewById(R.id.more_info_btn);

        // Reference to user from Database

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if ( binding != null){
               binding.usernameValueHome.setText(user.username);
               binding.textView9.setText("$"+user.balance);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Reference to LCD Screen information from Database
        DatabaseReference lcdSensorRef = FirebaseDatabase.getInstance().getReference("LCD Screen");

        lcdSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Object lcdObj = snapshot.child("LCD").child("lcd").getValue();
                    if (lcdObj != null) {
                        String lcd = lcdObj.toString();
                        lcdData.setText(lcd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Set an OnClickListener for the moreInfoBtn
        moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoreInfo.class);
                startActivity(intent);
            }
        });

        // Reference to Weight Sensor information from database
        DatabaseReference weightSensorRef = FirebaseDatabase.getInstance().getReference("Weight Sensor");

        weightSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Object weightObj = snapshot.child("Weight").child("Weight").getValue();
                    if (weightObj != null) {
                        String weight = weightObj.toString();
                        weightData.setText(weight);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        binding.starRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), TimerActivity.class));

            }
        });

        fetchLocation();

        return binding.getRoot();
    }

    //Firebase db user data
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getActivity(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addressList.size() > 0) {

                            binding.location.setText(addressList.get(0).getLocality());
                            latitude = addressList.get(0).getLatitude();
                            longitude = addressList.get(0).getLongitude();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        fetchLocation();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}