package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class ManageHHActivity9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_hh9 );
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SQLiteDatabase db = DBHelper.getDB(this);
        Cursor c = db.rawQuery("SELECT id FROM user WHERE manager=1 AND id=" + DBHelper.getUserID(this), null);
        c.moveToFirst();
        int count = c.getCount();
        c.close();
        db.close();

        if(count == 0)
        {
            finish();
        }
    }

    public void manageMembers(View view)
    {
        Intent intent = new Intent(this, ManageMembersActivity10.class);
        startActivity(intent);
    }

    public void changePet(View view)
    {
        Intent intent = new Intent(this, UpdatePetNameActivity13.class);
        startActivity(intent);
    }

    public void changePasswd(View view)
    {
        Intent intent = new Intent(this, ChangePasswordActivity14.class);
        startActivity(intent);
    }

    public void deleteHH(View view)
    {
        Intent intent = new Intent(this, DeleteHHActivity15.class);
        startActivity(intent);
    }
}
