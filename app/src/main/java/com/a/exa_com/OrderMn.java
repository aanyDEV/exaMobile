package com.a.exa_com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.firebase.auth.FirebaseAuth;

public class OrderMn extends Activity {

    ImageButton laptopbtn, computerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        laptopbtn = (ImageButton) findViewById(R.id.laptopbtn);
        laptopbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderMn.this, LaptopMn.class);
                startActivity(intent);
            }
        });

        computerbtn = (ImageButton) findViewById(R.id.computerbtn);
        computerbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderMn.this, ComputerMn.class);
                startActivity(intent);
            }
        });
    }
}
