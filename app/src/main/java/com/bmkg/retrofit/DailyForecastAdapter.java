package com.bmkg.retrofit;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmkg.retrofit.model.DailyForecast;
import com.bumptech.glide.Glide;
import com.bmkg.retrofit.utils.SvgSoftwareLayerSetter;
import com.bmkg.retrofit.R;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private final List<DailyForecast> list;
    private final Context context;

    public DailyForecastAdapter(List<DailyForecast> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_daily_forecast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast d = list.get(position);

        holder.tvDate.setText(d.getDateLabel());
        holder.tvTempMaxMin.setText(d.getMaxTemp() + "° / " + d.getMinTemp() + "°");
        holder.tvDescDay.setText(d.getDescription());
        holder.tvRainChance.setText(d.getRainChance() + "% hujan");

        String iconUrl = d.getIconUrl().replace(" ", "%20");

        Glide.with(context)
                .as(PictureDrawable.class)
                .load(iconUrl)
                .into(new SvgSoftwareLayerSetter(holder.ivIconDay));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTempMaxMin, tvDescDay, tvRainChance;
        ImageView ivIconDay;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTempMaxMin = itemView.findViewById(R.id.tvTempMaxMin);
            tvDescDay = itemView.findViewById(R.id.tvWeatherDesc);
            tvRainChance = itemView.findViewById(R.id.tvRainChance);
            ivIconDay = itemView.findViewById(R.id.ivIconDay);
        }
    }
}
