package com.a.exa_com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LaptopMn extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    Button button;
    TextView selection_merk;
    Spinner spin_merk;
    CheckBox sharelok;
    String MerkLap;
    EditText nama, tipe, keluhan, textTipe, textNama, textTelp, textKeluhan;
    String[] merk = {"HP", "Asus", "Lenovo", "Acer", "Fujitsu", "Axioo"};
    HashMap<String, String[]> hash_merk = new HashMap<String, String[]>();
    TextView tvLatitude;
    TextView tvLongitude;
    public static String tvLongi;
    public static String tvLati;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_mn);

        selection_merk = (TextView) findViewById(R.id.selection_merk);
        Spinner spin = (Spinner) findViewById(R.id.spin_merk);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item, merk);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaptopMn.this, Dashboard.class);
                startActivity(intent);
            }
        });

        textNama = (EditText)findViewById(R.id.editNama);
        textTelp = (EditText)findViewById(R.id.editTelp);
        textKeluhan = (EditText)findViewById(R.id.editKeluhan);
        textTipe = (EditText)findViewById(R.id.editTipe);
        sharelok = (CheckBox) findViewById(R.id.Sharelok);
        MerkLap = spin.getSelectedItem().toString();

        SimpleDateFormat tgl = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat jam = new SimpleDateFormat("H:mm:ss");
        final String d = tgl.format(new Date());
        final String j = jam.format(new Date());

        button = (Button) findViewById(R.id.buttonOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Servis Laptop");
                DatabaseReference inputdb = myRef.child("L"+System.currentTimeMillis());
                inputdb.child("Nama").setValue(textNama.getText().toString());
                inputdb.child("Telp").setValue(textTelp.getText().toString());
                inputdb.child("Tipe").setValue(textTipe.getText().toString());
                inputdb.child("Merk").setValue(MerkLap);
                inputdb.child("Keluhan").setValue(textKeluhan.getText().toString());
                CheckBox Chk = (CheckBox) findViewById(R.id.Sharelok);
                if (Chk.isChecked()) {
                    inputdb.child("Longtitude").setValue(tvLongi);
                    inputdb.child("Lalitude").setValue(tvLati);
                    Toast.makeText(LaptopMn.this, "Form berhasil dibuat", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LaptopMn.this, "Mohon share lokasi anda", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Silahkan isi form order", Toast.LENGTH_LONG).show();
    }


    /* Request updates at startup */
    @Override
    public void onResume() {
        super.onResume();
        getLocation();
    }

    /* Remove the locationlistener updates when Activity is paused */

    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }


    //mengambil longtitude latitude
    @Override
    public void onLocationChanged(Location location) {
        tvLongi = String.valueOf(location.getLongitude());
        tvLati = String.valueOf(location.getLatitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(LaptopMn.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

}
