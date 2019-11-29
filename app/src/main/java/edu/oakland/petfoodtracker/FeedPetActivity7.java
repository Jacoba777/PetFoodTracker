package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class FeedPetActivity7 extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed_pet7 );
    }

    // TODO: Move to OK button on confirm once it is loaded
    public void leaveHH(View view)
    {
        db = DBHelper.getDB(this);

        Cursor cursor =  db.rawQuery("SELECT user_id, household_id FROM local", null);
        cursor.moveToFirst();

        int user_id = cursor.getInt(0);

        // Delete the user from the household
        db.execSQL("DELETE FROM user WHERE id = " + user_id);

        // Delete the user from the local device
        db.execSQL("UPDATE local SET user_id=null, household_id=null WHERE id=1");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        db.close();
        finish();
    }
}
