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

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView( R.layout.activity_main );

        // Switch to the pet feed activity if the local user is in a household
        if(inHousehold())
        {
            Intent intent = new Intent(this, FeedPetActivity7.class);
            startActivity(intent);
            finish();
        }
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

    public boolean inHousehold()
    {
        db = DBHelper.getDB(this);

        Cursor res = db.rawQuery("SELECT user_id, household_id, id FROM local", null);
        res.moveToFirst();
        db.close();
        int count = res.getCount();
        if(count > 0)
        {
            int user_id = res.getInt(0);
            int household_id = res.getInt(1);

            return (user_id != 0 && household_id != 0);
        }
        else
            Toast.makeText(this, "ERROR: Could not find local data to store user information!", Toast.LENGTH_SHORT).show();

        return false;
    }
}
