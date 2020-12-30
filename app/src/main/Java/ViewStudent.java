package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudent extends AppCompatActivity {

    ListView myListView;
    List<Student> studentsList;
    DatabaseReference studentref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        myListView=findViewById(R.id.myListView);
        studentsList=new ArrayList<>();
        studentref= FirebaseDatabase.getInstance().getReference("bus").child("student");
        studentref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentsList.clear();

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Student student = snapshot1.getValue(Student.class);
                    studentsList.add(student);

                }
                ListAdapter adapter = new ListAdapter(ViewStudent.this,studentsList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}