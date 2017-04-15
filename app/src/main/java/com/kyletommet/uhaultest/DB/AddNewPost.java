package com.kyletommet.uhaultest.DB;

import android.os.AsyncTask;
import android.util.Log;

import com.kyletommet.uhaultest.User.Post;

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
import java.net.URLEncoder;

/**
 * Created by Kyle Tommet on 4/14/2017.
 */

public class AddNewPost extends AsyncTask<Post, Void, JSONObject> {
    private DBWorkerDelegate delegate;
    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected JSONObject doInBackground(Post... params) {
        try {
            URL url = new URL("http://jsonplaceholder.typicode.com/posts");
            Post newPost = params[0];
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream os = connection.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String post_data =  URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(new Integer(newPost.getPostersID()).toString(), "UTF-8") + "&"+
                                URLEncoder.encode("title", "UTF-8")  + "=" + URLEncoder.encode(newPost.getTitle(), "UTF-8") + "&"+
                                URLEncoder.encode("body", "UTF-8")   + "=" + URLEncoder.encode(newPost.getPostBody(), "UTF-8");

            bw.write(post_data);
            bw.flush();
            bw.close();

            JSONObject results = new JSONObject(readURLReturnData(connection));

            return results;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
            Log.i("AddNewPost", "Error Reading Input Stream");
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
