package ca.group6.it.thedocky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText email;
    TextInputEditText password;

    AppCompatButton update;

    //Global Variables
    String _USERNAME, _EMAIL;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        //Database
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //Initialize text and button
        username = findViewById(R.id.editProfUsername);
        email = findViewById(R.id.editProfEmail);
        password = findViewById(R.id.editProfPass);
        update = findViewById(R.id.update_button);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileActivity.this,"In progress...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateInfo(View view){
        if(isNameChanged() || isPasswordChanged ()){
            Toast.makeText(this, "Data has been updated!", Toast.LENGTH_SHORT).show();
            
        }
    }

    private boolean isPasswordChanged() {

        return false;
    }

    private boolean isNameChanged() {
        return false;
    }
}