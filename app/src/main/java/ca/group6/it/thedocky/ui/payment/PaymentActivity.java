package ca.group6.it.thedocky.ui.payment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.view.CardForm;

import ca.group6.it.thedocky.R;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

    CardForm cardForm ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
       cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);


        cardForm.setOnCardFormSubmitListener(new OnCardFormSubmitListener() {
            @Override
            public void onCardFormSubmit() {
                String cardNo= cardForm.getCardNumber();
                Log.d(TAG, "onCreate: "+ cardNo);

                String exp= cardForm.getExpirationMonth();


                String ExpYear= cardForm.getExpirationYear();
                String Cvv= cardForm.getCvv();
                String HolderName= cardForm.getCardholderName();
                String Postal= cardForm.getPostalCode();
                String CountryCode= cardForm.getCountryCode();
                String Mobile= cardForm.getMobileNumber();
            }
        });


    }
}