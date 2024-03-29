package ca.group6.it.thedocky.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.AccessibleSupportedCardTypesView;
import com.braintreepayments.cardform.view.CardForm;

import ca.group6.it.thedocky.R;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

    private static final CardType[] SUPPORTED_CARD_TYPES = { CardType.VISA, CardType.MASTERCARD, CardType.DISCOVER,
            CardType.AMEX, CardType.DINERS_CLUB, CardType.JCB, CardType.MAESTRO, CardType.UNIONPAY,
            CardType.HIPER, CardType.HIPERCARD };

    private AccessibleSupportedCardTypesView mSupportedCardTypesView;
    private ImageButton back_btn;

    CardForm cardForm;

    AlertDialog.Builder alertBuilder;
    private int btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mSupportedCardTypesView = findViewById(R.id.supported_card_types);
        mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);

        cardForm = (CardForm) findViewById(R.id.card_form);
        Button pay = findViewById(R.id.btnPay);
        back_btn = findViewById(R.id.navigate_up);

        View pay_t = findViewById(btnPay);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);

        /**
         });  * Listener to receive a callback when the card form should be submitted.
         * This is triggered from a keyboard by a {@link android.view.inputmethod.EditorInfo#IME_ACTION_GO}
         * event
         */


        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder;
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV:" + cardForm.getCvv() + "\n" +
                            "Postal code:" + cardForm.getPostalCode() + "\n" +
                            "Phone number:" + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(PaymentActivity.this, "Thank you for the payment", Toast.LENGTH_LONG).show();
                        }
                    });

                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(PaymentActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }

        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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