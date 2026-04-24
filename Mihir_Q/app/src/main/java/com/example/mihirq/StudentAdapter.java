package com.example.mihirq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private List<Student> students;
    private DBHelper dbHelper;

    public StudentAdapter(Context context, List<Student> students, DBHelper dbHelper) {
        this.context = context;
        this.students = students;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        }

        Student student = students.get(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvMarks = convertView.findViewById(R.id.tvMarks);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvName.setText(student.getName());
        tvMarks.setText("S1: " + student.getSubject1() + ", S2: " + student.getSubject2() + ", S3: " + student.getSubject3());

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteStudent(student.getId());
            students.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Student Deleted", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
