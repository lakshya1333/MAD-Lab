package com.example.lab7_string;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    private Button btnFilter, btnApply;
    private EditText etInput;
    private LinearLayout searchContainer;

    private String originalText = "Digital transformation is the integration of digital technology into all areas of a business, fundamentally changing how you operate and deliver value to customers.\n\n" +
            "It's also a cultural change that requires organizations to continually challenge the status quo, experiment, and get comfortable with failure.\n\n" +
            "Digital transformation is imperative for all businesses, from the small to the enterprise.\n\n" +
            "That message comes through loud and clear from seemingly every keynote, panel discussion, article, or study related to how businesses can remain competitive and relevant as the world becomes increasingly digital.\n\n" +
            "What is digital transformation?\n\n" +
            "Because digital transformation will look different for every company, it can be hard to pinpoint a definition that applies to all.\n\n" +
            "However, in general terms, we define digital transformation as the integration of digital technology into all areas of a business resulting in fundamental changes in how businesses operate and how they deliver value to customers.\n\n" +
            "Beyond that, it's a cultural change that requires organizations to continually challenge the status quo, experiment often, and get comfortable with failure.\n\n" +
            "This sometimes means walking away from long-standing business processes that companies were built upon in favor of relatively new practices that are still being defined.";

    private String currentText;
    private String lastSearchKeyword = "";
    private int currentMode = 0; // 0: None, 1: Search, 2: Highlight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        btnFilter = findViewById(R.id.btnFilter);
        btnApply = findViewById(R.id.btnApply);
        etInput = findViewById(R.id.etInput);
        searchContainer = findViewById(R.id.searchContainer);

        currentText = originalText;
        tvContent.setText(currentText);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleApply();
            }
        });
    }

    private void showFilterMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenu().add(0, 1, 0, "Search Keywords");
        popup.getMenu().add(0, 2, 1, "Highlight");
        
        PopupMenu sortMenu = new PopupMenu(this, v); // Using a nested popup or sub-menu
        // Creating a submenu programmatically
        android.view.SubMenu sub = popup.getMenu().addSubMenu(0, 3, 2, "Sort");
        sub.add(0, 31, 0, "Alphabetical");
        sub.add(0, 32, 1, "By Relevance");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        currentMode = 1;
                        searchContainer.setVisibility(View.VISIBLE);
                        etInput.setHint("Search keywords...");
                        return true;
                    case 2:
                        currentMode = 2;
                        searchContainer.setVisibility(View.VISIBLE);
                        etInput.setHint("Words to highlight...");
                        return true;
                    case 31:
                        sortAlphabetical();
                        return true;
                    case 32:
                        sortByRelevance();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void handleApply() {
        String input = etInput.getText().toString().trim();
        if (input.isEmpty()) {
            tvContent.setText(originalText);
            searchContainer.setVisibility(View.GONE);
            return;
        }

        if (currentMode == 1) { // Search
            lastSearchKeyword = input;
            performSearch(input);
        } else if (currentMode == 2) { // Highlight
            performHighlight(input);
        }
        searchContainer.setVisibility(View.GONE);
    }

    private void performSearch(String keyword) {
        String[] lines = originalText.split("\n\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(line).append("\n\n");
            }
        }
        currentText = sb.toString().trim();
        if (currentText.isEmpty()) {
            tvContent.setText("No results found.");
        } else {
            tvContent.setText(currentText);
        }
    }

    private void performHighlight(String keyword) {
        SpannableString spannable = new SpannableString(originalText);
        String lowerContent = originalText.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int index = lowerContent.indexOf(lowerKeyword);
        while (index >= 0) {
            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = lowerContent.indexOf(lowerKeyword, index + keyword.length());
        }
        tvContent.setText(spannable);
        currentText = originalText; // Reset to original for potential subsequent sorts
    }

    private void sortAlphabetical() {
        String textToSort = tvContent.getText().toString();
        if (textToSort.equals("No results found.")) return;
        
        String[] lines = textToSort.split("\n\n");
        List<String> list = new ArrayList<>();
        for(String s : lines) if(!s.trim().isEmpty()) list.add(s.trim());
        
        Collections.sort(list);
        
        StringBuilder sb = new StringBuilder();
        for(String s : list) sb.append(s).append("\n\n");
        tvContent.setText(sb.toString().trim());
    }

    private void sortByRelevance() {
        if (lastSearchKeyword.isEmpty()) {
            Toast.makeText(this, "Search first to sort by relevance", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String textToSort = tvContent.getText().toString();
        if (textToSort.equals("No results found.")) return;

        String[] lines = textToSort.split("\n\n");
        List<String> list = new ArrayList<>();
        for(String s : lines) if(!s.trim().isEmpty()) list.add(s.trim());

        final String keyword = lastSearchKeyword.toLowerCase();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int count1 = countOccurrences(o1.toLowerCase(), keyword);
                int count2 = countOccurrences(o2.toLowerCase(), keyword);
                return Integer.compare(count2, count1); // Descending
            }
        });

        StringBuilder sb = new StringBuilder();
        for(String s : list) sb.append(s).append("\n\n");
        tvContent.setText(sb.toString().trim());
    }

    private int countOccurrences(String text, String word) {
        int count = 0;
        int index = text.indexOf(word);
        while (index != -1) {
            count++;
            index = text.indexOf(word, index + word.length());
        }
        return count;
    }
}
