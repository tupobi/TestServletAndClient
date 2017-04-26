package com.example.administrator.testjson;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Manager> mManagers;

    public void setMManagers(List<Manager> mManagers){
        this.mManagers = mManagers;
    }

    public RecyclerViewAdapter(ArrayList<Manager> managers){
        this.mManagers = managers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Manager manager = mManagers.get(position);
        holder.tvUserName.setText(manager.getUname());
        holder.tvUserPwd.setText(manager.getUpwd());
    }

    @Override
    public int getItemCount() {
        return mManagers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName, tvUserPwd;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_userName);
            tvUserPwd = (TextView) itemView.findViewById(R.id.tv_userPwd);
        }
    }

    public void dataChanged(){
        notifyDataSetChanged();
    }
}
