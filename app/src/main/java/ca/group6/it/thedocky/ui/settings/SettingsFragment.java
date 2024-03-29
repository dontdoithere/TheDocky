//Semen Dyakonov. N01391812
//Binay Garlapati. N01368870
//Nissan Rayappu N01435235
//
package ca.group6.it.thedocky.ui.settings;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.group6.it.thedocky.EditProfileActivity;

import ca.group6.it.thedocky.FaqActivity;
import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.SplashScrActivity;
import ca.group6.it.thedocky.User;
import ca.group6.it.thedocky.databinding.FragmentSettingsBinding;
import ca.group6.it.thedocky.ui.payment.PaymentActivity;

public class SettingsFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private ImageButton FAQbtn;

    private SwitchCompat delete_acc;
    private SwitchCompat edit_noti;
    private SwitchCompat night;
    View view;
    private Button logout;


    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        view = inflater.inflate(R.layout.fragment_settings, container, false);



        // Find the FAQbtn ImageButton
        FAQbtn = binding.getRoot().findViewById(R.id.FAQbtn);
            // Set an OnClickListener for the FAQbtn
            FAQbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), FaqActivity.class);
                    startActivity(intent);

                }
            });
        // delete_acc = view.findViewById(R.id.delete_accaount_btn);




        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SplashScrActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    



            binding.editpro.setOnClickListener(new View.OnClickListener() {
                View view = inflater.inflate(R.layout.fragment_settings, container, false);
                View binding = view.findViewById(R.id.editpro);


                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                }
            });



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                binding.usernameValueHome.setText(user.username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(binding.night.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(getContext(), "Night Mode ON", Toast.LENGTH_SHORT).show();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(getContext(), "Night Mode OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });





        //Delete user account
        binding.deleteAccaountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Are you sure?");
                dialog.setMessage("By clicking <Delete Account> you agree that you will absolutely delete .........");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Account was deleted!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getActivity(), SplashScrActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInt, int which) {
                        dialogInt.dismiss();
                    }
                });
                //Alert Dialog
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        binding.editbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(binding.editbtn.isChecked()){
                    Toast.makeText(getContext(), "Notification is enabled!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Notification is disabled!", Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Are you sure?");
                dialog.setMessage("By clicking <Edit notifications> you agree that you will absolutely enable notifications .........");
                dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.editbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View task) {
                                if(task.isShown()){
                                    Toast.makeText(getActivity(), "Notification is enabled!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), SettingsFragment.class);


                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInt, int which) {
                        dialogInt.dismiss();
                    }
                });
                //Alert Dialog
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
        


        //Change orientation
        binding.b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Toast.makeText(getActivity(), "LANDSCAPE MODE ON",Toast.LENGTH_SHORT).show();


                } else {

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Toast.makeText(getActivity(), "PORTRAIT MODE ON",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return binding.getRoot();

    }}

