package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by JozuMaster on 28/05/2015.
 */
public class SelectVideogame extends AsyncTask<Void, Void, Boolean> {
    private final String urlString = "http://meetgame.es/MeetGame/SelectVideogame.php";
    private Activity activity;
    private VideogameFragment videogameFragment;
    private int idVideogame;
    private Videogame videogame;
    private Bitmap imageVideogame;

    public SelectVideogame(Activity activity, VideogameFragment videogameFragment, int idVideogame){
        this.activity = activity;
        this.videogameFragment = videogameFragment;
        this.idVideogame = idVideogame;
    }

    @Override
    public Boolean doInBackground(Void... params){
        this.videogame = this.parseStringToVideogame(this.convertStreamToString(this.openHttpConnection(this.urlString)));
        this.imageVideogame = this.downloadImage(this.videogame.getVideogameImage());
        if(this.videogame.getIdVideogame() == this.idVideogame){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onPostExecute(Boolean result){
        if(result){
            View rootView = this.videogameFragment.getView();
            ImageView imageVideogame = (ImageView) rootView.findViewById(R.id.imageView_VideogameFragment_videogameImage);
            imageVideogame.setImageBitmap(this.imageVideogame);
            TextView nameVideogame = (TextView) rootView.findViewById(R.id.textView_VideogameFragment_nameVideogame);
            nameVideogame.setText(this.videogame.getVideogameName());
            TextView genre = (TextView) rootView.findViewById(R.id.textView_VideogameFragment_genre);
            genre.setText(this.videogame.getGenre());
            TextView players = (TextView) rootView.findViewById(R.id.textView_VideogameFragment_players);
            if(this.videogame.getPlayers() == 0){
                players.setText("Online");
            }else{
                players.setText(Integer.toString(this.videogame.getPlayers()));
            }
            TextView pegi = (TextView) rootView.findViewById(R.id.textView_VideogameFragment_pegi);
            pegi.setText(Integer.toString(this.videogame.getPegi()));
            TextView releaseDate = (TextView) rootView.findViewById(R.id.textView_VideogameFragment_releaseDate);
            releaseDate.setText(this.videogame.getReleaseDate());
            Button followButton = (Button) rootView.findViewById(R.id.button_VideogameFragment_followButton);
            if(this.videogame.isFollowed()){
                followButton.setText("SEGUIDO");
            }
            followButton.setTag(this.videogame.getIdVideogame());
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((Button) view).getText().equals("SEGUIR")) {
                        SelectVideogame.this.videogameFragment.getActivityListener().follow(view, SelectVideogame.this.videogameFragment);
                    } else {
                        SelectVideogame.this.videogameFragment.getActivityListener().unFollow(view, SelectVideogame.this.videogameFragment);
                    }
                }
            });
        this.activity.getFragmentManager().beginTransaction()
                .show(videogameFragment)
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

    private Videogame parseStringToVideogame(String data){
        String [] attributes = data.trim().split("<->");
        Log.v("test", data);
        if(attributes.length > 1) {
            Videogame videogame = new Videogame();
            videogame.setIdVideogame(Integer.parseInt(attributes[0]));
            videogame.setGenre(attributes[1]);
            videogame.setVideogameName(attributes[2]);
            videogame.setPlayers(Integer.parseInt(attributes[3]));
            videogame.setPegi(Integer.parseInt(attributes[4]));
            videogame.setReleaseDate(attributes[5]);
            videogame.setVideogameImage(this.getUrlImageDensity(attributes[6]));
            videogame.setFollowed(Integer.parseInt(attributes[7]) == 1);
            return videogame;
        }else{
            return new Videogame();
        }
    }


    private String parseStringToJSON(){
        JSONObject json = new JSONObject();
        SharedPreferences sharedPreferences = this.activity.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        try {
            json
                    .accumulate("idPersonalData", sharedPreferences.getInt("IDPERSONALDATA", 0))
                    .accumulate("idVideogame", this.idVideogame);
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

    private Bitmap downloadImage(String urlString){
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            return image;
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUrlImageDensity(String urlString){
        String urlImageDensity = null;
        DisplayMetrics metrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenDensity = metrics.densityDpi;
        if(screenDensity >= 120 && screenDensity < 160){
            urlImageDensity = urlString + "ldpi/videogameImage" + this.idVideogame + ".png";
        }else if(screenDensity >= 160 && screenDensity < 240){
            urlImageDensity = urlString + "mdpi/videogameImage" + this.idVideogame + ".png";
        }else if(screenDensity >= 240 && screenDensity < 320){
            urlImageDensity = urlString + "hdpi/videogameImage" + this.idVideogame + ".png";
        }else if(screenDensity >= 320 && screenDensity < 480){
            urlImageDensity = urlString + "xhdpi/videogameImage" + this.idVideogame + ".png";
        }else if(screenDensity >= 480 && screenDensity < 640){
            urlImageDensity = urlString + "xxhdpi/videogameImage" + this.idVideogame + ".png";
        }else if(screenDensity >= 640){
            urlImageDensity = urlString + "xxxhdpi/videogameImage" + this.idVideogame + ".png";
        }
        return urlImageDensity;
    }
}
