//Semen Dyakonov. N01391812
//Binay Garlapati. N01368870
//Nissan Rayappu N01435235
//
package ca.group6.it.thedocky.ui.payment;

import static android.view.View.inflate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.annotation.NonNullApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.User;


public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";

    private CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        cardView = view.findViewById(R.id.card_pay_id);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click");
                startActivity(new Intent(getContext(), PaymentActivity.class));
            }
        });

        TextView userName = view.findViewById(R.id.username);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                userName.setText(user.username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    public View oonCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        CardView transfer_id = cardView.findViewById(R.id.transfer_id);
        transfer_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click");
                startActivity(new Intent(getContext(), TransferActivity.class));
            }
        });

        return v;
    }
}



