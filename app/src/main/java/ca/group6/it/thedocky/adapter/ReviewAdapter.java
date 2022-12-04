package ca.group6.it.thedocky.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.Review;
import ca.group6.it.thedocky.User;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReViewViewHolder> {

    List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ReViewViewHolder holder, int position) {
        Review review = reviews.get(position);

        if (review != null) {

            holder.comment.setText(review.getCommentText());
            holder.ratingBar.setRating(review.getStarCount());

            PrettyTime prettyTime = new PrettyTime(Locale.getDefault().getCountry());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

            try {
                Date date = simpleDateFormat.parse(review.getPostDate());
                holder.date.setText(" • "+prettyTime.format(date));
            } catch (Exception e) {
                e.printStackTrace();
            }


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(review.getPosterID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    holder.name.setText(user.username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReViewViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView comment;
        RatingBar ratingBar;

        public ReViewViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            comment = itemView.findViewById(R.id.comment);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }


}
