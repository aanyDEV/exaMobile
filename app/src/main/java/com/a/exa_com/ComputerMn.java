package com.a.exa_com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class ComputerMn extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    ArrayList<String> areas = new ArrayList<>();
    Button button;
    TextView selection_jenis;
    String JenCom;
    EditText nama, tipe, keluhan, textTelp, textKeluhan, textNama;
    String[] jenis = {"Pribadi", "Kantoran", "Desain", "Gamming"};
    HashMap<String, String[]> hash_jenis = new HashMap<String, String[]>();
    TextView tvLatitude;
    TextView tvLongitude;
    public static String tvLongi;
    public static String tvLati;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_computer_mn);
        selection_jenis = (TextView) findViewById(R.id.selection_jenis);
        Spinner spin = (Spinner) findViewById(R.id.spin_jenis);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, jenis);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        textNama = (EditText) findViewById(R.id.editName);
        textTelp = (EditText) findViewById(R.id.editTelp);
        textKeluhan = (EditText) findViewById(R.id.editKeluhan);
        CheckBox Chk = (CheckBox) findViewById(R.id.Sharelok);
        JenCom = spin.getSelectedItem().toString();

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
                DatabaseReference inputdb = database.getReference("Servis Computer");
                DatabaseReference myRef = inputdb.child("C"+System.currentTimeMillis());
                myRef.child("Nama").setValue(textNama.getText().toString());
                myRef.child("Telp").setValue(textTelp.getText().toString());
                myRef.child("Jenis").setValue(JenCom);
                myRef.child("Keluhan").setValue(textKeluhan.getText().toString());
                myRef.child("Tanggal").setValue(d);
                myRef.child("Jam").setValue(j);

                CheckBox Chk = (CheckBox) findViewById(R.id.Sharelok);
                if (Chk.isChecked()) {
                    myRef.child("Longtitude").setValue(tvLongi);
                    myRef.child("Lalitude").setValue(tvLati);
                    Toast.makeText(ComputerMn.this, "Form berhasil dibuat", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ComputerMn.this, "Mohon share lokasi anda", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComputerMn.this, Dashboard.class);
                startActivity(intent);
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
        Toast.makeText(ComputerMn.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
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

