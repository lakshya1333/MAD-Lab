# Android Java UI Cheatsheet

Copy only the section you need.

## 1) Date Picker (tomorrow to +20 days)

### MainActivity.java
```java
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnDate;
    private TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDate = findViewById(R.id.button);
        textDate = findViewById(R.id.textView);

        btnDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth, 0, 0, 0);
                        selectedDate.set(Calendar.MILLISECOND, 0);

                        Calendar minDate = Calendar.getInstance();
                        minDate.add(Calendar.DAY_OF_MONTH, 1);
                        minDate.set(Calendar.HOUR_OF_DAY, 0);
                        minDate.set(Calendar.MINUTE, 0);
                        minDate.set(Calendar.SECOND, 0);
                        minDate.set(Calendar.MILLISECOND, 0);

                        Calendar maxDate = Calendar.getInstance();
                        maxDate.add(Calendar.DAY_OF_MONTH, 20);
                        maxDate.set(Calendar.HOUR_OF_DAY, 23);
                        maxDate.set(Calendar.MINUTE, 59);
                        maxDate.set(Calendar.SECOND, 59);
                        maxDate.set(Calendar.MILLISECOND, 999);

                        if (!selectedDate.before(minDate) && !selectedDate.after(maxDate)) {
                            textDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        } else {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Select date after today and within 20 days",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );

            Calendar minPickerDate = Calendar.getInstance();
            minPickerDate.add(Calendar.DAY_OF_MONTH, 1);

            Calendar maxPickerDate = Calendar.getInstance();
            maxPickerDate.add(Calendar.DAY_OF_MONTH, 20);

            dialog.getDatePicker().setMinDate(minPickerDate.getTimeInMillis());
            dialog.getDatePicker().setMaxDate(maxPickerDate.getTimeInMillis());

            dialog.show();
        });
    }
}
```

```java
btnDate.setOnClickListener(v -> {
    Calendar now = Calendar.getInstance();

    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH);
    int day = now.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog dialog = new DatePickerDialog(
            MainActivity.this,
            (view, y, m, d) -> {
                String selectedDate = String.format("%04d-%02d-%02d", y, m+1, d);
                editDate.setText(selectedDate);
            },
            year, month, day
    );

    dialog.show();
});
```

## 2) Time Picker

### MainActivity.java (snippet)
```java
import android.app.TimePickerDialog;
import java.util.Calendar;

Button btnTime;
TextView textTime;

btnTime = findViewById(R.id.btnTime);
textTime = findViewById(R.id.textTime);

btnTime.setOnClickListener(v -> {
    Calendar calendar = Calendar.getInstance();

    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);

    TimePickerDialog dialog = new TimePickerDialog(
            this,
            (view, selectedHour, selectedMinute) ->
                    textTime.setText(selectedHour + ":" + selectedMinute),
            hour,
            minute,
            true
    );

    dialog.show();
    //String time = String.format("%02d:%02d:00", hour, minute);
});
```

## 3) Spinner with disabled hint item

### MainActivity.java
```java
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerWords;
    private Button btnValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerWords = findViewById(R.id.spinnerWords);
        btnValidate = findViewById(R.id.btnValidate);

        String[] words = {"Select fruit", "Apple", "Banana", "Mango", "Orange"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                words
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWords.setAdapter(adapter);

        btnValidate.setOnClickListener(v -> {
            if (spinnerWords.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select a fruit", Toast.LENGTH_SHORT).show();
            } else {
                String selected = spinnerWords.getSelectedItem().toString();
                Toast.makeText(this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

## 4) RadioGroup

### MainActivity.java (snippet)
```java
RadioGroup radioGroup = findViewById(R.id.radioGroup);
Button btnSubmit = findViewById(R.id.btnSubmit);

btnSubmit.setOnClickListener(v -> {
    int selectedId = radioGroup.getCheckedRadioButtonId();

    if (selectedId == -1) {
        Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        return;
    }

    RadioButton radioButton = findViewById(selectedId);
    String value = radioButton.getText().toString();
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
});
```

## 5) Spinner with images

### res/layout/spinner_item.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="8dp">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:layout_marginEnd="8dp"
        android:contentDescription="item image" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="16sp" />
</LinearLayout>
```

### SpinnerAdapter.java
```java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] items;
    private final int[] images;

    public SpinnerAdapter(Context context, String[] items, int[] images) {
        super(context, R.layout.spinner_item, items);
        this.context = context;
        this.items = items;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.spinner_item, parent, false);

        ImageView image = view.findViewById(R.id.imageView);
        TextView text = view.findViewById(R.id.textView);

        image.setImageResource(images[position]);
        text.setText(items[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
```

### MainActivity.java
```java
import android.os.Bundle;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;

    private final String[] fruits = {"Apple", "Banana", "Orange"};

    private final int[] images = {
            R.drawable.apple,
            R.drawable.banana,
            R.drawable.orange
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, fruits, images);
        spinner.setAdapter(adapter);
    }
}
```

## 6) Options Menu

### res/menu/menu_main.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/menu_settings"
        android:title="Settings"
        app:showAsAction="never" />

    <item
        android:id="@+id/menu_help"
        android:title="Help"
        app:showAsAction="never" />

    <item
        android:id="@+id/menu_exit"
        android:title="Exit"
        app:showAsAction="never" />
