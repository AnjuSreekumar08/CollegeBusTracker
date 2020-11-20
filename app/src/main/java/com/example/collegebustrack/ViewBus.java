package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewBus extends AppCompatActivity {

    ListView myListViewBus;
    List<Bus> busList;
    DatabaseReference busref;

    ListAdapterBus adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);

        myListViewBus=findViewById(R.id.myListViewBus);
        busList=new ArrayList<>();
        busref= FirebaseDatabase.getInstance().getReference("bus").child("busdetails");
        busref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                busList.clear();

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Bus bus = snapshot1.getValue(Bus.class);
                    busList.add(bus);


                }
                 adapter = new ListAdapterBus(ViewBus.this, busList);
                myListViewBus.setAdapter(adapter);


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       final EditText filter=findViewById(R.id.search_bar_bus);



        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void search(final String s) {

        Query query=busref.orderByChild("busdetails").startAt(s);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    busList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {



                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}