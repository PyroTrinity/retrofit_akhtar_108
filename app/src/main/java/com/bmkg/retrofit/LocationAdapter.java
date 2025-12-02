package com.bmkg.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmkg.retrofit.model.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> list;
    private OnItemClick listener;

    public interface OnItemClick {
        void onClick(Location location);
    }

    public LocationAdapter(List<Location> list, OnItemClick listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateList(List<Location> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    public List<Location> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location loc = list.get(position);
        holder.name.setText(loc.getName());
        holder.code.setText(loc.getCode());

        holder.itemView.setOnClickListener(v -> listener.onClick(loc));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, code;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            code = itemView.findViewById(R.id.tvCode);
        }
    }
}
