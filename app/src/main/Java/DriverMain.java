package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.DescriptorProtos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;


public class DriverMain extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    String bus,locationset="hello",phone;
    private Button studentstopButton,driverClose,emergency;
    LocationListener locationListener;
    SmsManager mySmsManager;
    private EditText resetMail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        ActivityCompat.requestPermissions(DriverMain.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);




        SessionManagerDriver sessionManager=new SessionManagerDriver(this);
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
          bus=userDetails.get(SessionManagerDriver.key_busid);

        studentstopButton=(Button)findViewById(R.id.driverstop);

        studentstopButton.setVisibility(VISIBLE);




        studentstopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String set="0";



                BusStatus busStatus=new BusStatus(set);

                FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(bus).child("BusStatus").child("status").setValue(set).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DriverMain.this, "set", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DriverMain.this, "not set", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                locationset=null;

                Intent i=new Intent(getApplicationContext(),DriverIndex.class);

                startActivity(i);

            }
        });


        emergency=findViewById(R.id.emergency);

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                 resetMail=new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Emergency");
                passwordResetDialog.setMessage("Type a query");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setIcon(R.drawable.ic_bus);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(bus).child("studentdetails").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    BusStudentPhone bus1 = data.getValue(BusStudentPhone.class);

                                    String mail=resetMail.getText().toString();


                                    phone = bus1.getPhone();
                                    String hello="9746688432";

                                    mySmsManager = SmsManager.getDefault();
                                    mySmsManager.sendTextMessage(hello,null,mail, null, null);

                                    Toast.makeText(DriverMain.this, "Message Sent", Toast.LENGTH_LONG).show();

                                    Toast.makeText(DriverMain.this
                                            , "Registered", Toast.LENGTH_LONG).show();

                                }



                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });





                    }
                });
                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                    }
                });



                passwordResetDialog.create().show();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



            mapFragment.getView().setVisibility(View.GONE);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){


                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(DriverMain.this);
                        try {

                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getLocality() + "," + addressList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str)).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_listpackfinal_foreground));


                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));

                            if(locationset!=null) {

                                LocationHelper helper = new LocationHelper(location.getLatitude(), location.getLongitude());

                                FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(bus).child("location").setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(DriverMain.this, "Location Saved", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(DriverMain.this, "Location Not Saved", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(DriverMain.this,"Location set",Toast.LENGTH_SHORT);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }



                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });



        }

        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //     LatLng sydney=new LatLng(-34,151);
        //   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Syndey"));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}