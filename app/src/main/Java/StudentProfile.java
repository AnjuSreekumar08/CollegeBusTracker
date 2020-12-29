package com.example.collegebustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class StudentProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        SessionManagerStudent sessionManager=new SessionManagerStudent(this);
        HashMap<String,String> userDetails= sessionManager.getsUserStudentDetailsFromSession();
        String busid=userDetails.get(SessionManagerStudent.key_busid);
        String name= userDetails.get(SessionManagerStudent.key_name);
        String branch=userDetails.get(SessionManagerStudent.key_branch);
        String email=userDetails.get(SessionManagerStudent.key_email);
        String adm=userDetails.get(SessionManagerStudent.key_adm);


        TextView sName= findViewById(R.id.sname);
        TextView sBranch= findViewById(R.id.sbranch);
        TextView sEmail= findViewById(R.id.semail);
        TextView sBus= findViewById(R.id.sbus);
        TextView sAdm= findViewById(R.id.sadm);

        sName.setText(name);
        sBranch.setText("Branch : "+branch);
        sEmail.setText("Email : "+email);
        sBus.setText("BusID : "+busid);
        sAdm.setText("Admission No : "+adm);




    }
}