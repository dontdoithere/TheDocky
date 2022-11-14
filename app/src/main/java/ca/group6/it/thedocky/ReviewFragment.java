package ca.group6.it.thedocky;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import ca.group6.it.thedocky.databinding.FragmentReviewBinding;



public class ReviewFragment extends Fragment {

    public ReviewFragment() {
    }
    RatingBar rbStars;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rbStars = rbStars.findViewById(R.id.rbStars);

        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0)
                {

                }
            }
        });
    }
    private FragmentReviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReviewViewModel ReviewViewModel =
                new ViewModelProvider(this).get(ReviewViewModel.class);

        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
}