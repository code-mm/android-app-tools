package com.ms.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class
MyAdapter extends RecyclerView.Adapter {
    private List<Item> datas;
    private Context context;

    public MyAdapter(Context context, List<Item> datas) {
        this.context = context;
        this.datas = datas;
    }

    public static class Item {

        String name;
        String id;

        public Item(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_my_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Item item = datas.get(position);

        viewHolder.textViewName.setText(item.name);
        viewHolder.textViewValue.setText(item.id);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewValue;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewValue = itemView.findViewById(R.id.textViewValue);
        }
    }
}
