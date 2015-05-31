package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by JozuMaster on 04/05/2015.
 */
public class Opacity extends AsyncTask<Void, Integer, Boolean> {
    private ImageView logo;
    private Activity activity;

    public Opacity(Activity activity){
        this.activity = activity;
        this.logo = (ImageView) activity.findViewById(R.id.logo);
    }

    @Override
    protected Boolean doInBackground(Void... params){
        for(int i = 0; i < 255; i++){
            try{
                Thread.sleep(10);
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
            Toast.makeText(this.activity, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this.activity, LoginActivity.class);
        this.activity.startActivity(intent);
        this.activity.finish();
    }

    @Override
    protected void onCancelled(){
        Toast.makeText(this.activity, "Tarea cancelada!", Toast.LENGTH_SHORT).show();
    }
}