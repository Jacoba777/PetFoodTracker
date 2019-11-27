package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper dbHelper;
    ListView list;
    ArrayAdapter adapter;

    ArrayList<Integer> arr_id;
    ArrayList<String> arr_name;
    ArrayList<String> arr_grade;
    ArrayList<String> arr_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = DBHelper.getDB(this);
        arr_name = new ArrayList<String>();

        Cursor res = DBHelper.queryDB(db, "SELECT * FROM household WHERE id > 0");
        int count = res.getCount();
        if(count > 0)
        {
            do
            {
                arr_name.add(res.getString(1));
            } while(res.moveToNext());
        }

        db.execSQL("DELETE FROM household WHERE hex_code = \"0001-4747\"");

        list = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, R.layout.list_item, arr_name);
        list.setAdapter(adapter);

        db.close();
    }
}
