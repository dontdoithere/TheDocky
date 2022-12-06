package ca.group6.it.thedocky;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText username;

    AppCompatButton update;

    //Global Variables
    String _USERNAME, _EMAIL;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        //Database
        reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());

        //Initialize text and button
        username = findViewById(R.id.editProfUsername);
        update = findViewById(R.id.update_button);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            Map<String,Object> map = new HashMap<>();
                            map.put("username",username.getText().toString());

                            reference.updateChildren(map);

                            Toast.makeText(EditProfileActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
            finish();

            }


        });
    }





}