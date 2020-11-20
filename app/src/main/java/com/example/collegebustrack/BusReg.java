package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusReg extends AppCompatActivity {


    public Button button1;

    private EditText number,id,route;
    private String bnumber,bid,broute;
    private DatabaseReference reference;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_reg);

        getSupportActionBar().setTitle("Bus Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        reference = FirebaseDatabase.getInstance().getReference("bus");
        auth = FirebaseAuth.getInstance();

        number = findViewById(R.id.editTextTextPersonName11);
        id= findViewById(R.id.editTextTextPersonName12);
         route= findViewById(R.id.editTextTextPersonName13);



        button1 = (Button) findViewById(R.id.button10);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BusReg.this, "helloooo", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void bus(View view) {



        bnumber = number.getText().toString();
        bid = id.getText().toString();
        broute = route.getText().toString();


        if(TextUtils.isEmpty(bnumber)){
            number.setError("Bus Number is Required");
            return;
        }

          if(TextUtils.isEmpty(bid)) {
              id.setError("BusId is Required");
          }

        if(TextUtils.isEmpty(broute)) {
            route.setError("Bus Route is Required");
            return;
        }
    String latitude="";
        String longitude="";


        Bus bus = new Bus(bnumber,bid,broute);


                reference.child("busdetails").child(bid).setValue(bus).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BusReg.this,
                                e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BusReg.this, "Data Saved", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BusReg.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



    }
}