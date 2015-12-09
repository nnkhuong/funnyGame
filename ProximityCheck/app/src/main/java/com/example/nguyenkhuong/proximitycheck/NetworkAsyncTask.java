package com.example.nguyenkhuong.proximitycheck;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nguyenkhuong on 12/8/15.
 */
public class NetworkAsyncTask extends AsyncTask {

    private String data;
    private static final String TAG = "PROXIMITY_CHECK";
    private String urlString = "http://10.10.35.18:3000/doAuthorization";

    public NetworkAsyncTask(String data)
    {
        this.data = data;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        try {
            Log.d(TAG, urlString);
            URL url = new URL(urlString);

            String urlParameters = "";

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            httpURLConnection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language", "en-US");

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
            writer.write("");
            writer.flush();
            writer.close();


            //dataOutputDtream.writeBytes(urlParameters);
            //dataOutputDtream.flush();
            //dataOutputDtream.close();

        }
        catch (MalformedURLException e)
        {
            Log.d(TAG, "MalformedURLException" + e.getMessage());
        }
        catch(IOException e)
        {
            Log.d(TAG, "IOException" + e.getMessage());
        }

        return null;
    }
}
