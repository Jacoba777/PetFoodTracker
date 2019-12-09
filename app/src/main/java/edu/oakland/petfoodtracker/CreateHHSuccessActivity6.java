package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class CreateHHSuccessActivity6 extends AppCompatActivity {

    TextView tv;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_hhsuccess6 );

        tv = findViewById(R.id.CreateHHSuccessTV1);
        et = findViewById(R.id.CreateHHSuccessET3);

        updateFields();
    }

    private void updateFields()
    {
        int user_id = DBHelper.getUserID(this);
        int hh_id = DBHelper.getHHID(this);

        SQLiteDatabase db = DBHelper.getDB(this);

        // Get user's name
        Cursor c = db.rawQuery("SELECT name FROM user WHERE id=" + user_id, null);
        c.moveToFirst();
        String username = c.getString(0);
        c.close();

        // Get household name and hex code
        c = db.rawQuery("SELECT name,hex_code FROM household WHERE id=" + hh_id, null);
        c.moveToFirst();
        String hhname = c.getString(0);
        String hex = c.getString(1);
        c.close();

        db.close();

        tv.setText(String.format(Locale.US, "Congrats, %s! You've just created %s household. \n Please send this ID to your household members along with your household password.", username, hhname));
        et.setText(hex);
    }

    public void finish(View view)
    {
        finish();
    }
}
