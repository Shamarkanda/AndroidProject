package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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
public class SelectPersonalDataPersonalData extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/SelectPersonalDataPersonalData.php";
    private Activity activity;
    private ProfileFragment profileFragment;
    private PlayersFragment playersFragment;
    private ArrayList<Person> objectPersons;

    public SelectPersonalDataPersonalData(Activity activity, PlayersFragment playersFragment, ProfileFragment profileFragment){
        this.activity = activity;
        this.playersFragment = playersFragment;
        this.profileFragment = profileFragment;
    }

    @Override
    public Boolean doInBackground(Void... params){
        this.objectPersons = this.parseStringToPersons(this.convertStreamToString(this.openHttpConnection(this.urlString)));
        return true;
    }

    @Override
    public void onPostExecute(Boolean result){
        if(result){
            String [] colDB = {"_id", "username"};
            final MatrixCursor matrixCursor = new MatrixCursor(colDB);
            for(Person person: this.objectPersons){
                matrixCursor.addRow(new Object[] {person.getIdPersonalData(), person.getUsername()});
            }
            String [] arrayNamesCols ={"username"};
            int [] arrayNamesViews = {R.id.textView_PlayersFragment_textOption};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.activity, R.layout.option_player, matrixCursor, arrayNamesCols, arrayNamesViews, 0);
            ListView list = (ListView) this.playersFragment.getView().findViewById(R.id.listView_PlayersFragment_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int count;
                    String username = ((TextView)view.findViewById(R.id.textView_PlayersFragment_textOption)).getText().toString();
                    for(count = 0; !SelectPersonalDataPersonalData.this.objectPersons.get(count).getUsername().equals(username); count++);
                    view.setTag(SelectPersonalDataPersonalData.this.objectPersons.get(count).getIdPersonalData());
                    SelectPersonalDataPersonalData.this.playersFragment.getActivityListener().changePlayer(view);
                }
            });
            list.setAdapter(adapter);
            this.activity.getFragmentManager().beginTransaction()
                    .remove(this.profileFragment)
                    .show(playersFragment)
                    .commit();
        }else{
            Toast.makeText(this.activity, "ERROR BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    }


    private InputStream openHttpConnection(String urlString){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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

    private ArrayList<Person> parseStringToPersons(String data){
        ArrayList<Person> objectPersons = new ArrayList<Person>();
        String [] persons = data.trim().split("<br>");
        for(int count = 0; count < persons.length; count++){
            String [] attributes = persons[count].trim().split("<->");
            try {
                if (Integer.parseInt(attributes[0]) != Integer.parseInt(this.profileFragment.getTag())) {
                    Person person = new Person();
                    person.setIdPersonalData(Integer.parseInt(attributes[0]));
                    person.setUsername(attributes[1]);
                    objectPersons.add(person);
                }
            }catch (NumberFormatException e){}
        }
        return objectPersons;
    }


    private String parseStringToJSON(){
        JSONObject json = new JSONObject();
        try {
            json
                    .accumulate("idPersonalData", Integer.parseInt(this.profileFragment.getTag()));
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
