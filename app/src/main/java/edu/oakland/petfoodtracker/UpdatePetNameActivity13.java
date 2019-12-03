package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class UpdatePetNameActivity13 extends AppCompatActivity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_pet_name13 );

        et = findViewById(R.id.UpdatePetNameEV1);
    }

    public void changePet(View view)
    {
        String newname = et.getText().toString();

        if(newname.length() < 1)
        {
            Toast.makeText(this, "The pet's name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SQLiteDatabase db = DBHelper.getDB(this);
            int hh_id = DBHelper.getHHID(this);

            db.execSQL(String.format(Locale.US, "UPDATE household SET pet_name='%s' WHERE id=%d", newname, hh_id));
            Toast.makeText(this, "The pet's name has been updated to " + newname + "!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void finish(View view)
    {
        finish();
    }
}
