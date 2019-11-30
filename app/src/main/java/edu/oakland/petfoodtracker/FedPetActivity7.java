package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class FedPetActivity7 extends AppCompatActivity {

    SQLiteDatabase db;
    TextView txt_lastfed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fed_pet7 );

        txt_lastfed = findViewById(R.id.fedpetTV2);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView( R.layout.activity_fed_pet7 );

        refreshLastFed();
    }

    // TODO: Move to OK button on confirm once it is loaded
    // TODO: Add logic to delete the household if there are no members left after leaving
    // TODO: Prompt for household to be deleted if the manager deletes it
    public void leaveHH(View view)
    {
        db = DBHelper.getDB(this);

        Cursor cursor =  db.rawQuery("SELECT user_id, household_id FROM local", null);
        cursor.moveToFirst();

        int user_id = cursor.getInt(0);
        cursor.close();

        // Delete the user from the household
        db.execSQL("DELETE FROM user WHERE id = " + user_id);

        // Delete the user from the local device
        db.execSQL("UPDATE local SET user_id=null, household_id=null WHERE id=1");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        db.close();
        finish();
    }

    public void feedPet(View view)
    {
        db = DBHelper.getDB(this);
        long unixNow = System.currentTimeMillis() / 1000L;

        // Get local user's household ID
        Cursor cursor =  db.rawQuery("SELECT household_id, user_id FROM local", null);
        cursor.moveToFirst();
        int hh_id = cursor.getInt(0);
        int user_id = cursor.getInt(1);
        cursor.close();

        // Get user name
        cursor =  db.rawQuery("SELECT name FROM user WHERE id='" + user_id + "'", null);
        cursor.moveToFirst();
        String user_name = cursor.getString(0);
        cursor.close();

        // Get pet last fed date
        db.execSQL(String.format(Locale.US, "UPDATE household SET pet_fed_unix=%d, last_feeder='%s' WHERE id=1", unixNow, user_name));
        db.close();

        refreshLastFed();
    }

    private void refreshLastFed()
    {
        db = DBHelper.getDB(this);

        // Get local user's household ID
        Cursor cursor =  db.rawQuery("SELECT household_id FROM local", null);
        cursor.moveToFirst();
        int hh_id = cursor.getInt(0);
        cursor.close();

        // Get pet last fed date
        cursor =  db.rawQuery("SELECT pet_fed_unix, pet_name, last_feeder FROM household WHERE id='" + hh_id + "'", null);
        cursor.moveToFirst();
        long unixLastFed = cursor.getLong(0);
        String pet_name = cursor.getString(1);
        String last_feeder = cursor.getString(2);
        cursor.close();
        db.close();

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

        System.out.println(txt_lastfed.getText());
    }

    private String calcUnixDiff(long u1, long u2)
    {
        long diff = u1 - u2;

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
