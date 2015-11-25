package com.ips.nnkhuong.indoorpositiontracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView greeting;
    Button btnTracker;
    Button btnWifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view-elements in activity_main.xml
        greeting = (TextView)findViewById(R.id.tvIPS);
        greeting.setTextSize(25);

        btnTracker= (Button)findViewById(R.id.btnTracker);
        btnTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onTracker(v);
            }
        });

        btnWifiList= (Button)findViewById(R.id.btnWifiList);
        btnWifiList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onWifiList(v);
            }
        });
    }

    // the method is called when Tracker button taped
    protected void onTracker(View v)
    {
        if(v.getId() == R.id.btnTracker) {
            greeting.setText("Tracking");
        }
    }

    // the method is called when Wifi List button taped
    protected void onWifiList(View v)
    {
        if(v.getId() == R.id.btnWifiList) {
            //greeting.setText("List of Wifi Info");
            Intent intent = new Intent(MainActivity.this, WifiList.class);
            startActivity(intent);
        }
    }

}
