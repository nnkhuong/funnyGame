package com.example.nguyenkhuong.proximitycheck;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by nguyenkhuong on 12/8/15.
 */
public class NetworkAsyncTask extends AsyncTask {

    private String qrDecoded;
    private String longitude;
    private String latitude;
    private static final String TAG = "PROXIMITY_CHECK";
    //private String urlString = "http://10.10.13.34:3000/doAuthorization";
    private String urlString = "http://10.10.35.18:3000/doAuthorization";
    public NetworkAsyncTask(String data, String lon, String lat)
    {
        this.qrDecoded = data;
        this.longitude = lon;
        this.latitude = lat;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        try {

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("qrstring", this.qrDecoded);
            jsonObject.put("longitude", this.longitude);
            jsonObject.put("latitude", this.latitude);
            String jsonString = jsonObject.toString();


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(jsonString.length());
            Log.d(TAG, jsonString);
            byte[] b = new byte[jsonString.length()];
            b = jsonString.getBytes();

            conn.getOutputStream().write(jsonString.getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

        return null;
    }

}
