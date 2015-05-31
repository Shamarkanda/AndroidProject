package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by JozuMaster on 30/05/2015.
 */
public class SelectPersonalData extends AsyncTask<Void, Void, Boolean>{
    private final String urlString = "http://meetgame.es/MeetGame/SelectPersonalData.php";
    private Activity activity;
    private ProfileFragment profileFragment;
    private int idPersonalData;
    private Person person;

    public SelectPersonalData(Activity activity, ProfileFragment profileFragment, int idPersonalData){
        this.activity = activity;
        this.profileFragment = profileFragment;
        this.idPersonalData = idPersonalData;
    }

    @Override
    public Boolean doInBackground(Void... params){
        this.person = this.parseStringToPerson(this.convertStreamToString(this.openHttpConnection(this.urlString)));
        if(this.person.getIdPersonalData() == this.idPersonalData){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onPostExecute(Boolean result){
        if(result){
            View rootView = this.profileFragment.getView();
            TextView username = (TextView) rootView.findViewById(R.id.textView_ProfileFragment_usernameText);
            username.setText(this.person.getUsername());
            Button friendsButton = (Button) rootView.findViewById(R.id.button_ProfileFragment_friendsButton);
            friendsButton.setText(Integer.toString(this.person.getFriendsNumber()));
            friendsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectPersonalData.this.profileFragment.getActivityListener().change(view, SelectPersonalData.this.profileFragment);
                }
            });
            Button videogamesButton = (Button) rootView.findViewById(R.id.button_ProfileFragment_videogamesButton);
            videogamesButton.setText(Integer.toString(this.person.getVideogamesNumber()));
            videogamesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectPersonalData.this.profileFragment.getActivityListener().change(view, SelectPersonalData.this.profileFragment);
                }
            });
            Button followButon = (Button) rootView.findViewById(R.id.button_ProfileFragment_followButton);
            followButon.setTag(this.person.getIdPersonalData());
            if(this.person.isFollowed()){
                followButon.setText("SEGUIDO");
            }
            if(this.idPersonalData == this.activity.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE).getInt("IDPERSONALDATA", 0)){
                followButon.setVisibility(View.INVISIBLE);
                followButon.setEnabled(false);
            }
            followButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((Button) view).getText().toString().equals("SEGUIR")) {
                        ((OnFollow) SelectPersonalData.this.profileFragment.getActivityListener()).follow(view, SelectPersonalData.this.profileFragment);
                    }else{
                        ((OnFollow) SelectPersonalData.this.profileFragment.getActivityListener()).unFollow(view, SelectPersonalData.this.profileFragment);
                    }
                }
            });
            this.activity.getFragmentManager().beginTransaction()
                    .show(this.profileFragment)
                    .commit();
        }else{
            Toast.makeText(this.activity, "ERROR BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    }


    private InputStream openHttpConnection(String urlString){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("JSON", this.parseStringToJSON()));
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
        Log.v("test", data);
        if(attributes.length > 1) {
            Person person = new Person();
            person.setIdPersonalData(Integer.parseInt(attributes[0]));
            person.setEmail(attributes[1]);
            person.setUsername(attributes[2]);
            person.setPassword(attributes[3]);
            person.setProvince(attributes[4]);
            person.setBirthDate(attributes[5]);
            person.setGenre(attributes[6]);
            person.setFriendsNumber(Integer.parseInt(attributes[7]));
            person.setVideogamesNumber(Integer.parseInt(attributes[8]));
            person.setFollowed(Integer.parseInt(attributes[9]) == 1);
            return person;
        }else{
            return new Person();
        }
    }


    private String parseStringToJSON(){
        JSONObject json = new JSONObject();
        try {
            json
                    .accumulate("idPersonalData", this.idPersonalData)
                    .accumulate("myIdPersonalData", this.activity.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE).getInt("IDPERSONALDATA", 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
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
