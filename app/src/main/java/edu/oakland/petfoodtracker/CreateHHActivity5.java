package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class CreateHHActivity5 extends AppCompatActivity {

    SQLiteDatabase db;

    EditText et_username, et_hh_name, et_hh_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_hh5 );

        et_username = findViewById(R.id.URNmET);
        et_hh_name = findViewById(R.id.HHIDET);
        et_hh_password = findViewById(R.id.HHPWET);


    }

    public void createHH(View view)
    {
        String username = et_username.getText().toString();
        String hh_name = et_hh_name.getText().toString();
        String hh_password = et_hh_password.getText().toString();
        String hex;

        if(username.length() < 1 || hh_name.length() < 1 || hh_password.length() < 1)
        {
            Toast.makeText(this, "All three of these fields are required.", Toast.LENGTH_LONG);
            return;
        }

        db = DBHelper.getDB(this);

        // Keep generating hex codes until we have a unique one
        do{
            hex = generateHex();
        } while(db.rawQuery("SELECT id FROM household WHERE hex_code = '" + hex + "'", null).getCount() > 0);

        // Create new household
        db.execSQL(String.format(Locale.US, "INSERT INTO household (hex_code, name, password) VALUES ('%s', '%s', '%s')", hex, hh_name, hh_password));

        // Get the new hosuehold's ID
        Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
        cursor.moveToFirst();
        int hh_id = cursor.getInt(0);

        // Create new user, add this user to the new household as the manager
        db.execSQL(String.format(Locale.US, "INSERT INTO user (name, household_id, manager) VALUES ('%s', '%s', '%d')", username, hh_id, 1));

        // Get the new user's ID
        cursor = db.rawQuery("select last_insert_rowid()", null);
        cursor.moveToFirst();
        int user_id = cursor.getInt(0);

        // Set the local user for this phone as the current user
        db.execSQL(String.format(Locale.US, "UPDATE local SET user_id=%d, household_id=%d WHERE id=1", user_id, hh_id));

        Intent intent = new Intent(this, CreateHHSuccessActivity6.class);
        startActivity(intent);
    }

    private String generateHex() {
        // Get a random number from 0 to 0xFFFFFFFF
        long x = (long) (Math.random() * 4294967296L);
        String hex = Long.toHexString(x).toUpperCase();

        // Pad 0s until we have 8 characters
        for (int i = hex.length(); i < 8; i++) {
            hex = "0" + hex;
        }

        // Add a hyphen to mask string
        hex = hex.substring(0, 3) + "-" + hex.substring(4);

        return hex;
    }
}
