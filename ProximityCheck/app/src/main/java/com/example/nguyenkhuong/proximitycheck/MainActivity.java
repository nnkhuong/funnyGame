package com.example.nguyenkhuong.proximitycheck;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import net.sourceforge.zbar.Symbol;
public class MainActivity extends AppCompatActivity {

    private TextView textView;
    //private EditText textEditURL;
    private String URL;
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
        //textEditURL = (EditText) findViewById(R.id.textEdit_URL);
    }

//    public void goSettings(MenuItem menuItem)
//    {
//        textView.setText("Settings");
//        Intent intent = new Intent(this, AppSettings.class);
//
//        startActivityForResult(intent, 0);
//
//    }
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
            startActivityForResult(intent, ZBarConstants.URL_CONFIG);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        textView.setText("BC");
        switch (requestCode) {
            case ZBarConstants.ZBAR_SCANNER_REQUEST:
            case ZBarConstants.ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {

                    textView.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ZBarConstants.URL_CONFIG:
                if(resultCode == RESULT_OK)
                {
                    URL = data.getStringExtra(ZBarConstants.URL_RESULT);
                    textView.setText(URL);
                    Log.d("AppSettings", "URL:" + URL);
                }
                break;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }
}
