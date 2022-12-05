package ca.group6.it.thedocky.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import ca.group6.it.thedocky.MainActivity;
import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText password;
    private TextInputEditText email;
    private TextView forgotPass;
    private Button log_btn;

    //Google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    LinearLayout googleBtn;

    //Database
    private FirebaseAuth auth;
    ProgressDialog progressDialog;

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
        log_btn = findViewById(R.id.login_button);
        password = findViewById(R.id.password_login);
        email = findViewById(R.id.email_login);

        //Database
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
        googleBtn = findViewById(R.id.social_icons);

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
    private void signIn() {
        Intent signIntent = gsc.getSignInIntent();
        startActivityForResult(signIntent, 1000);
    }

    //Check if login successful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {

            try {
                progressDialog.show();

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();

                                if (isNewUser) {

                                    GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

                                    String email = googleSignInAccount.getEmail();
                                    String name = googleSignInAccount.getDisplayName();

                                    User user = new User(name, email,100);
                                    //Put our values into database
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();

                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                                        Toast.makeText(LoginActivity.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        progressDialog.dismiss();

                                                        Toast.makeText(LoginActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                    Toast.makeText(LoginActivity.this, "Your now logged in", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            } catch (ApiException e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Method to move from Login activity to Main
    private void navigateToSecondActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    }

    //Method for login
    private void loginUser(String email, String password) {
        progressDialog.show();
        if (email.isEmpty()) {
            this.email.setError("Email is required!");
            this.email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("Please enter valid email!");
            this.email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            this.password.setError("Password is required!");
            this.password.requestFocus();
            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}