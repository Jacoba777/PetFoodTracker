package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DeleteHHActivity15 extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_delete_hh15 );

        tv = findViewById(R.id.DeleteHHTV1);
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

        tv.setText(String.format("Are you sure you want to delete %s? Everyone in the household will be removed.", hhname));
    }

    public void deleteHH(View view)
    {
        SQLiteDatabase db = DBHelper.getDB(this);

        int user_id = DBHelper.getUserID(this);
        int hh_id = DBHelper.getHHID(this);

        // Delete all users from the household
        db.execSQL("DELETE FROM user WHERE household_id = " + user_id);

        // Delete the user from the local device
        db.execSQL("UPDATE local SET user_id=null, household_id=null WHERE id=1");

        // Delete the household
        db.execSQL("DELETE FROM household WHERE id=" + hh_id);

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
