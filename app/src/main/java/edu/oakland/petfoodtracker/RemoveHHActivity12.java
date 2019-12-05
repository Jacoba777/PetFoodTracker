package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class RemoveHHActivity12 extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_remove_hh12 );

        txt = findViewById(R.id.RemoveHHTV1);

        refreshFields();
    }

    private void refreshFields()
    {
        String name = this.getIntent().getStringExtra("username");

        txt.setText(String.format(Locale.US, "Are you sure you want to remove %s from this household?", name));
    }

    public void removeMember(View view)
    {
        int victimid = this.getIntent().getIntExtra("userid", -1);

        SQLiteDatabase db = DBHelper.getDB(this);

        // Delete the victim user
        db.execSQL("DELETE FROM user WHERE id=" + victimid);

        finish();
    }

    public void finish(View view)
    {
        finish();
    }
}
