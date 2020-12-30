package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDriver extends AppCompatActivity {

    ListView myListViewDriver;
    List<Driver> driverList;
    DatabaseReference driverref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver);


        myListViewDriver=findViewById(R.id.myListViewDriver);
        driverList=new ArrayList<>();
        driverref= FirebaseDatabase.getInstance().getReference("bus").child("driver");
        driverref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverList.clear();

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Driver driver = snapshot1.getValue(Driver.class);
                    driverList.add(driver);

                }
                ListAdaptorDriver adapter = new ListAdaptorDriver(ViewDriver.this, driverList);
                myListViewDriver.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}