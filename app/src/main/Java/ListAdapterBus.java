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

public class ListAdapterBus extends ArrayAdapter {
    private Activity mContext;
    List<Bus> busList;
    public ListAdapterBus(Activity mContext,List<Bus> busList ){

        super(mContext,R.layout.list_item_bus,busList);
        this.mContext=mContext;
        this.busList=busList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= mContext.getLayoutInflater();
        View listItemViewBus =inflater.inflate(R.layout.list_item_bus,null,true);
        TextView tvBusNumber= listItemViewBus.findViewById(R.id.tvBusNumber);
        TextView tvBusid= listItemViewBus.findViewById(R.id.tvBusid);
        TextView tvBusRoute= listItemViewBus.findViewById(R.id.tvBusRoute);


        Bus bus =busList.get(position);
        tvBusNumber.setText(bus.getBusnumber());
        tvBusid.setText(bus.getBusid());
        tvBusRoute.setText(bus.getBusroute());


        return  listItemViewBus;



}

}
