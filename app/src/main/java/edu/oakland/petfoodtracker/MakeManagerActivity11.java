package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MakeManagerActivity11 extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_make_manager11 );

        txt = findViewById(R.id.MakeManagerTV1);

        refreshFields();
    }

    private void refreshFields()
    {
        String name = this.getIntent().getStringExtra("username");

        txt.setText(String.format(Locale.US, "Are you sure you want to make %s the new manager " +
        "for this household? This will remove you as the manager. You will not be able to undo this later without asking them to set you as the household manager again.", name));
    }

    public void makeManager(View view)
    {
        int newmanagerid = this.getIntent().getIntExtra("userid", -1);
        int userid = DBHelper.getUserID(this);

        SQLiteDatabase db = DBHelper.getDB(this);

        // Remove this user as manager
        db.execSQL("UPDATE user SET manager=0 WHERE id=" + userid);

        // Add the selected user as the new manager
        db.execSQL("UPDATE user SET manager=1 WHERE id=" + newmanagerid);

        finish();
    }

    public void finish(View view)
    {
        finish();
    }
}
