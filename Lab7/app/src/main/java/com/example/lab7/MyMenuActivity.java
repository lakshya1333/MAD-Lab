package com.example.lab7;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyMenuActivity extends AppCompatActivity {

    private ImageView imageViewMenuIcon;
    private ImageView imageViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_my_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageViewMenuIcon = findViewById(R.id.imageViewMenuIcon);
        imageViewDisplay = findViewById(R.id.imageViewDisplay);

        imageViewMenuIcon.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Image -1");
        popup.getMenu().add("Image -2");

        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            imageViewDisplay.setVisibility(View.VISIBLE);
            
            if (title.equals("Image -1")) {
                imageViewDisplay.setImageResource(android.R.drawable.ic_menu_gallery);
                Toast.makeText(this, "Displaying Gallery Icon Content", Toast.LENGTH_SHORT).show();
            } else if (title.equals("Image -2")) {
                imageViewDisplay.setImageResource(android.R.drawable.ic_menu_camera);
                Toast.makeText(this, "Displaying Camera Icon Content", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        popup.show();
    }
}
