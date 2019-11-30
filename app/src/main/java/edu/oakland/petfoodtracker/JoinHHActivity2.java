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
import java.util.Locale;

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

        System.out.println(name + password + id);

        Cursor res = db.rawQuery(String.format("SELECT id, name FROM household WHERE hex_code='%s' AND password='%s'", id, password), null);
        res.moveToFirst();
        int count = res.getCount();

        if(count == 1) // We have an exact match, password is correct
        {
            int household_id = res.getInt(0);
            String household_name = res.getString(1);

            // Add this user as a non-manager to this household
            db.execSQL(String.format(Locale.US, "INSERT INTO user (name, household_id, manager) VALUES ('%s', '%d', 0)", name, household_id));

            // Find the ID of the newly created user
            Cursor sqluser = db.rawQuery(String.format(Locale.US, "SELECT id FROM user WHERE name='%s' AND household_id='%d'", name, household_id), null);
            sqluser.moveToFirst();
            int userid = sqluser.getInt(0);

            // Set the local user for this phone as the current user
            db.execSQL(String.format(Locale.US, "UPDATE local SET user_id=%d, household_id=%d WHERE id=1", userid, household_id));
            Intent intent = new Intent(this, JoinHHSuccessActivity3.class);
            intent.putExtra("user_name", name);
            intent.putExtra("HH_name", household_name);
            db.close();
            startActivity(intent);
        }
        else if(count > 1) // This should never happen. This occurs if two households have the same hex code.
        {
            Toast.makeText(this, "ERROR: This hex code is not unique.", Toast.LENGTH_SHORT).show();
            db.close();
        }
        else // Either hex code is wrong or the password doesn't match.
        {
            db.close();
            Intent intent = new Intent(this, JoinHHFailActivity4.class);
            startActivity(intent);
        }
    }
}
