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
    private String urlString = "http://10.10.13.34:3000/doAuthorization";

    public NetworkAsyncTask(String data, String lon, String lat)
    {
        this.qrDecoded = data;
        this.longitude = lon;
        this.latitude = lat;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        String urlParameters  = "param1=a&param2=b&param3=c";
        //byte[] postData       = urlParameters.getBytes( Charset.forName("UTF-8"));
        //int    postDataLength = postData.length;
        String request        = "http://10.10.35.18:3000/doAuthorization";
        try {

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("qrstring", this.qrDecoded);
//            jsonObject.accumulate("longitude", this.longitude);
//            jsonObject.accumulate("latitude", this.latitude);

            jsonObject.put("qrstring", this.qrDecoded);
            jsonObject.put("longitude", this.longitude);
            jsonObject.put("latitude", this.latitude);
            String json = jsonObject.toString();
            Log.d(TAG, json);
            // convert JsonObject to json string
            //json = jsonObject.toString();

            // set json to StringEntity

            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            //conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setChunkedStreamingMode(0);
            //Writer wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //byte[] postData       = json.getBytes( Charset.forName("UTF-8"));
           // byte[] postData       = json.getBytes();
            //Log.d(TAG, "" + postData.toString());
            try {
                for(int i = 0; i < json.length(); ++i)
                {
                    wr.write(json.charAt(i));
                    //wr.flush();
                }
                //wr.write(json, 0, json.length() -1);
                wr.flush();
                wr.close();
                //Log.d(TAG, "Send: " + postData + "to->" + request);
            }
            catch(IOException e)
            {
                Log.e(TAG, e.toString());
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

}
