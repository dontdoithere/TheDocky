package ca.group6.it.thedocky;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.group6.it.thedocky.databinding.FragmentReviewBinding;



public class ReviewFragment extends Fragment {

    DatabaseReference databaseReference;

    private FirebaseAuth auth;

    private FragmentReviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReviewViewModel ReviewViewModel =
                new ViewModelProvider(this).get(ReviewViewModel.class);

        databaseReference = FirebaseDatabase.getInstance().getReference("Review");//path

        ReviewViewModel reviewViewModel = new ReviewViewModel();

        String uid=databaseReference.push().getKey();

        reviewViewModel.setQid("uid");
        reviewViewModel.setName("Enter the user name");
        reviewViewModel.setAddress("Enter the address");
        reviewViewModel.setEmail("Enter the email address");
        reviewViewModel.setPhonenumber("Enter the phone number");
        reviewViewModel.setFeedback("Enter the feedback");




        databaseReference.child(uid).setValue(reviewViewModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });


        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }

    }


