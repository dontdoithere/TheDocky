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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.annotation.NonNullApi;

import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.databinding.FragmentPaymentBinding;


public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";

    private CardView cardView;

    private CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_payment,container,false);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        cardView = view.findViewById(R.id.card_pay_id);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click");
                startActivity(new Intent(getContext(),PaymentActivity.class));
                startActivity(new Intent(getContext(), PaymentActivity.class));
            }
        });
    });

        return view;
}


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
