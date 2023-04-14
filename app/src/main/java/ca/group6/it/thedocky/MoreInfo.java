package ca.group6.it.thedocky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.group6.it.thedocky.databinding.FragmentHomeBinding;


public class MoreInfo extends AppCompatActivity {

    public TextView weightInfo;
    private FragmentHomeBinding binding;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);


        weightInfo = findViewById(R.id.weight_more_info);
        DatabaseReference weightSensorRef = FirebaseDatabase.getInstance().getReference("Weight Sensor");


        weightSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Object weightObj = snapshot.child("Weight").child("Weight").getValue();
                    if (weightObj != null) {
                        String weight = weightObj.toString();
                        weightInfo.setText(weight);
                    }
                }
            }


            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}