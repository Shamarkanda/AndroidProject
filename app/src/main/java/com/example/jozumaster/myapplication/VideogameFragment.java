package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by JozuMaster on 28/05/2015.
 */
public class VideogameFragment extends Fragment {

    private static OnFollow activityListenerVoid = new OnFollow() {
        @Override
        public void follow(View view, Fragment fragment) {}
        @Override
        public void unFollow(View view, Fragment fragment) {}
    };

    private OnFollow activityListener = VideogameFragment.activityListenerVoid;

    public VideogameFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_videogame, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.activityListener = (OnFollow) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.activityListener = VideogameFragment.activityListenerVoid;
    }

    public OnFollow getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(OnFollow activityListener) {
        this.activityListener = activityListener;
    }
}
