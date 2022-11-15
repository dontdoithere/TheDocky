package ca.group6.it.thedocky;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.group6.it.thedocky.databinding.FragmentReviewBinding;



public class ReviewFragment extends Fragment {


    Button btn;
    View view;

    DatabaseReference databaseReference;

    private FirebaseAuth auth;
    RatingBar testing0;
    EditText testing;
    EditText testing1;
    EditText testing2;
    EditText testing3;

    private FragmentReviewBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_review, container, false);
        testing0=view.findViewById(R.id.rbStars);
        testing=view.findViewById(R.id.customerReviewName);
        testing1=view.findViewById(R.id.customerReviewPhone);
        testing2=view.findViewById(R.id.customerReviewEmailAddress);
        testing3=view.findViewById(R.id.customerReviewTextboxv2);


        btn=view.findViewById(R.id.customerReviewButtonSubmit);
        buttonWork();
        databaseReference = FirebaseDatabase.getInstance().getReference("Review");//path

/*        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Button is working fine");
                ReviewViewModel reviewViewModel= new ReviewViewModel();
                String uid=databaseReference.push().getKey();
                reviewViewModel.setQid("uid");
                reviewViewModel.setName("Enter the user name");
                reviewViewModel.setAddress("Enter the address");
                reviewViewModel.setEmail("Enter the email address");
                reviewViewModel.setPhonenumber("Enter the phone number");
                reviewViewModel.setFeedback("Enter the feedback");
                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/test");


                databaseReference.child(uid).setValue(reviewViewModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/



        return view;
    }

    private void buttonWork() {
        Log.d(TAG,"MSG FROM BUTTON CLASS");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"testing button if working");
                float str=testing0.getRating();
                String str1=testing.getText().toString();
                String str2=testing1.getText().toString();
                String str3=testing2.getText().toString();
                String str4=testing3.getText().toString();
                Log.d (TAG,"testing 2022 str"+str);

                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/rating bar");
                databaseReference.setValue(str);
                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/name");
                databaseReference.setValue(str1);
                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/phone number");
                databaseReference.setValue(str2);
                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/email address");
                databaseReference.setValue(str3);
                databaseReference=FirebaseDatabase.getInstance().getReference().child("/review/feed back");
                databaseReference.setValue(str4);
//                Log.d (TAG,"testing 2022 str sent to db "+str);
                Log.d (TAG,"testing 2022 str sent to db "+str1);
                Log.d (TAG,"testing 2022 str sent to db "+str2);
                Log.d (TAG,"testing 2022 str sent to db "+str3);
                Log.d (TAG,"testing 2022 str sent to db "+str4);



            }
        });
    }


    @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }

    }


