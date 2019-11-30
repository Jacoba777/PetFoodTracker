package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JoinHHFailActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_join_hhfail4 );
    }

    public void route_joinHH(View view)
    {
        Intent intent = new Intent(this, JoinHHActivity2.class);
        startActivity(intent);
    }

    public void close(View view)
    {
        finish();
    }
}
