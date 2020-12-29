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

public class UpdateDriver extends AppCompatActivity {


    private String name,phone,bus,findDetails="",sname,sphone,sbus;
    private EditText ename,ephone,ebus,esearch;
    private Button search,update;
    DatabaseReference reference,reference1;
    String hello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_driver);
        reference= FirebaseDatabase.getInstance().getReference("bus");


        esearch=findViewById(R.id.editTextTextPersonName25);
        ename=(EditText)findViewById(R.id.editTextTextPersonName26);
        ephone=(EditText)findViewById(R.id.editTextTextPersonName27);
        ebus=(EditText)findViewById(R.id.editTextTextPersonName28);

        search=findViewById(R.id.button25);
        update=findViewById(R.id.button26);








        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                reference.child("driver").child(esearch.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        name=snapshot.child("name").getValue(String.class);
                        phone=snapshot.child("phone").getValue(String.class);
                        bus=snapshot.child("bus").getValue(String.class);


                        ename.setText(name, TextView.BufferType.EDITABLE);
                        ephone.setText(phone,TextView.BufferType.EDITABLE);

                        ebus.setText(bus,TextView.BufferType.EDITABLE);






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference1=FirebaseDatabase.getInstance().getReference("bus").child("driver").child(esearch.getText().toString());



                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                Map<String, Object> updates = new HashMap<String,Object>();

                                updates.put("name", ename.getText().toString());
                                updates.put("phone", ephone.getText().toString());
                                updates.put("bus", ebus.getText().toString());
//etc

                                reference1.updateChildren(updates);

                                //    reference.child("adm").setValue( eadm.getText().toString());
                                //          reference.child("branch").setValue( ebranch.getText().toString());
                                //        reference.child("sem").setValue( esem.getText().toString());
                                //      reference.child("busid").setValue(ebus.getText().toString());

                                Toast.makeText(UpdateDriver.this,"Data Updated",Toast.LENGTH_SHORT).show();



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