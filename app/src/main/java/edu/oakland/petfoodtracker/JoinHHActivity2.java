package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoinHHActivity2 extends AppCompatActivity {

    SQLiteDatabase db;

    EditText et_name, et_password, et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_join_hh2 );

        et_name = findViewById(R.id.URNmET);
        et_password = findViewById(R.id.HHPWET);
        et_id = findViewById(R.id.HHIDET);
    }

    public void joinHH(View view)
    {
        db = DBHelper.getDB(this);

        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        String id = et_id.getText().toString();

        Cursor res = db.rawQuery("SELECT id, name FROM household WHERE hex_code = '" + id + "' AND password = '" + password + "'", null);
        res.moveToFirst();
        int count = res.getCount();

        if(count == 1) // We have an exact match, password is correct
        {
            db.execSQL("INSERT INTO user (name, household_id, manager) VALUES ('" + name + "', '" + res.getInt(0) + "', 0)");
            Intent intent = new Intent(this, JoinHHSuccessActivity3.class);
            intent.putExtra("user_name", name);
            intent.putExtra("HH_name", res.getString(1));
            startActivity(intent);
        }
        else if(count > 1) // This should never happen. This occurs if two households have the same hex code.
        {
            Toast.makeText(this, "ERROR: This hex code is not unique.", Toast.LENGTH_SHORT).show();
        }
        else // Either hex code is wrong or the password doesn't match.
        {
            Intent intent = new Intent(this, JoinHHFailActivity4.class);
            startActivity(intent);
        }

        db.close();
    }
}
