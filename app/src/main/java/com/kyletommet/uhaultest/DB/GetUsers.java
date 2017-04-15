package com.kyletommet.uhaultest.DB;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kyle Tommet on 4/14/2017.
 */

public class GetUsers extends AsyncTask<Void, Void, JSONObject>{
    private DBWorkerDelegate delegate;
    private final String TAG = "GetUsers";
    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected JSONObject doInBackground(Void... params) {
        try{
            URL url = new URL("http://jsonplaceholder.typicode.com/users");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            JSONObject result = new JSONObject(readURLReturnData(connection));
            //connection.disconnect();
            //result.put("wasASuccess", true);
            return result;
        }catch(MalformedURLException ex) {
            Log.e(TAG, ex.getMessage());
        }catch(IOException ex){
            Log.e(TAG, ex.getMessage());
        }catch(JSONException ex){
            Log.e(TAG, ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        this.delegate.taskFinished(jsonObject);
    }
    private String readURLReturnData(HttpURLConnection connection){
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try{
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while((inputLine = br.readLine()) != null){
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch(Exception e){
            Log.i(TAG, "Error Reading Input Stream");
            result = null;
        }
        finally{
            if(is != null){
                try{
                    is.close();
                }
                catch(IOException e){}
            }
        }
        return result;
    }
}
