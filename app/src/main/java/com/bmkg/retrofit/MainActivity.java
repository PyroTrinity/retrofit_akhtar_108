package com.bmkg.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bmkg.retrofit.model.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvLokasi;
    TextView tvTitle, tvBreadcrumb;
    LocationAdapter adapter;

    // Stack untuk menyimpan history navigasi agar bisa "Back" ke level sebelumnya
    Stack<List<Location>> navigationHistory = new Stack<>();
    Stack<String> titleHistory = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLokasi = findViewById(R.id.rvLokasi);
        tvTitle = findViewById(R.id.tvTitle); // Pastikan ID ini ada di XML

        // Opsional: Tambahkan TextView baru di XML untuk info posisi sekarang
        // tvBreadcrumb = findViewById(R.id.tvBreadcrumb);

        rvLokasi.setLayoutManager(new LinearLayoutManager(this));

        // Load data awal (Provinsi)
        List<Location> initialList = loadLocations();

        if (initialList != null && !initialList.isEmpty()) {
            setupAdapter(initialList);
            titleHistory.push("Pilih Provinsi");
            updateUI("Pilih Provinsi");
        }
    }

    private void setupAdapter(List<Location> list) {
        adapter = new LocationAdapter(list, location -> {
            if (location.hasChildren()) {
                // MASUK LEBIH DALAM (Drill Down)
                // 1. Simpan list saat ini ke history
                navigationHistory.push(adapter.getList());
                titleHistory.push(tvTitle.getText().toString());

                // 2. Update judul (misal jadi nama provinsi/kota yang dipilih)
                String newTitle = "Pilih Wilayah di " + location.getName();
                updateUI(newTitle);

                // 3. Ganti isi adapter dengan children
                adapter.updateList(location.getChildren());

            } else {
                // SUDAH DI LEVEL TERBAWAH (Kelurahan) -> Buka Cuaca
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra("kode_wilayah", location.getCode());
                // Opsional: Kirim nama kelurahan untuk ditampilkan di judul WeatherActivity
                intent.putExtra("nama_wilayah", location.getName());
                startActivity(intent);
            }
        });
        rvLokasi.setAdapter(adapter);
    }

    // Update Text Judul
    private void updateUI(String title) {
        tvTitle.setText(title);
    }

    // Handle tombol Back fisik Android
    @Override
    public void onBackPressed() {
        if (!navigationHistory.isEmpty()) {
            // Jika ada history, kembalikan list sebelumnya
            List<Location> previousList = navigationHistory.pop();
            String previousTitle = titleHistory.pop();

            updateUI(previousTitle);
            adapter.updateList(previousList);
        } else {
            // Jika history kosong, keluar aplikasi
            super.onBackPressed();
        }
    }

    private List<Location> loadLocations() {
        List<Location> list = new ArrayList<>();
        try {
            // Ganti nama file sesuai yang dibuat di assets
            InputStream is = getAssets().open("jateng.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Location>>() {}.getType();
            list = gson.fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal load data json", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}