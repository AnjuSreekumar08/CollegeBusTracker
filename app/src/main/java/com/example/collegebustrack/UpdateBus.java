package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateBus extends AppCompatActivity {

    private String busnumber,busid,busroute,findDetails="",sbusnumber,sbusid,sbusroute;
    private EditText ebusnumber,ebusid,ebusroute,esearch;
    private Button search,update;
    DatabaseReference reference,reference1;
    String hello;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bus);

        reference= FirebaseDatabase.getInstance().getReference("bus");


        esearch=findViewById(R.id.editTextTextPersonName30);
        ebusnumber=(EditText)findViewById(R.id.editTextTextPersonName31);
        ebusid=(EditText)findViewById(R.id.editTextTextPersonName32);
        ebusroute=(EditText)findViewById(R.id.editTextTextPersonName33);

        search=findViewById(R.id.button35);
        update=findViewById(R.id.button36);








        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                reference.child("busdetails").child(esearch.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        busnumber=snapshot.child("busnumber").getValue(String.class);
                        busid=snapshot.child("busid").getValue(String.class);
                        busroute=snapshot.child("busroute").getValue(String.class);


                        ebusnumber.setText(busnumber, TextView.BufferType.EDITABLE);
                        ebusid.setText(busid,TextView.BufferType.EDITABLE);
                        ebusroute.setText(busroute,TextView.BufferType.EDITABLE);







                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference1=FirebaseDatabase.getInstance().getReference("bus").child("busdetails").child(esearch.getText().toString());



                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Map<String, Object> updates = new HashMap<String,Object>();

                                updates.put("busnumber", ebusnumber.getText().toString());
                                updates.put("busid", ebusid.getText().toString());

                                updates.put("busroute",ebusroute.getText().toString());

//etc

                                reference1.updateChildren(updates);

                                //    reference.child("adm").setValue( eadm.getText().toString());
                                //          reference.child("branch").setValue( ebranch.getText().toString());
                                //        reference.child("sem").setValue( esem.getText().toString());
                                //      reference.child("busid").setValue(ebus.getText().toString());

                                Toast.makeText(UpdateBus.this,"Data Updated",Toast.LENGTH_SHORT).show();



                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });










            }
        });


    }

}
