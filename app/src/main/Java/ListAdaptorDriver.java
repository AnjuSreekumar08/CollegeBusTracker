package com.example.collegebustrack;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdaptorDriver extends ArrayAdapter {

    private Activity mContext;
    List<Driver> driverList;
    public ListAdaptorDriver(Activity mContext,List<Driver> driverList){

        super(mContext,R.layout.list_item_driver,driverList);
        this.mContext=mContext;
        this.driverList=driverList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= mContext.getLayoutInflater();
        View listItemViewDriver =inflater.inflate(R.layout.list_item_driver,null,true);
        TextView tvName= listItemViewDriver.findViewById(R.id.tvDName);
        TextView tvPhone= listItemViewDriver.findViewById(R.id.tvDphone);
        TextView tvBus= listItemViewDriver.findViewById(R.id.tvDBus);

        TextView tvEmail= listItemViewDriver.findViewById(R.id.tvDEmail);


        Driver driver =driverList.get(position);
        tvName.setText(driver.getName());
        tvPhone.setText(driver.getPhone());
        tvBus.setText(driver.getBus());

        tvEmail.setText(driver.getEmail());


        return  listItemViewDriver;

    }

}
