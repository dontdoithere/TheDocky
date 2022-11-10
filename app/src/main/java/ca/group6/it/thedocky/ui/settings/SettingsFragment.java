//Semen Dyakonov. N01391812
//Binay Garlapati. N01368870
//Nissan Rayappu N01435235
//
package ca.group6.it.thedocky.ui.settings;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import ca.group6.it.thedocky.R;
import ca.group6.it.thedocky.SplashScrActivity;
import ca.group6.it.thedocky.databinding.FragmentSettingsBinding;
import ca.group6.it.thedocky.ui.login.LoginActivity;
import ca.group6.it.thedocky.ui.payment.PaymentActivity;

public class SettingsFragment extends Fragment {

    View view;
    private Button logout;


    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SplashScrActivity.class);
                startActivity(intent);
                getActivity().finish();
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


        return root;


    }}

