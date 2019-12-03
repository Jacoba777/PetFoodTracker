package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LeaveHHActivity16 extends AppCompatActivity {

    SQLiteDatabase db;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_leave_hh16 );
        tv = findViewById(R.id.RemoveHHTV1);

        refreshFields();
    }

    private void refreshFields()
    {
        int hh_id = DBHelper.getHHID(this);

        SQLiteDatabase db = DBHelper.getDB(this);
        Cursor c = db.rawQuery("SELECT name FROM household WHERE id=" + hh_id, null);
        c.moveToFirst();
        String hhname = c.getString(0);
        c.close();
        db.close();

        tv.setText(String.format("Are you sure you want to leave %s?", hhname));
    }


    public void leaveHH(View view)
    {
        db = DBHelper.getDB(this);

        int user_id = DBHelper.getUserID(this);

        // Delete the user from the household
        db.execSQL("DELETE FROM user WHERE id = " + user_id);

        // Delete the user from the local device
        db.execSQL("UPDATE local SET user_id=null, household_id=null WHERE id=1");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        db.close();
        finish();
    }

    public void finish(View view)
    {
        finish();
    }
}
