package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by JozuMaster on 30/05/2015.
 */
public class SelectPersonalDataVideogames extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/SelectPersonalDataVideogames.php";
    private Activity activity;
    private ProfileFragment profileFragment;
    private VideogamesFragment videogamesFragment;
    private ArrayList<Videogame> objectVideogames;

    public SelectPersonalDataVideogames(Activity activity, VideogamesFragment videogamesFragment, ProfileFragment profileFragment){
        this.activity = activity;
        this.profileFragment = profileFragment;
        this.videogamesFragment = videogamesFragment;
    }

    @Override
    public Boolean doInBackground(Void... params){
        this.objectVideogames = this.parseStringToVideogames(this.convertStreamToString(this.openHttpConnection(this.urlString)));
        return true;
    }

    @Override
    public void onPostExecute(Boolean result){
        if(result){
            String [] colDB = {"_id", "nameVideogame"};
            final MatrixCursor matrixCursor = new MatrixCursor(colDB);
            for(Videogame videogame: this.objectVideogames){
                matrixCursor.addRow(new Object[] {videogame.getIdVideogame(), videogame.getVideogameName()});
            }
            String [] arrayNamesCols ={"nameVideogame"};
            int [] arrayNamesViews = {R.id.textView_VideogamesFragment_textOption};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.activity, R.layout.option_videogame, matrixCursor, arrayNamesCols, arrayNamesViews, 0);
            ListView list = (ListView) this.videogamesFragment.getView().findViewById(R.id.listView_VideogamesFragment_list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int count;
                    String nameVideogame = ((TextView)view.findViewById(R.id.textView_VideogamesFragment_textOption)).getText().toString();
                    for(count = 0; !SelectPersonalDataVideogames.this.objectVideogames.get(count).getVideogameName().equalsIgnoreCase(nameVideogame); count++);
                    view.setTag(SelectPersonalDataVideogames.this.objectVideogames.get(count).getIdVideogame());
                    SelectPersonalDataVideogames.this.videogamesFragment.getActivityListener().changeVideogame(view);
                }
            });
            list.setAdapter(adapter);
            this.activity.getFragmentManager().beginTransaction()
                    .remove(this.profileFragment)
                    .show(videogamesFragment)
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

    private ArrayList<Videogame> parseStringToVideogames(String data){
        Log.v("test", data.substring(1));
        ArrayList<Videogame> objectVideogames = new ArrayList<Videogame>();
        String [] videogames = data.substring(1).trim().split("<br>");
        for(int count = 0; count < videogames.length; count++){
            String [] attributes = videogames[count].trim().split("<->");
            Videogame videogame = new Videogame();
            try {
                videogame.setIdVideogame(Integer.parseInt(attributes[0]));
                videogame.setVideogameName(attributes[1]);
                objectVideogames.add(videogame);
            }catch (NumberFormatException e){}
        }
        return objectVideogames;
    }

    private String parseStringToJSON(){
        JSONObject json = new JSONObject();
        Log.v("test", this.profileFragment.getTag());
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
