package com.example.collegebustrack;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Student> studentsList;
    public ListAdapter(Activity mContext,List<Student> studentsList){

        super(mContext,R.layout.list_item,studentsList);
        this.mContext=mContext;
        this.studentsList=studentsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= mContext.getLayoutInflater();
        View listItemView =inflater.inflate(R.layout.list_item,null,true);
        TextView tvName= listItemView.findViewById(R.id.tvName);
        TextView tvBranch= listItemView.findViewById(R.id.tvBranch);
        TextView tvSem= listItemView.findViewById(R.id.tvSem);
        TextView tvAdm= listItemView.findViewById(R.id.tvAdm);
        TextView tvEmail= listItemView.findViewById(R.id.tvEmail);


        Student student =studentsList.get(position);
        tvName.setText(student.getName());
        tvBranch.setText(student.getBranch());
        tvSem.setText(student.getSem());
        tvAdm.setText(student.getAdm());
        tvEmail.setText(student.getEmail());

        return  listItemView;
    }
}
