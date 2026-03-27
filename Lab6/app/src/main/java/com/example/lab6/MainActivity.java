package com.example.lab6;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView contentTitle;
    private TextView contentDescription;
    private ImageView contentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentTitle = findViewById(R.id.content_title);
        contentDescription = findViewById(R.id.content_description);
        contentImage = findViewById(R.id.content_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_workout) {
            showWorkoutPlans();
            return true;
        } else if (id == R.id.menu_trainers) {
            showTrainers();
            return true;
        } else if (id == R.id.menu_membership) {
            showMembership();
            return true;
        } else if (id == R.id.menu_home) {
            showHome();
            return true;
        } else if (id == R.id.menu_about) {
            showAboutUs();
            return true;
        } else if (id == R.id.menu_contact) {
            showContactUs();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHome() {
        contentTitle.setText("XYZ Fitness Center");
        contentDescription.setText("Welcome to the best fitness center in town! We offer state-of-the-art equipment and professional guidance to help you reach your goals.");
        contentImage.setVisibility(View.GONE);
    }

    private void showWorkoutPlans() {
        contentTitle.setText("Workout Plans");
        contentDescription.setText("1. Weight Loss: Intensive cardio and fat-burning exercises.\n" +
                "2. Cardio: Focus on heart health and endurance.\n" +
                "3. Muscle Building: Strength training and bodybuilding.\n" +
                "4. Yoga: Flexibility and mental wellness.");
        contentImage.setVisibility(View.GONE);
    }

    private void showTrainers() {
        contentTitle.setText("Our Professional Trainers");
        contentDescription.setText("1. John Doe - Specialization: Weight Loss & HIIT\n" +
                "2. Jane Smith - Specialization: Yoga & Pilates\n" +
                "3. Mike Ross - Specialization: Bodybuilding & Strength");
        contentImage.setImageResource(R.drawable.ic_launcher_foreground);
        contentImage.setVisibility(View.VISIBLE);
    }

    private void showMembership() {
        contentTitle.setText("Membership Packages");
        contentDescription.setText("1. Basic Plan: $20/month - Access to gym area.\n" +
                "2. Pro Plan: $50/month - Access to gym + group classes.\n" +
                "3. Elite Plan: $100/month - Full access + personal trainer (2 sessions).");
        contentImage.setVisibility(View.GONE);
    }

    private void showAboutUs() {
        contentTitle.setText("About Us");
        contentDescription.setText("XYZ Fitness Center was established in 2010 with a mission to make fitness accessible to everyone. We believe in a holistic approach to health.");
        contentImage.setVisibility(View.GONE);
    }

    private void showContactUs() {
        contentTitle.setText("Contact Us");
        contentDescription.setText("Address: 123 Fitness Ave, Workout City\n" +
                "Phone: +1 (555) 123-4567\n" +
                "Email: info@xyzfitness.com");
        contentImage.setVisibility(View.GONE);
    }
}