package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JozuMaster on 05/05/2015.
 */
public class InsertPersonalData extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/InsertPersonalData.php";
    private Activity activity;
    private EditText email, username, password, date;
    private AutoCompleteTextView province;
    private CheckBox genreMan, genreWoman;
    private ProgressDialog spinner;

    public InsertPersonalData(Activity activity, ProgressDialog spinner, EditText email, EditText username, EditText password, AutoCompleteTextView province, EditText date, CheckBox genreMan, CheckBox genreWoman){
        this.activity = activity;
        this.spinner = spinner;
        this.email = email;
        this.username = username;
        this.password = password;
        this.province = province;
        this.date = date;
        this.genreMan = genreMan;
        this.genreWoman = genreWoman;
    }

    @Override
    public void onPreExecute(){
        this.spinner.show();
    }

    @Override
    public Boolean doInBackground(Void... params){
        return this.openHttpConnection(this.urlString);
    }

    @Override
    public void onPostExecute(Boolean result){
        this.spinner.dismiss();
        if(result){
            Toast.makeText(this.activity, "Registro realizado con exito", Toast.LENGTH_SHORT).show();
            this.activity.getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_LoginActivity_container, new LoginFragment())
                    .commit();
        }else{
            Toast.makeText(this.activity, "Ha ocurrido un error, intentelo de nuevo", Toast.LENGTH_SHORT).show();
        }
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
        try {
            json
                    .accumulate("email", this.email.getText().toString())
                    .accumulate("username", this.username.getText().toString())
                    .accumulate("password", this.password.getText().toString())
                    .accumulate("idProvince", this.province.getTag())
                    .accumulate("birthDate", this.date.getText().toString());
            if(this.genreMan.isChecked()){
                json.accumulate("genre", "Man");
            }else{
                json.accumulate("genre", "Women");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
