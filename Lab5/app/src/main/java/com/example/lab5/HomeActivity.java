package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnVehicle, btnTravel, btnMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnVehicle = findViewById(R.id.btnVehicle);
        btnTravel = findViewById(R.id.btnTravel);
        btnMovie = findViewById(R.id.btnMovie);

        btnVehicle.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        btnTravel.setOnClickListener(v ->
                startActivity(new Intent(this, TravelActivity.class)));
        btnMovie.setOnClickListener(v ->
                startActivity(new Intent(this, MovieActivity.class)));
    }
}
