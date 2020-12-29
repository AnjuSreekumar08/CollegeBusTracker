package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePasswordStudent extends AppCompatActivity {

    EditText existpwd,newpwd;
    Button changepwdstudent;
    String password,adm,email;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_student);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SessionManagerStudent sessionManager=new SessionManagerStudent(this);
        HashMap<String,String> userDetails= sessionManager.getsUserStudentDetailsFromSession();
        password=userDetails.get(SessionManagerStudent.key_password);
        adm=userDetails.get(SessionManagerStudent.key_adm);
        email=userDetails.get(SessionManagerStudent.key_email);

        changepwdstudent=findViewById(R.id.changepwdstudent);

        newpwd= findViewById(R.id.newpwdstudent);



        changepwdstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=FirebaseAuth.getInstance().getCurrentUser();



                user.updatePassword( newpwd.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordStudent.this,"Passsword changed",Toast.LENGTH_SHORT).show();


                                    FirebaseDatabase.getInstance().getReference("bus").child("student").child(adm).child("password").setValue( newpwd.getText().toString()
                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(ChangePasswordStudent.this,"Password Changed hggg",Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(ChangePasswordStudent.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            }
                        });


            }
        });



    }
}