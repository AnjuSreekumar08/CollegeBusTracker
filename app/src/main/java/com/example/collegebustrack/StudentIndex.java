package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class StudentIndex extends AppCompatActivity {

    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;
    private TextView textView;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_index);



        SessionManagerStudent sessionManager=new SessionManagerStudent(this);
        HashMap<String,String> userDetails= sessionManager.getsUserStudentDetailsFromSession();

        name=userDetails.get(SessionManagerStudent.key_name);


        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_student);
        bottomNavigationView = findViewById(R.id.bottomNavigationStudent);


        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/

        textView=(TextView)header.findViewById(R.id.txtStudent);

        textView.setText("Hello "+name);


        appBarConfiguration = new AppBarConfiguration.Builder(R.id.slideshowFragment)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.slideshowFragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.profilestudent: Intent in=new Intent(getApplicationContext(),StudentProfile.class);
                        startActivity(in);
                        return true;

                    case R.id.changepasswordstudent:



                                Intent i = new Intent(StudentIndex.this, ChangePasswordStudent.class);


                                startActivity(i);


                        return true;

                }
                return false;
            }
        });




        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id= destination.getId();

                switch(id){


                    case R.id.logout_student:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StudentIndex.this, MainActivity.class)); //Go back to home page
                        finish();
                        break;
                }
            }
        });
    }








    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration );
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(StudentIndex.this);
        mBuilder.setTitle("Do you want to go back to previous menu?");
        mBuilder.setIcon(R.drawable.ic_action_name);
        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        mBuilder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });




    }





}


