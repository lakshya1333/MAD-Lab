package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(int position) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("pos", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        int pos = getArguments().getInt("pos");
        String[] content = {"Welcome to Top Stories", "Latest Sports News", "Entertainment Updates"};
        textView.setText(content[pos]);
        textView.setGravity(android.view.Gravity.CENTER);
        textView.setTextSize(24);
        return textView;
    }
}

