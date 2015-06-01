package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by JozuMaster on 03/05/2015.
 */
public class MenuFragmentStatic extends Fragment {

    private static OnChange activityListenerVoid = new OnChange() {
        @Override
        public void change(View view, Fragment fragment){}
    };

    private OnChange activityListener = MenuFragmentStatic.activityListenerVoid;

    public MenuFragmentStatic(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_static_menu, container, false);
        ImageButton menu = (ImageButton) rootView.findViewById(R.id.imageButton_MenuFragmentStatic_buttonMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            long lastClickTime = 0;
            @Override
            public void onClick(View view) {
                if(SystemClock.elapsedRealtime() - lastClickTime > 1000) {
                    lastClickTime = SystemClock.elapsedRealtime();
                    activityListener.change(view, null);
                }
            }
        });
        return rootView;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            activityListener = (OnChange) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.activityListener = MenuFragmentStatic.activityListenerVoid;
    }
}
