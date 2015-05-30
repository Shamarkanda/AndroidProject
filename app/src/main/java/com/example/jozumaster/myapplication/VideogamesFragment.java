package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by JozuMaster on 27/05/2015.
 */
public class VideogamesFragment extends ListFragment {
    private static OnChangeVideogame activityListenerVoid = new OnChangeVideogame(){
        @Override
        public void changeVideogame(View view) {}
    };

    private OnChangeVideogame activityListener = VideogamesFragment.activityListenerVoid;

    public VideogamesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_videogames, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            this.activityListener = (OnChangeVideogame) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.activityListener = VideogamesFragment.activityListenerVoid;
    }

    public OnChangeVideogame getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(OnChangeVideogame activityListener) {
        this.activityListener = activityListener;
    }
}
