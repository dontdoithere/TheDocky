package ca.group6.it.thedocky;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ca.group6.it.thedocky.databinding.FragmentReviewBinding;



public class ReviewFragment extends Fragment {

   private EditText name, email, message;
   private ImageButton send;


   private FirebaseDatabase firebase;


    private FragmentReviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReviewViewModel ReviewViewModel =
                new ViewModelProvider(this).get(ReviewViewModel.class);

        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //initialize
        name = binding.customerReviewName;
        email = binding.customerReviewEmailAddress;
        message = binding.customerReviewTextboxv2;
        send = binding.customerReviewButtonSubmit;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



