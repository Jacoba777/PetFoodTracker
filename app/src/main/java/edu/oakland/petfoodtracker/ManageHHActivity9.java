package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageHHActivity9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_hh9 );
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
