package com.example.SmartFarmingk210.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartFarmingk210.R;
import com.example.SmartFarmingk210.dao.SysData;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Object> dataList = new ArrayList<>();

    public RecyclerViewAdapter(List<Object> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SysData data = (SysData) dataList.get(position);
        holder.dateText.setText(data.getCreateDateTime().split(" ")[0]);
        holder.humiText.setText(data.getHumi() != -1 ? data.getHumi() + "" : "无数据");
        holder.lightText.setText(data.getSomg() != -1 ? data.getSomg() + "" : "无数据");
        holder.tempText.setText(data.getTemp() != -1 ? data.getTemp() + "" : "无数据");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, tempText, humiText, lightText, trHumiText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            tempText = itemView.findViewById(R.id.tempText);
            humiText = itemView.findViewById(R.id.humiText);
            lightText = itemView.findViewById(R.id.lightText);
        }
    }
}
