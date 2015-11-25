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
import android.util.Log;

/**
 * Created by nguyenkhuong on 11/24/15.
 */
public class WifiList extends Activity{

    WifiManager wifiManager;
    WifiScanReceiver wifiScanReceiver;
    TextView tvWifiList;
    TableLayout tableLayout = (TableLayout) findViewById(R.id.table_main);
    TableRow tbrow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_list_view);

        tvWifiList = (TextView)findViewById(R.id.tvWifiList);
        tvWifiList.setTextSize(25);

        //TableLayout tableLayout = (TableLayout) findViewById(R.id.table_main);

        TableRow tbrowSSID = new TableRow(this);
        TextView tvSSID = new TextView(this);
        tvSSID.setText(" SSID ");
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

        tbrow = new TableRow(this);


//        for (int i = 0; i < 25; i++) {
//            TableRow tbrow = new TableRow(this);
//            TextView tvSSID_Value = new TextView(this);
//            tvSSID_Value.setText("" + i);
//            tvSSID_Value.setTextColor(Color.WHITE);
//            tvSSID_Value.setGravity(Gravity.CENTER);
//            tbrow.addView(tvSSID_Value);
//
//            TextView tvBSSID_Value = new TextView(this);
//            tvBSSID_Value.setText("BSSID " + i);
//            tvBSSID_Value.setTextColor(Color.WHITE);
//            tvBSSID_Value.setGravity(Gravity.CENTER);
//            tbrow.addView(tvBSSID_Value);
//
//            TextView tvRSSI_Value = new TextView(this);
//            tvRSSI_Value.setText("Rssi." + i);
//            tvRSSI_Value.setTextColor(Color.WHITE);
//            tvRSSI_Value.setGravity(Gravity.CENTER);
//            tbrow.addView(tvRSSI_Value);
//
//            tableLayout.addView(tbrow);
//        }

        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiScanReceiver = new WifiScanReceiver(this);
        wifiManager.startScan();
    }
    @Override
    protected void onPause()
    {
        unregisterReceiver(wifiScanReceiver);
        super.onPause();
    }
    @Override
    protected void onResume()
    {
        registerReceiver(wifiScanReceiver, new IntentFilter(wifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
    private class WifiScanReceiver extends BroadcastReceiver{

        private WifiList outer;

        public WifiScanReceiver(WifiList outerClass)
        {
            outer = outerClass;
        }
        @Override
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = wifiManager.getScanResults();

            for(ScanResult record : wifiScanList)
            {
                String outPut = "SSID:" + record.SSID + "   BSSID:" + record.BSSID + "   RSSI Level:" + record.level;
                System.out.println(outPut);
                //TableRow tbrow = new TableRow(outer);
                TextView tvSSID_Value = new TextView(outer);
                tvSSID_Value.setText("" + record.SSID);
                tvSSID_Value.setTextColor(Color.WHITE);
                tvSSID_Value.setGravity(Gravity.CENTER);
                tbrow.addView(tvSSID_Value);

                TextView tvBSSID_Value = new TextView(outer);
                tvBSSID_Value.setText("BSSID " + record.BSSID);
                tvBSSID_Value.setTextColor(Color.WHITE);
                tvBSSID_Value.setGravity(Gravity.CENTER);
                tbrow.addView(tvBSSID_Value);

                TextView tvRSSI_Value = new TextView(outer);
                tvRSSI_Value.setText("Rssi." + record.level);
                tvRSSI_Value.setTextColor(Color.WHITE);
                tvRSSI_Value.setGravity(Gravity.CENTER);
                tbrow.addView(tvRSSI_Value);

                tableLayout.addView(tbrow);

            }
        }
    }
}
