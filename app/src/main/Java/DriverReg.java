package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverReg extends AppCompatActivity {

    public Button button1;

    private EditText dname, dbus, dphone, demail, dpwd;
    private String dname1,dbus1,dphone1,demail1,dpwd1;
    private DatabaseReference reference;
    private String subject,message,dropdownBusid,eMessage;
    private Spinner dropdown;
    private List<String> busList;
    SmsManager mySmsManager;


    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg);

        ActivityCompat.requestPermissions(DriverReg.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);


        getSupportActionBar().setTitle("Driver Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        reference = FirebaseDatabase.getInstance().getReference("bus");
        auth = FirebaseAuth.getInstance();

        dname = findViewById(R.id.editTextTextPersonName7);
        demail= findViewById(R.id.editTextTextPersonName8);

        dphone = findViewById(R.id.editTextTextPersonName10);
        dpwd = findViewById(R.id.editTextTextPassword2);

        dropdown = findViewById(R.id.spinner2);
        dropdown.setPrompt("Bus ID");


        busList=new ArrayList<String>();
        busList.add(0, "Bus ID");
        FirebaseDatabase.getInstance().getReference("bus").child("busdetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);

                    dropdownBusid=bus.getBusid();

                    busList.add(dropdownBusid);


                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.colour_spinner, busList);
                dropdown.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void driver(View view) {

        dname1 = dname.getText().toString();
        demail1 = demail.getText().toString();
        dbus1 =   dropdown.getSelectedItem().toString();
        dphone1 = dphone.getText().toString();
        dpwd1 = dpwd.getText().toString();

busList.add(0, "Bus ID");

        subject="Driver Registration ";
        message="heello";

        if(TextUtils.isEmpty(dname1)){
            dname.setError("Name is Required");
            return;
        }
        if(TextUtils.isEmpty(demail1)){
            demail.setError("Email is Required");
            return;
        }

        if(TextUtils.isEmpty(dbus1)){
            dbus.setError("Bus is Required");
            return;
        }
        if(TextUtils.isEmpty(dphone1)){
            dphone.setError("Phone is Required");
            return;
        }
        if(TextUtils.isEmpty(dpwd1)){
            dpwd.setError("Password is Required");
            return;
        }

        if(dpwd1.length() < 6){
            dpwd.setError("Password must be >= ^ Characters");
            return;
        }




        subject="Hello hiiiiii";
        eMessage="Message send";





        auth.createUserWithEmailAndPassword(demail1, dpwd1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Driver driver = new Driver(auth.getCurrentUser().getUid(), dname1,dbus1,dphone1,demail1,dpwd1);


                reference.child("driver").child(dphone1).setValue(driver).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DriverReg.this,
                                e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        JavaMailAPI javaMailAPI=new JavaMailAPI(DriverReg.this,demail1,subject,eMessage);

                        javaMailAPI.execute();


                        Toast.makeText(DriverReg.this, "Registered", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());;


                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DriverReg.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}