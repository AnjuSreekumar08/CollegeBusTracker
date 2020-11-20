package com.example.collegebustrack.ui.gallery;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.collegebustrack.BusStudentLink;
import com.example.collegebustrack.DriverMain;
import com.example.collegebustrack.JavaMailAPI;
import com.example.collegebustrack.R;
import com.example.collegebustrack.Registration;
import com.example.collegebustrack.SessionManagerDriver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;


public class GalleryFragment extends Fragment {
    private DatabaseReference reference;

    private String email,subject,eMessage,busid,name;
    private TextView textView;
    LocationManager locationManager;






    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SessionManagerDriver sessionManager=new SessionManagerDriver(getActivity());
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
        busid=userDetails.get(SessionManagerDriver.key_busid);
        name=userDetails.get(SessionManagerDriver.key_name);


        locationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Turn On GPS")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Button btn = (Button) root.findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setMessage("Do you want to start the journey").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        reference = FirebaseDatabase.getInstance().getReference("bus");

                        subject="hello";
                        eMessage="helloo man";

                        FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(busid).child("studentdetails").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    BusStudentLink bus = data.getValue(BusStudentLink.class);
                                    subject="hello";
                                    eMessage="helloo man";



                                    email = bus.getEmail();
                                    JavaMailAPI javaMailAPI=new JavaMailAPI(getActivity(),email,subject,eMessage);
                                    javaMailAPI.execute();
                                    Toast.makeText(getActivity(),"send email",Toast.LENGTH_SHORT).show();



                                }





                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });













                        Intent i = new Intent(getActivity(), DriverMain.class);
                        startActivity(i);

                    }
                }).show();
            }
        });

        return root;
    }
}



