package com.example.collegebustrack;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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

    public EditText name,adm,branch,email,busid,pwd,sem,phone,place;
    private String ename, eadm,ebranch,eemail,ebusid,epwd,esem,ephone,eplace;
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
        phone=findViewById(R.id.editTextTextPersonName3);
        place=findViewById(R.id.editTextTextPersonName4);

        pwd= findViewById(R.id.editTextTextPassword);
        email=findViewById(R.id.editTextTextPersonName6);

        button=findViewById(R.id.button2);




        admission=adm.getText().toString();
        password=pwd.getText().toString();




        emailTo=email.getText().toString();







    }
    public void createaccount(View view){

        eadm=adm.getText().toString();
        ename=name.getText().toString();
        ephone=phone.getText().toString();
        eemail=email.getText().toString();

        epwd=pwd.getText().toString();
        eplace=place.getText().toString();

         ebusid = dropdown.getSelectedItem().toString();

        subject="Student Registration ";
        eMessage="Username : "+eemail+ "\nPassword : "+epwd+ "\n Login to account using username and password.";






        if(TextUtils.isEmpty(ename)){
            name.setError("Name is Required");
            return;
        }
        if(TextUtils.isEmpty(eadm)){
            adm.setError("Admission Number is Required");
            return;
        }
        if(TextUtils.isEmpty(ephone)){
            phone.setError("Phone is Required");
            return;
        }

        if(TextUtils.isEmpty(eplace)){
            place.setError("Place is Required");
            return;
        }

        if(TextUtils.isEmpty(ebusid)){
            busid.setError("BusID is Required");
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
                Student student=new Student(auth.getCurrentUser().getUid(),ename,eadm,ephone,eemail,ebusid,epwd,eplace);


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





                        BusStudentPhone busStudentPhone=new BusStudentPhone(ephone);
                        reference.child("busdetails").child(ebusid).child("studentdetails").child(ename).setValue(busStudentPhone).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Registration.this,
                                       " Student details saved at bus details",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(Registration.this, "Registered", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());;
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