package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ManageMembersActivity10 extends AppCompatActivity {

    Spinner spin;

    int user_id, hh_id;

    String[] usernames;
    int[] userids;
    int userIndex;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_members10 );

        spin = findViewById(R.id.MMSpinner);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                userIndex = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        user_id = DBHelper.getUserID(this);
        hh_id = DBHelper.getHHID(this);

        updateFields();
    }
    @Override
    protected void onResume()
    {
        super.onResume();

        db = DBHelper.getDB(this);
        Cursor c = db.rawQuery("SELECT id FROM user WHERE manager=1 AND id=" + user_id, null);
        c.moveToFirst();
        int count = c.getCount();
        c.close();
        db.close();

        if(count == 0)
        {
            finish();
        }
        else
        {
            updateFields();
        }
    }

    private void updateFields()
    {
        // Get a list of users and their IDs from this household
        db = DBHelper.getDB(this);
        Cursor c = db.rawQuery("SELECT name,id FROM user WHERE household_id=" + hh_id + " AND id <> " + user_id, null);
        c.moveToFirst();
        if(c.getCount() > 0)
        {
            usernames = new String[c.getCount()];
            userids = new int[c.getCount()];

            for(int i = 0; i < c.getCount(); i++)
            {
                usernames[i] = c.getString(0);
                userids[i] = c.getInt(1);
                c.moveToNext();
            }

            // Set the spinner's options to the members of this household
            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usernames);
            spin.setAdapter(adapter);
        }
        c.close();
        db.close();
    }

    public void setManager(View view)
    {
        Intent intent = new Intent(this, MakeManagerActivity11.class);
        intent.putExtra("username", usernames[userIndex]);
        intent.putExtra("userid", userids[userIndex]);
        startActivity(intent);
    }

    public void removeMember(View view)
    {
        Intent intent = new Intent(this, RemoveHHActivity12.class);
        intent.putExtra("username", usernames[userIndex]);
        intent.putExtra("userid", userids[userIndex]);
        startActivity(intent);
    }

    public void finish(View view)
    {
        finish();
    }
}
