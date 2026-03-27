package com.example.lab7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppInfo> appList;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(AppInfo appInfo);
    }

    public AppAdapter(List<AppInfo> appList, OnItemLongClickListener longClickListener) {
        this.appList = appList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo app = appList.get(position);
        holder.textViewName.setText(app.name);
        holder.imageViewIcon.setImageDrawable(app.icon);
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(app);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewIcon;
        TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}
