package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ChangePasswordActivity14 extends AppCompatActivity {

    EditText et_pw1, et_pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_change_password14 );

        et_pw1 = findViewById(R.id.ChangePasswordET1);
        et_pw2 = findViewById(R.id.ChangePasswordET2);
    }

    public void changePasswd(View view)
    {
        String newpassword = et_pw1.getText().toString();

        if(!newpassword.equals(et_pw2.getText().toString()))
        {
            Toast.makeText(this, "The password fields do not match.", Toast.LENGTH_LONG).show();
        }
        else if( newpassword.length() < 1)
        {
            Toast.makeText(this, "The password cannot be empty.", Toast.LENGTH_LONG).show();
        }
        else
        {
            SQLiteDatabase db = DBHelper.getDB(this);
            int hh_id = DBHelper.getHHID(this);

            // Hash the password for storage
            newpassword = Hash.hashPassword(newpassword).get();

            db.execSQL(String.format(Locale.US, "UPDATE household SET password='%s' WHERE id=%d", newpassword, hh_id));
            Toast.makeText(this, "The password has been updated.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void finish(View view)
    {
        finish();
    }
}