</menu>
```

## 7) AppBar with Toolbar for Menu

### MainActivity.java
```java
//res->new android directory->menu
//menu->menu.xml
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_help) {
            Toast.makeText(this, "Help Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_about) {
            Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

## 8) Context Menu (long press)

### res/menu/context_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/menu_copy" android:title="Copy" />
    <item android:id="@+id/menu_delete" android:title="Delete" />
    <item android:id="@+id/menu_share" android:title="Share" />
</menu>
```

### MainActivity.java
```java
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_copy) {
            Toast.makeText(this, "Copy clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_delete) {
            Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.menu_share) {
            Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
```

## 9) Popup Menu

### res/menu/popup_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/menu_edit" android:title="Edit" />
    <item android:id="@+id/menu_delete" android:title="Delete" />
    <item android:id="@+id/menu_share" android:title="Share" />
</menu>
```

### MainActivity.java
```java
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnMenu);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Toast.makeText(MainActivity.this, "Edit Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (item.getItemId() == R.id.menu_delete) {
                    Toast.makeText(MainActivity.this, "Delete Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (item.getItemId() == R.id.menu_share) {
                    Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });
    }
}
```

## 9.1) AlertDialog

### MainActivity.java (snippet)
```java
submit.setOnClickListener(v -> {
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Confirmation");
    builder.setMessage("Are you sure you want to proceed?");

    builder.setPositiveButton("Yes", (dialog, which) ->
            Toast.makeText(this, "Proceeding...", Toast.LENGTH_SHORT).show()
    );

    builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
    builder.show();
});
```

## 10) TabLayout + ViewPager2 (simple)

### build.gradle (Module: app)
```gradle
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.viewpager2:viewpager2:1.0.0'
```

### activity_main.xml
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

### fragment_page.xml
```xml
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/textPage"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:textSize="22sp" />
```

### PageFragment.java
```java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";

    public static PageFragment newInstance(String text) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textPage = view.findViewById(R.id.textPage);

        Bundle args = getArguments();
        textPage.setText(args != null ? args.getString(ARG_TEXT, "") : "");

        return view;
    }
}
```

### PagerAdapter.java
```java
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {

    private final String[] pages = {"Home Page", "Profile Page", "Settings Page"};

    public PagerAdapter(MainActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PageFragment.newInstance(pages[position]);
    }

    @Override
    public int getItemCount() {
        return pages.length;
    }
}
```

### MainActivity.java
```java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        PagerAdapter adapter = new PagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Home");
            else if (position == 1) tab.setText("Profile");
            else tab.setText("Settings");
        }).attach();
    }
}
```
## 10) Database (simple)
### MainActivity.java
```java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText name,email;
        MyDatabaseHelper dbHelper;
        Button insertBtn;
        TextView textView = findViewById(R.id.textView);
        dbHelper = new MyDatabaseHelper(this);

        name = findViewById(R.id.editTextText);
        email = findViewById(R.id.editTextText2);
        insertBtn = findViewById(R.id.button);

        insertBtn.setOnClickListener(v -> {
            String userName = name.getText().toString();
            String userEmail = email.getText().toString();

            long result = dbHelper.insertUser(userName, userEmail);
            if (result == -1) {
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        String[][] users = dbHelper.getUsers("John");

        if (users != null) {
            String output = "";

            for (int i = 0; i < users.length; i++) {
                output += "ID: " + users[i][0] +
                        "\nName: " + users[i][1] +
                        "\nEmail: " + users[i][2] + "\n\n";
            }

            textView.setText(output);
        } else {
            textView.setText("No users found");
        }
    }
}
```
### MyDatabaseHelper.java
```java
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MyDatabase.db";
        private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT UNIQUE);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Create the table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users"); // Drop old table
        onCreate(db); // Create new table
    }


    public long insertUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);

        long result = db.insert("Users", null, values);
        return result;
    }


    public String[] getUser(String input) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Users " +
                " WHERE name = '"  + input + "'" +
                " OR email= '" + input + "'";

        Cursor cursor = db.rawQuery(query, null);

        String[] userData = null;

        if (cursor.moveToFirst()) {
            userData = new String[3];

            userData[0] = String.valueOf(cursor.getInt(0)); // id
            userData[1] = cursor.getString(1);              // name
            userData[2] = cursor.getString(2);              // email
        }

        cursor.close();
        db.close();
        return userData;
    }

    public int updateUser(String input, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // prevent crash if input has '
        input = input.replace("'", "''");
        newName = newName.replace("'", "''");

        String query = "UPDATE Users SET name = '" + newName + "' " +
                "WHERE name = '" + input + "' " +
                "OR email = '" + input + "'";

        db.execSQL(query);

        db.close();

        return 1; // assume success (since execSQL doesn't return count)
    }


    public int deleteUser(String input) {
        SQLiteDatabase db = this.getWritableDatabase();

        // prevent crash if input has '
        input = input.replace("'", "''");

        String query = "DELETE FROM Users " +
                "WHERE name = '" + input + "' " +
                "OR email = '" + input + "'";

        db.execSQL(query);

        db.close();

        return 1; // assume success
    }

    public String[][] getUsers(String input) {
        SQLiteDatabase db = this.getReadableDatabase();

        // prevent crash for '
        input = input.replace("'", "''");

        String query = "SELECT * FROM Users " +
                "WHERE name = '" + input + "' " +
                "OR email = '" + input + "'";

        Cursor cursor = db.rawQuery(query, null);

        int rowCount = cursor.getCount();

        if (rowCount == 0) {
            cursor.close();
            db.close();
            return null;
        }

        String[][] data = new String[rowCount][3];

        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                data[i][0] = String.valueOf(cursor.getInt(0)); // id
                data[i][1] = cursor.getString(1);              // name
                data[i][2] = cursor.getString(2);              // email
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;
    }


}
```    