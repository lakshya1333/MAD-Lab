package com.example.lab7;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String packageName = getIntent().getStringExtra("packageName");
        if (packageName == null) {
            finish();
            return;
        }

        ImageView iconView = findViewById(R.id.imageViewDetailIcon);
        TextView nameView = findViewById(R.id.textViewDetailName);
        TextView packageView = findViewById(R.id.textViewDetailPackage);
        TextView versionView = findViewById(R.id.textViewDetailVersion);
        TextView sizeView = findViewById(R.id.textViewDetailSize);
        TextView typeView = findViewById(R.id.textViewDetailType);
        TextView permissionsView = findViewById(R.id.textViewDetailPermissions);

        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            PackageInfo pInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

            iconView.setImageDrawable(appInfo.loadIcon(pm));
            nameView.setText(appInfo.loadLabel(pm));
            packageView.setText(packageName);
            versionView.setText("Version: " + pInfo.versionName);
            
            long sizeInBytes = new File(appInfo.sourceDir).length();
            double sizeInMb = sizeInBytes / (1024.0 * 1024.0);
            sizeView.setText(String.format("Size: %.2f MB", sizeInMb));

            boolean isSystemApp = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
            typeView.setText("App Type: " + (isSystemApp ? "System" : "User"));

            StringBuilder permissions = new StringBuilder();
            if (pInfo.requestedPermissions != null) {
                for (String permission : pInfo.requestedPermissions) {
                    permissions.append(permission).append("\n");
                }
            } else {
                permissions.append("No permissions requested.");
            }
            permissionsView.setText(permissions.toString());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            finish();
        }
    }
}
