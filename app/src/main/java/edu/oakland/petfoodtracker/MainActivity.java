package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayAdapter adapter;
    ArrayList<String> arr_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = DBHelper.getDB(this);
        arr_name = new ArrayList<>();

        Cursor res = db.rawQuery("SELECT * FROM household WHERE id > 0", null);
        res.moveToFirst();
        int count = res.getCount();
        if(count > 0)
            do {
                arr_name.add(res.getString(1));
            } while (res.moveToNext());

        db.execSQL("DELETE FROM household WHERE hex_code = \"0001-4747\"");

        //list = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, R.layout.list_item, arr_name);
        //list.setAdapter(adapter);

        db.close();
    }

    public void route_joinHH(View view)
    {
        Intent intent = new Intent(this, JoinHHActivity2.class);
        startActivity(intent);
    }

    public void route_createHH(View view)
    {
        Intent intent = new Intent(this, CreateHHActivity5.class);
        startActivity(intent);
    }
}
