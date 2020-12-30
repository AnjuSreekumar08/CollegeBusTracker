package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


        public class MainActivity extends AppCompatActivity {

            String listitem[];
            private EditText ename,epass,resetMail;
            private TextView forget,mtext;
            private DatabaseReference reference;
            public FirebaseAuth auth;
            private Button b1,b2,b3,button1,button_fragment;
            private String usertype,username,password,email,pass,forgetemail,forgetpassword,subject,eMessage,mail;
            ValueEventListener listener ;
            private int count,countuser;
            boolean isEmailValid, isPasswordValid, isPasswordVisible;

            @Override
            protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);


                auth =FirebaseAuth.getInstance();
                reference = FirebaseDatabase.getInstance().getReference("bus");


                ename = findViewById(R.id.username1);
                epass = findViewById(R.id.password1);

                button1=findViewById(R.id.button);
                forget=findViewById(R.id.forget);


                epass.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int RIGHT = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (epass.getRight() - epass.getCompoundDrawables()[RIGHT].getBounds().width())) {
                                int selection = epass.getSelectionEnd();
                                if (isPasswordVisible) {
                                    // set drawable image
                                    epass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye2, 0);
                                    // hide Password
                                    epass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    isPasswordVisible = false;
                                } else  {
                                    // set drawable image
                                    epass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye2, 0);
                                    // show Password
                                    epass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    isPasswordVisible = true;
                                }
                                epass.setSelection(selection);

                                return true;
                            }
                        }
                        return false;
                    }
                });







                forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          resetMail=new EditText(v.getContext());
                        AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                        passwordResetDialog.setTitle("Forget Password");
                        passwordResetDialog.setMessage("Enter Username");
                        passwordResetDialog.setView(resetMail);
                        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                 mail=resetMail.getText().toString();
                                 count=mail.length();

                                 if(count!=10) {

                                     FirebaseDatabase.getInstance().getReference("bus").child("student").child(mail).addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             forgetemail = snapshot.child("email").getValue(String.class);
                                             forgetpassword = snapshot.child("password").getValue(String.class);


                                             subject = "Student Registration ";
                                             eMessage = "Username : " + forgetemail + "\nPassword : " + forgetpassword + "\n Login to account using username and password.";


                                             JavaMailAPI javaMailAPI = new JavaMailAPI(MainActivity.this, forgetemail, subject, eMessage);
                                             javaMailAPI.execute();
                                             Toast.makeText(MainActivity.this, "Email Send", Toast.LENGTH_SHORT).show();

                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });
                                 }
                                 else{


                                     FirebaseDatabase.getInstance().getReference("bus").child("driver").child(mail).addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                             forgetemail = snapshot.child("email").getValue(String.class);
                                             forgetpassword = snapshot.child("password").getValue(String.class);


                                             subject = "Student Registration ";
                                             eMessage = "Username : " + forgetemail + "\nPassword : " + forgetpassword + "\n Login to account using username and password.";


                                             JavaMailAPI javaMailAPI = new JavaMailAPI(MainActivity.this, forgetemail, subject, eMessage);
                                             javaMailAPI.execute();
                                             Toast.makeText(MainActivity.this, "Email Send", Toast.LENGTH_SHORT).show();

                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });
                                 }

                            }
                        });
                        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });

                        passwordResetDialog.create().show();
                    }
                });








                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        username = ename.getText().toString();
                        password = epass.getText().toString();
                        countuser=username.length();

                        if(TextUtils.isEmpty(username)){
                            ename.setError("Email is Required");
                            return;
                        }
                        if(TextUtils.isEmpty(password)){
                            epass.setError("Password is Required");
                            return;
                        }
                        if(password.length() < 6){
                            epass.setError("Password must be >= ^ Characters");
                            return;
                        }

                                if(countuser==10){
                                    usertype="driver";
                                    set();
                                }
                                else if(countuser==5){
                                    usertype="student";
                                    set();
                                }
                                else{
                                    usertype="admin";
                                    set();
                                }


                    }
                });
            }




            private void hideKeyBoard() {
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    view.clearFocus();
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }


            public void set(){



                if (!usertype.isEmpty()) {



                    reference.child(usertype).child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                email = snapshot.child("email").getValue(String.class);
                                pass = snapshot.child("password").getValue(String.class);


                                if (pass.equals(password)) {
                                    auth.signInWithEmailAndPassword(email,pass).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();


                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {

                                            if(usertype=="admin") {
                                          String _name=snapshot.child("name").getValue(String.class);
                                          String _password=snapshot.child("password").getValue(String.class);
                                          String _email=snapshot.child("email").getValue(String.class);
                                          String _branch=snapshot.child("branch").getValue(String.class);
                                                SessionManager sessionManager=new SessionManager(MainActivity.this);
                                                sessionManager.createLoginSession(_name,_password,_email,_branch);

                                                Intent i = new Intent(getApplicationContext(), Admin.class);
                                                startActivity(i);
                                            }
                                            if (usertype=="driver"){

                                                String bus_id=snapshot.child("bus").getValue(String.class);
                                                String _name=snapshot.child("name").getValue(String.class);
                                                String _email=snapshot.child("email").getValue(String.class);
                                                String phone=snapshot.child("phone").getValue(String.class);
                                                String _password=snapshot.child("password").getValue(String.class);


                                                SessionManagerDriver sessionManager=new SessionManagerDriver(MainActivity.this);
                                                sessionManager.createLoginSession(bus_id,_name,_email,phone,_password);

                                                Intent i = new Intent(getApplicationContext(), DriverIndex.class);
                                                startActivity(i);
                                            }
                                            if (usertype=="student"){

                                                String _name=snapshot.child("name").getValue(String.class);
                                                String _email=snapshot.child("email").getValue(String.class);
                                                String _phone=snapshot.child("phone").getValue(String.class);
                                                String _adm=snapshot.child("adm").getValue(String.class);
                                                String _place=snapshot.child("place").getValue(String.class);
                                                String _busid=snapshot.child("busid").getValue(String.class);
                                                String _password=snapshot.child("password").getValue(String.class);


                                                SessionManagerStudent sessionManager=new SessionManagerStudent(MainActivity.this);
                                                sessionManager.createLoginSession(_name,_email,_phone,_adm,_place,_busid,_password);

                                                Intent i = new Intent(getApplicationContext(), StudentIndex.class);
                                                startActivity(i);
                                            }
                                        }
                                    });
                                } else {

                                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                }

                            } else {

                                Toast.makeText(MainActivity.this, "User doesnt Exist", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                } else {
                    Toast t = Toast.makeText(getApplicationContext(), "No word entered", Toast.LENGTH_LONG);
                    t.show();
                }

            }

        }