package com.ips.nnkhuong.indoorpositiontracker;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.os.Bundle;

import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.util.Log;

/**
 * Created by nguyenkhuong on 11/26/15.
 */
public class MapTracker extends Activity{

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tracker);
        image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.verihall47_2);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        image.setAdjustViewBounds(true);

        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Clicked Second Image",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}
