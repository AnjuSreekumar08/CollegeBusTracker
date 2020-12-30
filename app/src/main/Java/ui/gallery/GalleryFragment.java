package com.example.collegebustrack.ui.gallery;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.collegebustrack.BusStatus;
import com.example.collegebustrack.BusStudentLink;
import com.example.collegebustrack.BusStudentPhone;
import com.example.collegebustrack.DriverMain;
import com.example.collegebustrack.DriverReg;
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

    private String email,subject,eMessage,busid,name,phone;
    private TextView textView;
    LocationManager locationManager;
    SmsManager mySmsManager;





    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SessionManagerDriver sessionManager=new SessionManagerDriver(getActivity());
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
        busid=userDetails.get(SessionManagerDriver.key_busid);
        name=userDetails.get(SessionManagerDriver.key_name);




        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);


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
                                    BusStudentPhone bus1 = data.getValue(BusStudentPhone.class);
                                    subject="Bus Tracker";
                                    eMessage="Bus Started...";

                                    phone = bus1.getPhone();

                                    String hello="9746688432";

                                    mySmsManager = SmsManager.getDefault();
                                    mySmsManager.sendTextMessage(hello,null,eMessage, null, null);

                                    Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_LONG).show();

                                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_LONG).show();

                                }




                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        String set="1";

                        BusStatus busStatus=new BusStatus(set);

                        FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(busid).child("BusStatus").child("status").setValue(set).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "set", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "not set", Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                        Intent i = new Intent(getActivity(), DriverMain.class);
                        startActivity(i);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                }).show();
            }
        });

        return root;
    }
}



