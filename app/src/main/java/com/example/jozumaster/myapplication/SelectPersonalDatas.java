package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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
import java.util.ArrayList;

/**
 * Created by JozuMaster on 30/05/2015.
 */
public class SelectPersonalDatas extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/SelectPersonalDatas.php";
    private Activity activity;
    private PlayersFragment playersFragment;
    private int idPersonalData;
    private ArrayList<Person> objectPersons;

    public SelectPersonalDatas(Activity activity, PlayersFragment playersFragment, int idPersonalData){
        this.activity = activity;
        this.playersFragment = playersFragment;
        this.idPersonalData = idPersonalData;
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
                    for(count = 0; !SelectPersonalDatas.this.objectPersons.get(count).getUsername().equals(username); count++);
                    view.setTag(SelectPersonalDatas.this.objectPersons.get(count).getIdPersonalData());
                    SelectPersonalDatas.this.playersFragment.getActivityListener().changePlayer(view);
                }
            });
            list.setAdapter(adapter);
            this.activity.getFragmentManager().beginTransaction()
                    .show(playersFragment)
                    .commit();
        }else{
            Toast.makeText(this.activity, "ERROR BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    }


    private InputStream openHttpConnection(String urlString){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        try{
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
            if(Integer.parseInt(attributes[0]) != this.idPersonalData) {
                Person person = new Person();
                person.setIdPersonalData(Integer.parseInt(attributes[0]));
                person.setUsername(attributes[1]);
                objectPersons.add(person);
            }
        }
        return objectPersons;
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
