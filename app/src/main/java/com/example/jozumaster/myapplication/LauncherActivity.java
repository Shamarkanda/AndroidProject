package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


public class LauncherActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Opacity().execute();
    }

    private class Opacity extends AsyncTask<Void, Integer, Boolean>{
        ImageView logo;

        @Override
        protected void onPreExecute(){
            logo = (ImageView) findViewById(R.id.logo);
        }

        @Override
        protected Boolean doInBackground(Void... params){
            for(int i = 0; i < 255; i++){
                try{
                    Thread.sleep(0);
                }catch(InterruptedException e){
                    cancel(true);
                    e.printStackTrace();
                }
                publishProgress(i);
                if(isCancelled()){
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            int opacidad = values[0].intValue();
            logo.setAlpha(opacidad);
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                Toast.makeText(LauncherActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onCancelled(){
            Toast.makeText(LauncherActivity.this, "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }

}
