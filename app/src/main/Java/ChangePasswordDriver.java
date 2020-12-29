package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.firebase.auth.FirebaseAuth.*;

public class ChangePasswordDriver extends AppCompatActivity {



    EditText existpwd,newpwd;
    Button changepwddriver;
    String password,phone,email;
    String zero;
    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_driver);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionManagerDriver sessionManager=new SessionManagerDriver(this);
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
         password=userDetails.get(SessionManagerDriver.key_password);
         phone=userDetails.get(SessionManagerDriver.key_phone);
         email=userDetails.get(SessionManagerDriver.key_email);

        changepwddriver=findViewById(R.id.changepwddriver);

         newpwd= findViewById(R.id.newpwddriver);










        changepwddriver.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 user=FirebaseAuth.getInstance().getCurrentUser();
                 Toast.makeText(ChangePasswordDriver.this,"hwloo",Toast.LENGTH_SHORT).show();




                 user.updatePassword( newpwd.getText().toString())
                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<Void> task) {
                                                     if (task.isSuccessful()) {
                                                       Toast.makeText(ChangePasswordDriver.this,"Passsword changed",Toast.LENGTH_SHORT).show();


                                                         FirebaseDatabase.getInstance().getReference("bus").child("driver").child(phone).child("password").setValue( newpwd.getText().toString()
                                                         ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                             @Override
                                                             public void onSuccess(Void aVoid) {

                                                                 Toast.makeText(ChangePasswordDriver.this,"Password Changed hggg",Toast.LENGTH_SHORT).show();

                                                             }
                                                         }).addOnFailureListener(new OnFailureListener() {
                                                             @Override
                                                             public void onFailure(@NonNull Exception e) {

                                                                 Toast.makeText(ChangePasswordDriver.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                                             }
                                                         });

                                                     }
                                                 }
                                             });







             }
         });




    }
}