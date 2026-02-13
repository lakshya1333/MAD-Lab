package com.example.lab4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class TestAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_app);

        Button btn = findViewById(R.id.btnClick);
        ToggleButton toggle = findViewById(R.id.toggleBtn);

        btn.setOnClickListener(v -> showCustomToast(
                "Button Clicked",
                R.drawable.ic_launcher_background
        ));

        toggle.setOnCheckedChangeListener((b, isChecked) -> showCustomToast(
                isChecked ? "Toggle ON" : "Toggle OFF",
                R.drawable.ic_launcher_foreground
        ));
    }

    private void showCustomToast(String message, int imageRes) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);

        ImageView image = view.findViewById(R.id.toast_image);
        TextView text = view.findViewById(R.id.toast_text);

        image.setImageResource(imageRes);
        text.setText(message);

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
