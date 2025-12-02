package com.bmkg.retrofit.model;

import java.util.List;

public class Location {
    private String code;
    private String name;
    // Tambahkan list children untuk menampung sub-wilayah
    private List<Location> children;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Location> getChildren() {
        return children;
    }

    // Helper untuk cek apakah wilayah ini punya sub-wilayah (bukan kelurahan)
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}