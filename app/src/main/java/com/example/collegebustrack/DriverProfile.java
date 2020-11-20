package com.example.collegebustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class DriverProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        SessionManagerDriver sessionManager=new SessionManagerDriver(this);
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
        final String bus=userDetails.get(SessionManagerDriver.key_busid);
        String name=userDetails.get(SessionManagerDriver.key_name);
        String phone=userDetails.get(SessionManagerDriver.key_phone);
        String email=userDetails.get(SessionManagerDriver.key_email);

        TextView dName= findViewById(R.id.dname);
        TextView dEmail= findViewById(R.id.demail);
        TextView dBus= findViewById(R.id.dbus);
        TextView dPhone= findViewById(R.id.dphone);

        dName.setText(name);
        dPhone.setText("Phone : "+phone);
        dEmail.setText("Email : "+email);
        dBus.setText("BusID : "+bus);


    }
}