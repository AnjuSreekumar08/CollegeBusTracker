package com.example.collegebustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateActivity extends AppCompatActivity {

    private Button button,buttondriver,buttonbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        button=findViewById(R.id.btn10);
        buttondriver=findViewById(R.id.btn11);
        buttonbus=findViewById(R.id.btn12);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(UpdateActivity.this,UpdateStudent.class);
                startActivity(i);
            }
        });

        buttonbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UpdateActivity.this,UpdateBus.class);
                startActivity(i);
            }
        });

        buttondriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(UpdateActivity.this,UpdateDriver.class);
                startActivity(i);

            }
        });

    }
}