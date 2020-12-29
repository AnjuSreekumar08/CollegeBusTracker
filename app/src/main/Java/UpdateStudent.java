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

public class UpdateStudent extends AppCompatActivity {

    private String name,branch,sem,bus,adm,findDetails="",sname,sbranch,ssem,sbus,sadm;
    private EditText ename,ebranch,esem,ebus,eadm,esearch;
    private Button search,update;
    DatabaseReference reference,reference1;
    String hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        reference= FirebaseDatabase.getInstance().getReference("bus");


        esearch=findViewById(R.id.editTextTextPersonName15);
        ename=(EditText)findViewById(R.id.editTextTextPersonName16);
        eadm=(EditText)findViewById(R.id.editTextTextPersonName17);
        ebranch=(EditText)findViewById(R.id.editTextTextPersonName18);
        esem=(EditText)findViewById(R.id.editTextTextPersonName19);
        ebus=(EditText)findViewById(R.id.editTextTextPersonName21);

        search=findViewById(R.id.button5);
        update=findViewById(R.id.button6);








        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                reference.child("student").child(esearch.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        name=snapshot.child("name").getValue(String.class);
                         branch=snapshot.child("branch").getValue(String.class);
                        adm=snapshot.child("adm").getValue(String.class);
                      sem=snapshot.child("sem").getValue(String.class);
                         bus=snapshot.child("busid").getValue(String.class);


                        ename.setText(name, TextView.BufferType.EDITABLE);
                        eadm.setText(adm,TextView.BufferType.EDITABLE);
                        ebranch.setText(branch,TextView.BufferType.EDITABLE);
                        esem.setText(sem,TextView.BufferType.EDITABLE);
                        ebus.setText(bus,TextView.BufferType.EDITABLE);






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference1=FirebaseDatabase.getInstance().getReference("bus").child("student").child(esearch.getText().toString());



                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                Map<String, Object> updates = new HashMap<String,Object>();

                                updates.put("name", ename.getText().toString());
                                updates.put("sem", esem.getText().toString());
//etc

                                reference1.updateChildren(updates);

                            //    reference.child("adm").setValue( eadm.getText().toString());
                              //          reference.child("branch").setValue( ebranch.getText().toString());
                                //        reference.child("sem").setValue( esem.getText().toString());
                                  //      reference.child("busid").setValue(ebus.getText().toString());

                                        Toast.makeText(UpdateStudent.this,"Data Updated",Toast.LENGTH_SHORT).show();

  

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