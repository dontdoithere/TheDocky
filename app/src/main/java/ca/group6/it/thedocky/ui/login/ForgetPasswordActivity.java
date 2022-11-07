package ca.group6.it.thedocky.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import ca.group6.it.thedocky.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputLayout emailText;
    private Button resetPas;


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailText = (TextInputLayout) findViewById(R.id.email_cover);
        resetPas = (Button) findViewById(R.id.reset_button);

        auth = FirebaseAuth.getInstance();

        resetPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        ImageButton back = findViewById(R.id.navigate_up);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    //Method for reset password
    private void resetPassword(){
        String email = emailText.getEditText().getText().toString();

        if(email.isEmpty()){
            emailText.setError("Email is required!");
            emailText.requestFocus();
            return;
        }

       // if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
         //   emailText.setError("Please provide valid email!");
         //   emailText.requestFocus();
         //   return;
      //  }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPasswordActivity.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgetPasswordActivity.this, "Try again! Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}