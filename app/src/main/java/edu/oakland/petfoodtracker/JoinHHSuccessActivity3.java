package edu.oakland.petfoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JoinHHSuccessActivity3 extends AppCompatActivity {

    TextView txt_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_join_hhsuccess3 );

        Intent intent = this.getIntent();

        String success_msg = String.format("Congrats, %s! You've just joined the %s household.", intent.getStringExtra("user_name"), intent.getStringExtra("HH_name"));

        txt_success = findViewById(R.id.JoinHHSuccessTV);
        txt_success.setText(success_msg);
    }
}
