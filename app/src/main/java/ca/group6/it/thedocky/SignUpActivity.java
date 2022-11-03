package ca.group6.it.thedocky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class SignUpActivity extends AppCompatActivity {

    private Button signUp;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText rePassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView or = findViewById(R.id.or);

        or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        signUp = findViewById(R.id.sign_up_button);
        username = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        password  = findViewById(R.id.password_signup);
        rePassword = findViewById(R.id.confirm_password_signup);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_rePassword = rePassword.getText().toString();
                String txt_username = username.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_rePassword)){
                    Toast.makeText(SignUpActivity.this, "You have empty fields!", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() < 6){
                    Toast.makeText(SignUpActivity.this, "Password should contain more then 6 symbols!", Toast.LENGTH_SHORT).show();
                }
                else if (!txt_password.equals(txt_rePassword)){
                    Toast.makeText(SignUpActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void rigesterUser(String email, String password){

    }
}