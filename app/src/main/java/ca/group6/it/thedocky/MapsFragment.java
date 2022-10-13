package ca.group6.it.thedocky;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsFragment extends Fragment implements  OnMapReadyCallback  {

//    private OnMapReadyCallback callback = new OnMapReadyCallback() {
//
//
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        }
//    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.g_map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
System.out.println("gggggggggggggggggggggggggggggg");
try {
    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    LatLng latLng = new LatLng(36.778259, -119.417931);
    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here");

    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    googleMap.getUiSettings().setZoomControlsEnabled(true);
    googleMap.addMarker(markerOptions);

    googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(@NonNull LatLng latLng) {

            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Want to got here?");

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.addMarker(markerOptions);


        }
    });

}catch (Exception e){

}




    }

}