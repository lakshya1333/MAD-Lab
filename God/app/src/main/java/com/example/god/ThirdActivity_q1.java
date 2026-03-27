package com.example.god;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class ThirdActivity_q1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_q1);

        View rootLayout = findViewById(R.id.root_layout_q1);
        EditText etBookTitle = findViewById(R.id.etBookTitle_q1);
        EditText etReview = findViewById(R.id.etReview_q1);
        ListView lvRating = findViewById(R.id.lvRating_q1);
        Button btnSubmit = findViewById(R.id.btnSubmitReview_q1);
        Button btnPrev = findViewById(R.id.btnPrev3_q1);

        String[] ratings = {"1 - Poor", "2 - Fair", "3 - Good", "4 - Very Good", "5 - Excellent"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, ratings);
        lvRating.setAdapter(adapter);
        lvRating.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etBookTitle.getText().toString();
                String review = etReview.getText().toString();
                if (title.isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter a book title", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(rootLayout, "Review submitted for: " + title, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}