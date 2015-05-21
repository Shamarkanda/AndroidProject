package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JozuMaster on 17/05/2015.
 */
public class SelectPersonalData extends AsyncTask<Void, Void, Boolean> {
    public final static String KEY_PERSON = "PERSON";
    private Activity activity;
    private ProgressDialog spinner;
    private EditText username, password;
    private Person person;

    public SelectPersonalData(Activity activity, ProgressDialog spinner, EditText username, EditText password){
        this.activity = activity;
        this.spinner = spinner;
        this.username = username;
        this.password = password;
    }

    @Override
    public void onPreExecute(){
        this.spinner.show();
    }

    @Override
    public Boolean doInBackground(Void... params){
        this.person = this.parseStringToPerson(this.convertStreamToString(this.openHttpConnection("http://meetgame.es/MeetGame/SelectPersonalData.php")));
        if(this.person.getUsername().equalsIgnoreCase(this.username.getText().toString()) && this.person.getPassword().equals(this.password.getText().toString())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onPostExecute(Boolean result){
        this.spinner.dismiss();
        if(result){
            Toast.makeText(this.activity, "USUARIO CORRECTO", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.activity, MenuActivity.class);
            intent.putExtra(SelectPersonalData.KEY_PERSON, this.person);
            this.activity.startActivity(intent);
        }else{
            Toast.makeText(this.activity, "USUARIO INCORRECTO", Toast.LENGTH_SHORT).show();
        }
    }

    private InputStream openHttpConnection(String urlString){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("username", this.username.getText().toString()));
        try{
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            return httpResponse.getEntity().getContent();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Person parseStringToPerson(String data){
        String [] attributes = data.split("-");
        if(attributes.length > 1) {
            return new Person(Integer.parseInt(attributes[0]), attributes[1], attributes[2], attributes[3], Integer.parseInt(attributes[4]), attributes[5], attributes[6]);
        }else{
            return new Person();
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        if(inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }else{
            return "";
        }
    }
}
