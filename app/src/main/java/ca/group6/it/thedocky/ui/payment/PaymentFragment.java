//Semen Dyakonov. N01391812
//Binay Garlapati. N01368870
//Nissan Rayappu N01435235
//
package ca.group6.it.thedocky.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";
    
    private CardView cardView;  
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_payment,container,false);
        cardView = view.findViewById(R.id.card_pay_id);
        
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click");
                startActivity(new Intent(getContext(),PaymentActivity.class));
            }
        });        

        return view;
    }

  
}