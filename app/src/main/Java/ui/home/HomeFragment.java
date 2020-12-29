package com.example.collegebustrack.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.collegebustrack.Admin;
import com.example.collegebustrack.BusReg;
import com.example.collegebustrack.DriverReg;
import com.example.collegebustrack.R;
import com.example.collegebustrack.Registration;
import com.example.collegebustrack.SessionManager;

import java.util.HashMap;

public class HomeFragment extends Fragment {



    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

            Button btn = (Button) root.findViewById(R.id.btn10);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getActivity(), Registration.class);
                    startActivity(in);
                }
            });
        Button btn1 = (Button) root.findViewById(R.id.btn11);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), DriverReg.class);
                startActivity(in);
            }
        });

        Button btn2 = (Button) root.findViewById(R.id.btn12);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), BusReg.class);
                startActivity(in);
            }
        });




        return root;
    }


}