package com.example.nguyenkhuong.proximitycheck;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by nguyenkhuong on 12/8/15.
 */
public class NetworkAsyncTask extends AsyncTask {

    private String data;
    private static final String TAG = "PROXIMITY_CHECK";
    private String urlString = "http://10.10.13.34:3000/doAuthorization";

    public NetworkAsyncTask(String data)
    {
        this.data = data;
    }
    @Override
    protected Object doInBackground(Object[] params) {

        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( Charset.forName("UTF-8"));
        int    postDataLength = postData.length;
        String request        = "http://10.10.13.34:3000/doAuthorization";
        try {

            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setChunkedStreamingMode(0);
            OutputStream wr = new BufferedOutputStream(conn.getOutputStream());

            try
            {
                wr.write(postData);
                wr.flush();
                wr.close();
                Log.d(TAG, "Send: " + postData + "to->" + request);
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
