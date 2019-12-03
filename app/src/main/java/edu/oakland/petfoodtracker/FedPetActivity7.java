package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class FedPetActivity7 extends AppCompatActivity {

    SQLiteDatabase db;
    TextView txt_lastfed;
    Button btn_feed, btn_leave;

    boolean isManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fed_pet7 );

        txt_lastfed = findViewById(R.id.fedpetTV1);
        btn_feed = findViewById(R.id.fedpetBtn1);
        btn_leave = findViewById(R.id.fedpetBtn2);
        refreshFields();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView( R.layout.activity_fed_pet7 );

        txt_lastfed = findViewById(R.id.fedpetTV1);
        btn_feed = findViewById(R.id.fedpetBtn1);
        btn_leave = findViewById(R.id.fedpetBtn2);
        refreshFields();
    }

    public void leaveHH(View view)
    {
        if(isManager)
        {
            Intent intent = new Intent(this, ManageHHActivity9.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, LeaveHHActivity16.class);
            startActivity(intent);
        }
    }

    public void feedPet(View view)
    {
        txt_lastfed = findViewById(R.id.fedpetTV1);
        db = DBHelper.getDB(this);
        long unixNow = System.currentTimeMillis() / 1000L;

        // Get local user's household ID
        int hh_id = DBHelper.getHHID(this);
        int user_id = DBHelper.getUserID(this);

        // Get user name
        Cursor cursor =  db.rawQuery("SELECT name FROM user WHERE id='" + user_id + "'", null);
        cursor.moveToFirst();
        String user_name = cursor.getString(0);
        cursor.close();

        // Set pet last fed date to now
        db.execSQL(String.format(Locale.US, "UPDATE household SET pet_fed_unix=%d, last_feeder='%s' WHERE id=%d", unixNow, user_name, hh_id));
        db.close();

        refreshFields();
    }

    private void refreshFields()
    {
        db = DBHelper.getDB(this);

        // Get local user's ID and household ID
        int hh_id = DBHelper.getHHID(this);
        int user_id = DBHelper.getUserID(this);

        // Get pet last fed date
        Cursor cursor =  db.rawQuery("SELECT pet_fed_unix, pet_name, last_feeder FROM household WHERE id='" + hh_id + "'", null);
        cursor.moveToFirst();
        long unixLastFed = cursor.getLong(0);
        String pet_name = cursor.getString(1);
        String last_feeder = cursor.getString(2);
        cursor.close();

        // Determine if this user is a manager or not
        cursor = db.rawQuery("SELECT manager FROM user WHERE id=" + user_id, null);
        cursor.moveToFirst();
        isManager = (cursor.getInt(0) == 1);
        cursor.close();

        db.close();

        btn_feed.setText("I fed " + pet_name);

        if(unixLastFed == 0)
        {
            String diff = pet_name + " has not been fed yet.";
            txt_lastfed.setText(diff);
        }
        else
        {
            long unixNow = System.currentTimeMillis() / 1000L;

            String diff = pet_name + " was fed " + calcUnixDiff(unixLastFed, unixNow) + " by " + last_feeder;
            txt_lastfed.setText(diff);
        }

        if(isManager)
        {
            btn_leave.setText("Manage Household");
        }
        else
        {
            btn_leave.setText("Leave Household");
        }
    }

    private String calcUnixDiff(long u1, long u2)
    {
        long diff = u2 - u1;

        if(diff < 60)
            return "just now";
        else if(diff < 60 * 60)
            return (diff / 60) + " minutes ago";
        else if(diff < 60 * 60 * 24)
            return (diff / 60 / 60) + " hours ago";
        else
            return (diff / 60 / 60 / 24) + " days ago";
    }
}
