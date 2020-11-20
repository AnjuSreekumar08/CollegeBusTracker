package com.example.collegebustrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverIndex extends AppCompatActivity {



    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;

    private DatabaseReference reference;

    private String email,subject,eMessage,busid,name;
    private TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_index);


        SessionManagerDriver sessionManager=new SessionManagerDriver(this);
        HashMap<String,String> userDetails= sessionManager.getsUserDriverDetailsFromSession();
        busid=userDetails.get(SessionManagerDriver.key_busid);
        name=userDetails.get(SessionManagerDriver.key_name);




        reference = FirebaseDatabase.getInstance().getReference("bus");
        
        subject="Bus Started";
        eMessage="Started journey";



        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_driver);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/

        textView=(TextView)header.findViewById(R.id.textViewDriver);

        textView.setText("Hello "+name);



        appBarConfiguration = new AppBarConfiguration.Builder(R.id.galleryFragment)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.galleryFragment);



        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.changedriver1:



                                Intent i = new Intent(DriverIndex.this, ChangePasswordDriver.class);
                                startActivity(i);


                        return true;

                    case R.id.homedriver:
                        Intent in = new Intent(getApplicationContext(), DriverIndex.class);
                        startActivity(in);
                        return true;


                    case R.id.profiledriver:
                        Intent intent = new Intent(getApplicationContext(), DriverProfile.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });







        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()

    {
        @Override
        public void onDestinationChanged (@NonNull NavController controller, @NonNull NavDestination
        destination, @Nullable Bundle arguments){
        int id = destination.getId();

        switch (id) {

            case R.id.logout_driver:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DriverIndex.this, MainActivity.class)); //Go back to home page
                finish();
                break;

        }
    }
    });


}

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(DriverIndex.this);
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







        @Override
        public boolean onSupportNavigateUp() {

            return NavigationUI.navigateUp(navController,appBarConfiguration )
            ;
        }



        }








