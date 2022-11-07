package ca.group6.it.thedocky.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import ca.group6.it.thedocky.MainActivity;
import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.User;


public class SignUpActivity extends AppCompatActivity {

    private Button signUp;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText rePassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView or = findViewById(R.id.or);

        //To go back to login page
        or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        //Initialize views
        signUp = findViewById(R.id.sign_up_button);
        username = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        password  = findViewById(R.id.password_signup);
        rePassword = findViewById(R.id.confirm_password_signup);

        //Declare database
        auth = FirebaseAuth.getInstance();

        //When you click register button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString().trim();
                String txt_rePassword = rePassword.getText().toString().trim();
                String txt_username = username.getText().toString().trim();

                if (txt_email.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if (txt_username.isEmpty()){
                    username.setError("Username is required");
                    username.requestFocus();
                    return;
                }
                if (txt_password.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                    email.setError("Please provide valid email!");
                }
                else if (txt_password.length() < 6){
                    password.setError("Min password length is 6 characters!");
                    password.requestFocus();
                    return;
                }
                else if (!txt_password.equals(txt_rePassword)){
                    rePassword.setError("Password are not matching!");
                    rePassword.requestFocus();
                    return;
                } else {
                    registerUser(txt_email, txt_password);
                }
            }
        });

    }

    //Method to register user
    private void registerUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String txt_username = username.getText().toString().trim();
                    User user = new User(txt_username, email);

                    //Put our values into database
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SignUpActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}