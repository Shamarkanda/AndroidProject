package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

/**
 * Created by JozuMaster on 10/05/2015.
 */
public class SelectProvinces extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/SelectProvinces.php";
    private Activity activity;
    private RegisterFragment registerFragment;
    private String [] result;
    private HttpClient httpClient;
    private HttpPost httpPost;

    public SelectProvinces(Activity activity, RegisterFragment registerFragment){
        this.activity = activity;
        this.registerFragment = registerFragment;
    }

    @Override
    public void onPreExecute(){
        this.httpClient = new DefaultHttpClient();
        this.httpPost = new HttpPost(this.urlString);
    }

    @Override
    public Boolean doInBackground(Void... params){
        try {
            HttpResponse httpResponse = this.httpClient.execute(this.httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            this.result = Normalizer.normalize(this.convertStreamToString(inputStream), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").split("<br>");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

     @Override
     public void onPostExecute(Boolean result){
        if(result){
            final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.registerFragment.getView().findViewById(R.id.autoCompleteTextView_RegisterFragment_provinces);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity, android.R.layout.simple_dropdown_item_1line, this.result);
            autoCompleteTextView.setThreshold(2);
            autoCompleteTextView.setAdapter(adapter);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int count;
                    for(count = 0; !SelectProvinces.this.result[count].equalsIgnoreCase(((TextView)view).getText().toString()); count++);
                    autoCompleteTextView.setTag(count+1);
                }
            });
        }else {
            Toast.makeText(this.activity, "ERROR BASE DE DATOS", Toast.LENGTH_LONG).show();
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
