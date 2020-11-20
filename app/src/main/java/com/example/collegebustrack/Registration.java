package com.example.collegebustrack;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Message;


public class Registration extends AppCompatActivity {
    public Button button,button1;
    Message message;
    Session session;
    Properties properties;
    Spinner dropdown;

    public EditText name,adm,branch,email,busid,pwd,sem;
    private String ename, eadm,ebranch,eemail,ebusid,epwd,esem;
    private DatabaseReference reference;
    private String emailTo,subject,admission="",password="";
    String messageToSend,eMessage,dropdownBusid;
    private List<String> busList;


    FirebaseAuth auth;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        getSupportActionBar().setTitle("Student Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         dropdown = findViewById(R.id.spinner1);






        reference = FirebaseDatabase.getInstance().getReference("bus");
        auth =FirebaseAuth.getInstance();


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



        name=findViewById(R.id.editTextTextPersonName);
        adm=findViewById(R.id.editTextTextPersonName2);
        branch=findViewById(R.id.editTextTextPersonName3);
        sem=findViewById(R.id.editTextTextPersonName4);

        pwd= findViewById(R.id.editTextTextPassword);
        email=findViewById(R.id.editTextTextPersonName6);

        button=findViewById(R.id.button2);
        button1=findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Registration.this,"helloooo",Toast.LENGTH_LONG).show();


            }
        });

        admission=adm.getText().toString();
        password=pwd.getText().toString();




        emailTo=email.getText().toString();







    }
    public void createaccount(View view){

        eadm=adm.getText().toString();
        ename=name.getText().toString();
        ebranch=branch.getText().toString();
        eemail=email.getText().toString();

        epwd=pwd.getText().toString();
        esem=sem.getText().toString();

         ebusid = dropdown.getSelectedItem().toString();

        subject="Student Registration ";
        eMessage="Username : "+eemail+ "\nPassword : "+epwd+ "\nLogin to account using username and password.";






        if(TextUtils.isEmpty(ename)){
            name.setError("Name is Required");
            return;
        }
        if(TextUtils.isEmpty(eadm)){
            adm.setError("Admission Number is Required");
            return;
        }
        if(TextUtils.isEmpty(ebranch)){
            branch.setError("Branch is Required");
            return;
        }

        if(TextUtils.isEmpty(esem)){
            sem.setError("Semester is Required");
            return;
        }

        if(TextUtils.isEmpty(ebusid)){
            busid.setError("Place is Required");
            return;
        }

        if(TextUtils.isEmpty(eemail)){
            email.setError("Email is Required");
            return;
        }
        if(TextUtils.isEmpty(epwd)){
            pwd.setError("Password is Required");
            return;
        }

        if(epwd.length() < 6){
            pwd.setError("Password must be >= ^ Characters");
            return;
        }




        auth.createUserWithEmailAndPassword(eemail,epwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Student student=new Student(auth.getCurrentUser().getUid(),ename,eadm,ebranch,eemail,ebusid,epwd,esem);


                reference.child("student").child(eadm).setValue(student).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registration.this,
                                e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        JavaMailAPI javaMailAPI=new JavaMailAPI(Registration.this,eemail,subject,eMessage);

                        javaMailAPI.execute();





                        BusStudentLink busStudentLink=new BusStudentLink(eemail);
                        reference.child("busdetails").child(ebusid).child("studentdetails").child(ename).setValue(busStudentLink).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Registration.this,
                                       " Student details saved at bus details",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(Registration.this, "Data Saved", Toast.LENGTH_LONG).show();
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
}