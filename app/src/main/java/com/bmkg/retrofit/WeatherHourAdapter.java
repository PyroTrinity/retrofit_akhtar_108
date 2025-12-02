package com.bmkg.retrofit;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmkg.retrofit.model.CuacaItem;
import com.bmkg.retrofit.utils.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherHourAdapter extends RecyclerView.Adapter<WeatherHourAdapter.ViewHolder> {

    private List<CuacaItem> list;
    private Context context;

    public WeatherHourAdapter(List<CuacaItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_weather_hour, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        CuacaItem c = list.get(pos);

        holder.jam.setText(c.getHour());
        holder.suhu.setText(c.t + "°");
        holder.desc.setText(c.weather_desc);

        String iconUrl = c.image.replace(" ", "%20");

        Glide.with(context)
                .as(PictureDrawable.class)
                .load(iconUrl)
                .into(new SvgSoftwareLayerSetter(holder.icon));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView jam, suhu, desc;
        ImageView icon;
        ViewHolder(View v) {
            super(v);
            jam = v.findViewById(R.id.tvJam);
            suhu = v.findViewById(R.id.tvSuhu);
            desc = v.findViewById(R.id.tvDesc);
            icon = v.findViewById(R.id.ivIcon);
        }
    }
}
