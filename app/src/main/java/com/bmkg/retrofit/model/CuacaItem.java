package com.bmkg.retrofit.model;

public class CuacaItem {
    public String datetime;
    public int t;               // temperature
    public double tp;           // precipitation
    public String weather_desc;
    public String weather_desc_en;
    public String image;
    public String local_datetime;

    public String getHour() {
        // contoh: "2025-11-23 12:00:00"
        return local_datetime.substring(11, 16);
    }
}
