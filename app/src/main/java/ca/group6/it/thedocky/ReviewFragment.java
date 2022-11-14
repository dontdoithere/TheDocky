package ca.group6.it.thedocky;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.group6.it.thedocky.databinding.FragmentReviewBinding;



public class ReviewFragment extends Fragment {

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText writtenFeedback;
    private Button saveToDatabase;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
TextView tvcustomerReviewStarRating;



    private FragmentReviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReviewViewModel ReviewViewModel =
                new ViewModelProvider(this).get(ReviewViewModel.class);

        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        //tvcustomerReviewStarRating = findViewById(R.id.customerReviewStarRating);
        name = getView().findViewById(R.id.customerReviewName);
        email = getView().findViewById(R.id.customerReviewEmailAddress);
        phone = getView().findViewById(R.id.customerReviewPhone);
        writtenFeedback = getView().findViewById(R.id.customerReviewTextboxv2);
        saveToDatabase = getView().findViewById(R.id.customerReviewButtonSubmit);
//save all info to database when user presses the submit button
        saveToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = name.getText().toString();
                String getEmail = email.getText().toString();
                String getPhone = phone.getText().toString();
                String getFeedback = writtenFeedback.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",getName);
                hashMap.put("email",getEmail);
                hashMap.put("phone",getPhone);
                hashMap.put("feedback",getFeedback);



                databaseReference.child("Users")
                        .child(getName)
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Data added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



