package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
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

/**
 * Created by JozuMaster on 03/05/2015.
 */
public class MenuFragment extends ListFragment {

    private static OnChange activityListenerVoid = new OnChange() {
        @Override
        public void change(View view){}
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
    public void onDetach(){
        super.onDetach();
        this.activityListener = MenuFragment.activityListenerVoid;
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id){
        TextView textOption = (TextView) view.findViewById(R.id.textView_MenuFragment_textOption);
        textOption.setTag(textOption.getText().toString());
        activityListener.change(textOption);
    }
}
