package com.example.nguyenkhuong.proximitycheck;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import net.sourceforge.zbar.Symbol;

/**
 * Created by nguyenkhuong on 11/28/15.
 */
public class AppSettings extends Activity {

    private EditText editTextURL;
    private Button btnSubmit;
    private Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        editTextURL = (EditText) findViewById(R.id.textEdit_URL);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnScan = (Button) findViewById(R.id.btnURLScan);
    }

    public void onURLScan(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBarConstants.URL_QR_SCANNER_REQUEST);
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
            case ZBarConstants.URL_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {

                    editTextURL.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void onSubmit(View view)
    {
        Intent intent = new Intent();
        intent.putExtra(ZBarConstants.URL_RESULT, "" + editTextURL.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
