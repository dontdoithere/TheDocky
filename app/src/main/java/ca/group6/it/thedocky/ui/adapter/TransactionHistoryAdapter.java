package ca.group6.it.thedocky.ui.adapter;

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
import ca.group6.it.thedocky.TransactionHistory;
import ca.group6.it.thedocky.User;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ReViewViewHolder> {

    List<TransactionHistory> historyList;

    public TransactionHistoryAdapter(List<TransactionHistory> reviews) {
        this.historyList = reviews;
    }

    @NonNull
    @Override
    public ReViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ReViewViewHolder holder, int position) {
        TransactionHistory history = historyList.get(position);

        if (history != null) {

            holder.city.setText(history.cityName);
            holder.amount.setText("-$"+history.amount);

            PrettyTime prettyTime = new PrettyTime(Locale.getDefault().getCountry());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

            try {
                Date date = simpleDateFormat.parse(history.date);
                holder.date.setText(prettyTime.format(date));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class ReViewViewHolder extends RecyclerView.ViewHolder {

        TextView city;
        TextView date;
        TextView amount;

        public ReViewViewHolder(@NonNull View itemView) {
            super(itemView);

            city = itemView.findViewById(R.id.city);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);

        }
    }


}
