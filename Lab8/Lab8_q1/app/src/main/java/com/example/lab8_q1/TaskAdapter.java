package com.example.lab8_q1;

import android.app.*;
import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<Task> list;
    DBHelper db;

    public TaskAdapter(Context context, ArrayList<Task> list, DBHelper db) {
        this.context = context;
        this.list = list;
        this.db = db;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);

        TextView txt = view.findViewById(R.id.txt);
        Button delete = view.findViewById(R.id.delete);
        Button edit = view.findViewById(R.id.edit);

        Task t = list.get(i);

        txt.setText(t.name + " | " + t.date + " | " + t.priority);

        delete.setOnClickListener(v -> {
            db.deleteTask(t.id);
            list.remove(i);
            notifyDataSetChanged();
        });

        edit.setOnClickListener(v -> {
            showEditDialog(t);
        });

        return view;
    }

    private void showEditDialog(Task t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.activity_main, null);

        EditText name = v.findViewById(R.id.name);
        EditText date = v.findViewById(R.id.date);
        Spinner priority = v.findViewById(R.id.priority);

        name.setText(t.name);
        date.setText(t.date);

        builder.setView(v);
        builder.setPositiveButton("Update", (d, w) -> {
            t.name = name.getText().toString();
            t.date = date.getText().toString();
            t.priority = priority.getSelectedItem().toString();

            db.updateTask(t);
            notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
