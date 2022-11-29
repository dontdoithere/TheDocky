package ca.group6.it.thedocky.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.group6.it.thedocky.MainActivity;
import ca.group6.it.thedocky.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText password;
    private TextInputEditText email;
    private TextView forgotPass;
    private ImageButton back_btn;
    private Button log_btn;

    //Google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    //Database
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView or = findViewById(R.id.or);

        //To go back to login page
        or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        //Declare views
        forgotPass = findViewById(R.id.forgot_password);
        back_btn = findViewById(R.id.navigate_up);
        log_btn = findViewById(R.id.login_button);
        password = findViewById(R.id.password_login);
        email = findViewById(R.id.email_login);

        //Database
        auth = FirebaseAuth.getInstance();

        //Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        googleBtn = findViewById(R.id.btn_google);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        //Login button activity
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();
                loginUser(email_txt, password_txt);
            }
        });

        //Back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Button for forget password
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);

            }
        });

    }

        //SignIn with internet account
        private void signIn(){
        Intent signIntent = gsc.getSignInIntent();
        startActivityForResult(signIntent, 1000);
        }

        //Check if login successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            }catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Method to move from Login activity to Main
    private void navigateToSecondActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    }

    //Method for login
        private void loginUser(String email, String password){

        if(email.isEmpty()){
            this.email.setError("Email is required!");
            this.email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("Please enter valid email!");
            this.email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            this.password.setError("Password is required!");
            this.password.requestFocus();
            return;
        }
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                       if (user.isEmailVerified()){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class ));
                       }else{
                           user.sendEmailVerification();
                           Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(LoginActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

}