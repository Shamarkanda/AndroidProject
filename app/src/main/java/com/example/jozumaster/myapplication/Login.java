package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class Login extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/Login.php";
    private Activity activity;
    private ProgressDialog spinner;
    private EditText username, password;
    private Person person;

    public Login(Activity activity, ProgressDialog spinner, EditText username, EditText password){
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
        this.person = this.parseStringToPerson(this.convertStreamToString(this.openHttpConnection(this.urlString)));
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
            SharedPreferences myPreferences = this.activity.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
            myPreferences.edit()
                    .putInt("IDPERSONALDATA", this.person.getIdPersonalData())
                    .putString("EMAIL", this.person.getEmail())
                    .putString("USERNAME", this.person.getUsername())
                    .putString("PASSWORD", this.person.getPassword())
                    .putString("PROVINCE", this.person.getProvince())
                    .putString("BIRTHDATE", this.person.getBirthDate())
                    .putString("GENRE", this.person.getGenre())
                    .commit();
            Toast.makeText(this.activity, "USUARIO CORRECTO", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.activity, MenuActivity.class);
            this.activity.startActivity(intent);
            this.activity.finish();
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
        String [] attributes = data.trim().split("<->");
        if(attributes.length > 1) {
            Person person = new Person();
            person.setIdPersonalData(Integer.parseInt(attributes[0]));
            person.setEmail(attributes[1]);
            person.setUsername(attributes[2]);
            person.setPassword(attributes[3]);
            person.setProvince(attributes[4]);
            person.setBirthDate(attributes[5]);
            person.setGenre(attributes[6]);
            return person;
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
