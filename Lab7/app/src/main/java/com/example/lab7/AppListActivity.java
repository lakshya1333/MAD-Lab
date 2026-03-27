package com.example.lab7;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity implements AppAdapter.OnItemLongClickListener {

    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private List<AppInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_list);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_app_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewApps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appList = getInstalledApps();
        adapter = new AppAdapter(appList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<AppInfo> getInstalledApps() {
        List<AppInfo> apps = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : packages) {
            String name = appInfo.loadLabel(pm).toString();
            String packageName = appInfo.packageName;
            android.graphics.drawable.Drawable icon = appInfo.loadIcon(pm);
            String version = "Unknown";
            try {
                PackageInfo pInfo = pm.getPackageInfo(packageName, 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            long size = new File(appInfo.sourceDir).length();
            boolean isSystemApp = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

            apps.add(new AppInfo(name, packageName, icon, version, size, isSystemApp));
        }
        return apps;
    }

    @Override
    public void onItemLongClick(AppInfo appInfo) {
        String type = appInfo.isSystemApp ? "System App" : "User Installed App";
        String hasPermissions = checkSpecialPermissions(appInfo.packageName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(appInfo.name)
                .setMessage("Type: " + type + "\n" + hasPermissions)
                .setItems(new String[]{"Open", "Uninstall", "View Details"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // Open
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
                            if (launchIntent != null) {
                                startActivity(launchIntent);
                            } else {
                                Toast.makeText(this, "Cannot open this app", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1: // Uninstall
                            if (appInfo.isSystemApp) {
                                Toast.makeText(this, "System apps cannot be uninstalled", Toast.LENGTH_SHORT).show();
                            } else {
                                confirmUninstall(appInfo.packageName);
                            }
                            break;
                        case 2: // View Details
                            Intent intent = new Intent(this, DetailsActivity.class);
                            intent.putExtra("packageName", appInfo.packageName);
                            startActivity(intent);
                            break;
                    }
                })
                .show();
    }

    private String checkSpecialPermissions(String packageName) {
        StringBuilder sb = new StringBuilder("Special Permissions:\n");
        PackageManager pm = getPackageManager();
        String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION};
        String[] names = {"Camera", "Location"};

        boolean found = false;
        for (int i = 0; i < permissions.length; i++) {
            if (pm.checkPermission(permissions[i], packageName) == PackageManager.PERMISSION_GRANTED) {
                sb.append("- ").append(names[i]).append(" Access: Enabled\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No special permissions (Camera/Location) enabled.";
    }

    private void confirmUninstall(String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Uninstall App")
                .setMessage("Are you sure you want to uninstall this application?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }
}
