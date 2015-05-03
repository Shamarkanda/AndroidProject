package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

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
        String [] colBD = {"_id", "image", "text"};
        MatrixCursor cursor = new MatrixCursor(colBD);
        cursor.addRow(new Object [] {"0", android.R.drawable.ic_menu_my_calendar, "PERFIL"});
        cursor.addRow(new Object [] {"1",android.R.drawable.ic_menu_my_calendar, "VIDEOJUEGOS"});
        cursor.addRow(new Object [] {"2", android.R.drawable.ic_menu_my_calendar, "AMIGOS"});
        cursor.addRow(new Object [] {"3", android.R.drawable.ic_menu_my_calendar, "EVENTOS"});
        cursor.addRow(new Object [] {"4", android.R.drawable.ic_menu_my_calendar, "CONFIGURACION"});
        String [] arrayNamesCols = {"image", "text"};
        int [] arrayIdViews = {R.id.imageView_MenuFragment_imageOption, R.id.textView_MenuFragment_textOption};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.option_menu, cursor, arrayNamesCols, arrayIdViews, 0);
        this.setListAdapter(adapter);
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
        activityListener.change(view);
    }
}
