package com.example.mihirq;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName1, etS1_1, etS2_1, etS3_1;
    private EditText etName2, etS1_2, etS2_2, etS3_2;
    private EditText etName3, etS1_3, etS2_3, etS3_3;
    private Button btnPopup, btnNext;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        etName1 = findViewById(R.id.etName1);
        etS1_1 = findViewById(R.id.etS1_1);
        etS2_1 = findViewById(R.id.etS2_1);
        etS3_1 = findViewById(R.id.etS3_1);

        etName2 = findViewById(R.id.etName2);
        etS1_2 = findViewById(R.id.etS1_2);
        etS2_2 = findViewById(R.id.etS2_2);
        etS3_2 = findViewById(R.id.etS3_2);

        etName3 = findViewById(R.id.etName3);
        etS1_3 = findViewById(R.id.etS1_3);
        etS2_3 = findViewById(R.id.etS2_3);
        etS3_3 = findViewById(R.id.etS3_3);

        btnPopup = findViewById(R.id.btnPopup);
        btnNext = findViewById(R.id.btnNext);

        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreviewActivity.class));
            }
        });
    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Action")
                .setItems(new String[]{"Reset", "Save"}, (dialog, which) -> {
                    if (which == 0) {
                        clearFields();
                        dbHelper.clearAllData();
                        Toast.makeText(MainActivity.this, "Data Reset", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                })
                .show();
    }

    private void clearFields() {
        etName1.setText(""); etS1_1.setText(""); etS2_1.setText(""); etS3_1.setText("");
        etName2.setText(""); etS1_2.setText(""); etS2_2.setText(""); etS3_2.setText("");
        etName3.setText(""); etS1_3.setText(""); etS2_3.setText(""); etS3_3.setText("");
    }

    private void saveData() {
        if (validateFields()) {
            dbHelper.addStudent(getStudentFromFields(etName1, etS1_1, etS2_1, etS3_1));
            dbHelper.addStudent(getStudentFromFields(etName2, etS1_2, etS2_2, etS3_2));
            dbHelper.addStudent(getStudentFromFields(etName3, etS1_3, etS2_3, etS3_3));
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        return !isEmpty(etName1) && !isEmpty(etS1_1) && !isEmpty(etS2_1) && !isEmpty(etS3_1) &&
               !isEmpty(etName2) && !isEmpty(etS1_2) && !isEmpty(etS2_2) && !isEmpty(etS3_2) &&
               !isEmpty(etName3) && !isEmpty(etS1_3) && !isEmpty(etS2_3) && !isEmpty(etS3_3);
    }

    private boolean isEmpty(EditText et) {
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError("Required");
            return true;
        }
        return false;
    }

    private Student getStudentFromFields(EditText name, EditText s1, EditText s2, EditText s3) {
        return new Student(
                name.getText().toString().trim(),
                Integer.parseInt(s1.getText().toString().trim()),
                Integer.parseInt(s2.getText().toString().trim()),
                Integer.parseInt(s3.getText().toString().trim())
        );
    }
}
