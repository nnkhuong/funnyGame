package com.example.nguyenkhuong.proximitycheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.zbar.Symbol;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private String longitude;
    private String latitude;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean LocationAvailable;
    private String urlString;
    private static final String TAG = "PROXIMITY_CHECK";
    private static final String action = "/doAuthorization";
    static {
        System.loadLibrary("iconv");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.textView);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                double pLon = location.getLongitude();
                double pLat = location.getLatitude();
                longitude = Double.toString(pLon);
                latitude = Double.toString(pLat);

                Log.d(TAG, "Longitude:" + longitude);
                //textView.setText(longitude);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Check permisson and then Register the listener with the Location Manager to receive location updates
        LocationAvailable = checkPermission();
        if(LocationAvailable) {
            Log.d(TAG, "Location is available");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        else {
            Log.d(TAG, "Location no available");
            requestPermission();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AppSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, ZBarConstants.URL_CONFIG_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSend() {
        try {
            Log.d(TAG, urlString);
            URL url = new URL(urlString);

            String urlParameters = "fName=" + URLEncoder.encode("???", "UTF-8") +
                            "&lName=" + URLEncoder.encode("???", "UTF-8");

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            httpURLConnection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language", "en-US");

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            DataOutputStream dataOutputDtream = new DataOutputStream(httpURLConnection.getOutputStream());
            try {
                dataOutputDtream.writeBytes(urlParameters);
                dataOutputDtream.flush();
                dataOutputDtream.close();
            }
            catch (Exception e)
            {
                
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
    }
    public void launchQRScanner(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBarConstants.ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBarConstants.ZBAR_SCANNER_REQUEST:
            case ZBarConstants.ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {

                    textView.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                    onSend();
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ZBarConstants.URL_CONFIG_REQUEST:
                if(resultCode == RESULT_OK)
                {
                    urlString = data.getStringExtra(ZBarConstants.URL_RESULT) + action;
                    Log.d(TAG, urlString);
                }
                break;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocationAvailable = checkPermission();
        if(LocationAvailable) {
            Log.d(TAG, "Location is available");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        else {
            Log.d(TAG, "Location no available");
            requestPermission();
        }


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        LocationAvailable = checkPermission();
        if(LocationAvailable) {
            // Remove the listener you previously added
            locationManager.removeUpdates(locationListener);
        }

    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){
            LocationAvailable = true;
            return true;
        } else {
            LocationAvailable = false;
            return false;
        }
    }
    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "This app relies on location data for it's main functionality. Please enable GPS data to access all features.", Toast.LENGTH_LONG).show();
        } else {

        }
    }
}
