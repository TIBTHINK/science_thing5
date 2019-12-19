package com.tibthink.science_thing5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Switch sw_metric;
    TextView tv_speed;
    Button button;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw_metric = findViewById(R.id.sw_metric);
        tv_speed = findViewById(R.id.tv_speed);
        button = findViewById(R.id.button);


        //check if the fuckers let me use the gps permissions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        else {
            DoShit();
        }



        this.updateSpeed(null);

        sw_metric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MainActivity.this.updateSpeed(null);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }


    public void playSound(View view) {
        mp = MediaPlayer.create(this, R.raw.button);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location){
        if (location != null){
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onProviderDisabled(String provider){

    }
    @SuppressLint("Missing Permision")
    private void DoShit() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this, "Waiting for GPS connection baby!!", Toast.LENGTH_SHORT).show();
    }

    public void updateSpeed(CLocation location){
        float nCurrentSpeed = 0;

        if(location != null){
            location.setUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }
        java.util.Formatter fmt = new java.util.Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(" ", "0");

        if(this.useMetricUnits()){
            tv_speed.setText(strCurrentSpeed + " km/h");
        } else {
            tv_speed.setText(strCurrentSpeed + " mile/h");
        }
    }

    private boolean useMetricUnits(){
        return sw_metric.isChecked();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        if (requestCode == 1000){
            if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                DoShit();
            } else {
                finish();
            }
        }
    }


}
