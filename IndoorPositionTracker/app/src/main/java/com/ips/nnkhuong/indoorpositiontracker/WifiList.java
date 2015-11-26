package com.ips.nnkhuong.indoorpositiontracker;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.graphics.Color;
import android.view.Gravity;
import android.net.wifi.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Intent;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import android.util.Log;

/**
 * Created by nguyenkhuong on 11/24/15.
 */
public class WifiList extends Activity{

    private static final int SCAN_DEPLAY = 1000; // (milisecond)
    private static final int SCAN_INTERVAL = 1000; // interval update
    WifiManager wifiManager;
    WifiScanReceiver wifiScanReceiver;
    TextView tvWifiList;
    TableLayout tableLayout;

    private boolean mPause = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_list_view);

        tvWifiList = (TextView)findViewById(R.id.tvWifiList);
        tvWifiList.setTextSize(25);

        tableLayout = (TableLayout) findViewById(R.id.table_main);
        displayWifiList(null);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiScanReceiver = new WifiScanReceiver();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mPause == false) {
                    wifiManager.startScan();
                }
            }
        }, SCAN_DEPLAY, SCAN_INTERVAL);


    }
    @Override
    protected void onPause()
    {
        unregisterReceiver(wifiScanReceiver);
        super.onPause();
        mPause = true;
    }
    @Override
    protected void onResume()
    {
        registerReceiver(wifiScanReceiver, new IntentFilter(wifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
        mPause = false;
    }
    private class WifiScanReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = wifiManager.getScanResults();
            displayWifiList(wifiScanList);
        }
    }
    private void displayWifiList(List<ScanResult> wifiScanList)
    {
        tableLayout.removeAllViewsInLayout();
        TableRow tbrowSSID = new TableRow(this);
        TextView tvSSID = new TextView(this);
        tvSSID.setText(" SSID        ");
        tvSSID.setTextColor(Color.WHITE);
        tbrowSSID.addView(tvSSID);

        TextView tvBSSID = new TextView(this);
        tvBSSID.setText(" BSSID ");
        tvBSSID.setTextColor(Color.WHITE);
        tbrowSSID.addView(tvBSSID);

        TextView tvRSSI = new TextView(this);
        tvRSSI.setText(" RSSI Level ");
        tvRSSI.setTextColor(Color.WHITE);
        tbrowSSID.addView(tvRSSI);

        tableLayout.addView(tbrowSSID);


        if(wifiScanList != null) {
            for (ScanResult record : wifiScanList) {
                TableRow tbrow = new TableRow(this);
                TextView tvSSID_Value = new TextView(this);
                tvSSID_Value.setText(record.SSID +"        ");
                tvSSID_Value.setTextColor(Color.WHITE);
                tvSSID_Value.setGravity(Gravity.LEFT);
                tbrow.addView(tvSSID_Value);

                TextView tvBSSID_Value = new TextView(this);
                tvBSSID_Value.setText(record.BSSID);
                tvBSSID_Value.setTextColor(Color.WHITE);
                tvBSSID_Value.setGravity(Gravity.CENTER);
                tbrow.addView(tvBSSID_Value);

                TextView tvRSSI_Value = new TextView(this);
                tvRSSI_Value.setText("" +record.level);
                tvRSSI_Value.setTextColor(Color.WHITE);
                tvRSSI_Value.setGravity(Gravity.CENTER);
                tbrow.addView(tvRSSI_Value);

                tableLayout.addView(tbrow);
            }

        }
    }
}
