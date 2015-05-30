package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JozuMaster on 30/05/2015.
 */
public class PlayersFragment extends ListFragment {
    private static OnChangePlayer activityListenerVoid = new OnChangePlayer() {
        @Override
        public void changePlayer(View view) {}
    };

    private OnChangePlayer activityListener = PlayersFragment.activityListenerVoid;

    public PlayersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_players, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.activityListener = (OnChangePlayer) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.activityListener = PlayersFragment.activityListenerVoid;
    }

    public OnChangePlayer getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(OnChangePlayer activityListener) {
        this.activityListener = activityListener;
    }
}
