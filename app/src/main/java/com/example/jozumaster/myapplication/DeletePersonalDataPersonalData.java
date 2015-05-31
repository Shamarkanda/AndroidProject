package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JozuMaster on 31/05/2015.
 */
public class DeletePersonalDataPersonalData extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/DeletePersonalDataPersonalData.php";
    private Activity activity;
    private View view;

    public DeletePersonalDataPersonalData(Activity activity, View view){
        this.activity = activity;
        this.view = view;
    }

    @Override
    public Boolean doInBackground(Void... params){
        return this.openHttpConnection(this.urlString);
    }


    private Boolean openHttpConnection(String urlString){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("JSON", this.parseStringToJSON()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private String parseStringToJSON(){
        JSONObject json = new JSONObject();
        SharedPreferences sharedPreferences = this.activity.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        try {
            json
                    .accumulate("idPersonalData", sharedPreferences.getInt("IDPERSONALDATA", 0))
                    .accumulate("idFollowed", this.view.getTag());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
