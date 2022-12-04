package ca.group6.it.thedocky;

import android.os.Bundle;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.group6.it.thedocky.ui.adapter.ReviewAdapter;
import ca.group6.it.thedocky.databinding.FragmentReviewBinding;


public class ReviewFragment extends Fragment {

    Button btn;
    View view;
    ReactionPopup popup;
    EditText comment;
    DatabaseReference databaseReference;

    private FirebaseAuth auth;
    int starCount = 0;
    FloatingActionButton send;

    private FragmentReviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review, container, false);

        ImageButton imageButton = view.findViewById(R.id.add_rating);
        comment = view.findViewById(R.id.comment_text);
        send = view.findViewById(R.id.send_review);
        databaseReference = FirebaseDatabase.getInstance().getReference("review");//path

        RecyclerView recyclerView = view.findViewById(R.id.review);
        List<Review> reviews = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Review review = dataSnapshot.getValue(Review.class);
                    reviews.add(review);
                }
                ReviewAdapter adapter = new ReviewAdapter(reviews);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] reactionText = {"1 Star", "2 Star", "3 Star", "4 Star", "5 Star",};

        ReactionsConfig config = new ReactionsConfigBuilder(getContext())
                .withReactions(new int[]{
                        R.drawable.star,
                        R.drawable.star,
                        R.drawable.star,
                        R.drawable.star,
                        R.drawable.star,

                }).withReactionTexts(position -> reactionText[position])

                .build();

        popup = new ReactionPopup(getContext(), config, (position) -> {

            Toast.makeText(getContext(), "Gave " + (position + 1) + " stars", Toast.LENGTH_SHORT).show();
            starCount = position + 1;
            Selection.setSelection(comment.getText(), comment.getText().length());
            return true;
        });

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                popup.onTouch(view, motionEvent);

                return true;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (starCount != 0) {

                    String commentText = comment.getText().toString();
                    String postID = String.valueOf(System.currentTimeMillis());
                    String posterID = FirebaseAuth.getInstance().getUid();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

                    Date date = new Date();

                    String format = dateFormat.format(date);

                    Review review = new Review(postID, posterID, starCount, commentText, format);

                    FirebaseDatabase.getInstance().getReference("review")
                            .child(postID)
                            .setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        starCount = 0;
                                        comment.setText("");

                                    } else {
                                    }

                                }
                            });
                } else {

                    Toast.makeText(getContext(), "Please add a star button is at thr left", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


