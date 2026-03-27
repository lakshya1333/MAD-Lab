package com.example.lab7;

import android.graphics.drawable.Drawable;

public class AppInfo {
    String name;
    String packageName;
    Drawable icon;
    String version;
    long size;
    boolean isSystemApp;

    public AppInfo(String name, String packageName, Drawable icon, String version, long size, boolean isSystemApp) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.version = version;
        this.size = size;
        this.isSystemApp = isSystemApp;
    }
}
