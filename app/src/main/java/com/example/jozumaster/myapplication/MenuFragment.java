package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by JozuMaster on 03/05/2015.
 */
public class MenuFragment extends ListFragment {

    private static OnChange activityListenerVoid = new OnChange() {
        @Override
        public void change(View view, Fragment fragment){}
    };

    private OnChange activityListener = MenuFragment.activityListenerVoid;

    public MenuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.activityListener = (OnChange) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activityListener = MenuFragment.activityListenerVoid;
    }

    public OnChange getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(OnChange activityListener) {
        this.activityListener = activityListener;
    }
}
