package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePasswordAdmin extends AppCompatActivity {

    private EditText oldpassword,newpassword,repassword;
    private Button changepasswordadmin;
    String password,phone,email,name;
    String zero;
    FirebaseAuth auth;
    FirebaseUser user;
    boolean isEmailValid, isPasswordValid, isPasswordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_admin);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionManager sessionManager=new SessionManager(this);
        HashMap<String,String> userDetails= sessionManager.getsUserDetailsFromSession();
        name=userDetails.get(SessionManager.key_fullname);


        changepasswordadmin=findViewById(R.id.changepasswordadmin);
        oldpassword=findViewById(R.id.oldpasswordadmin);
        newpassword= findViewById(R.id.newpasswordadmin);
        repassword=findViewById(R.id.repasswordadmin);




        oldpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (oldpassword.getRight() - oldpassword.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = oldpassword.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            oldpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // hide Password
                            oldpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            oldpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // show Password
                            oldpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        oldpassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        newpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newpassword.getRight() - newpassword.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = oldpassword.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            newpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // hide Password
                            newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            newpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // show Password
                            newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        newpassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        repassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (repassword.getRight() - repassword.getCompoundDrawables()[RIGHT].getBounds().width())) {
                        int selection = oldpassword.getSelectionEnd();
                        if (isPasswordVisible) {
                            // set drawable image
                            repassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // hide Password
                            repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else  {
                            // set drawable image
                            repassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            // show Password
                            repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        repassword.setSelection(selection);

                        return true;
                    }
                }
                return false;
            }
        });



        changepasswordadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newpassword.equals(repassword)) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(newpassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        FirebaseDatabase.getInstance().getReference("bus").child("admin").child(name).child("password").setValue(newpassword.getText().toString()
                                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                FirebaseAuth.getInstance().signOut();
                                                Intent intent = new Intent(ChangePasswordAdmin.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                ChangePasswordAdmin.this.startActivity(intent);


                                                Toast.makeText(ChangePasswordAdmin.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(ChangePasswordAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                }
                            });


                }
                else {
                    Toast.makeText(ChangePasswordAdmin.this, "Password Mismatch", Toast.LENGTH_SHORT).show();

                }

            }

        });




    }
}