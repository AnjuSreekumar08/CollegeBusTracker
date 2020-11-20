package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StudentMain extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String city;
    List<Address> addressList=null;
    TextView textView;


    private Button studentcloseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        textView=findViewById(R.id.mapstudenTextview);





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SessionManagerStudent sessionManager=new SessionManagerStudent(this);
        HashMap<String,String> userDetails= sessionManager.getsUserStudentDetailsFromSession();
        String busid=userDetails.get(SessionManagerStudent.key_busid);

        studentcloseButton=findViewById(R.id.studentcloseButton);

        studentcloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),StudentIndex.class);
                startActivity(i);
            }
        });









        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(busid).child("location");
        ValueEventListener listener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Geocoder geocoder=new Geocoder(StudentMain.this);



                Double latitude = snapshot.child("latitude").getValue(Double.class);
                Double longitude= snapshot.child("longitude").getValue(Double.class);
                try {
                    addressList=geocoder.getFromLocation(latitude,longitude,1);
                    city=addressList.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                LatLng location=new LatLng(latitude,longitude);



                mMap.addMarker(new MarkerOptions().position(location).title("Marker in Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14));
                textView.setText(city);
                Toast.makeText(getApplicationContext(),"Location set",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Add a marker in Sydney and move the camera

    }
}