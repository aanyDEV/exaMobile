package com.a.exa_com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    ImageButton orderbtn, infobtn, statusbtn, signoutbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        infobtn = (ImageButton) findViewById(R.id.BtnInfo);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AboutExa.class);
                startActivity(intent);
            }
        });

        statusbtn = (ImageButton) findViewById(R.id.BtnStatus);
        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AboutExa.class);
                startActivity(intent);
            }
        });

        orderbtn = (ImageButton) findViewById(R.id.BtnOrder);
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, OrderMn.class);
                startActivity(intent);
            }
        });

        signoutbtn = (ImageButton) findViewById(R.id.Btnexit);
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
