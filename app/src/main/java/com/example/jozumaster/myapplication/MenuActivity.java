package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by JozuMaster on 25/04/2015.
 */
public class MenuActivity extends Activity implements OnChange, OnChangeVideogame, OnChangePlayer, OnFollow{
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ProfileFragment profile = new ProfileFragment();
        this.sharedPreferences = this.getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        new SelectPersonalData(this, profile, this.sharedPreferences.getInt("IDPERSONALDATA", 0)).execute();
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_MenuActivity_containerLower, profile, Integer.toString(this.sharedPreferences.getInt("IDPERSONALDATA", 0)))
                .hide(profile)
                .commit();
    }

    @Override
    public void change(View view, Fragment fragment){
        if(view != null) {
            String option = view.getTag().toString();
            switch (option) {
                case "MENU":
                    MenuFragment menu = new MenuFragment();
                    new SelectCategories(this, menu).execute();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_MenuActivity_containerLower, menu, "menuFragment")
                            .commit();
                    break;
                case "1":
                    ProfileFragment profile = new ProfileFragment();
                    new SelectPersonalData(this, profile,this.sharedPreferences.getInt("IDPERSONALDATA", 0)).execute();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_MenuActivity_containerLower, profile, Integer.toString(this.sharedPreferences.getInt("IDPERSONALDATA", 0)))
                            .hide(profile)
                            .commit();
                    break;
                case "2":
                    VideogamesFragment videogames = new VideogamesFragment();
                    try{
                        Button button = (Button) view;
                        new SelectPersonalDataVideogames(this, videogames, (ProfileFragment) fragment).execute();
                        getFragmentManager().beginTransaction()
                                .add(R.id.frameLayout_MenuActivity_containerLower, videogames)
                                .hide(videogames)
                                .commit();
                    }catch (ClassCastException e) {
                        new SelectVideogames(this, videogames).execute();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_MenuActivity_containerLower, videogames)
                                .hide(videogames)
                                .commit();
                    }
                    break;
                case "3":
                    PlayersFragment players = new PlayersFragment();
                    try{
                        Button button = (Button) view;
                        new SelectPersonalDataPersonalData(this, players, (ProfileFragment) fragment).execute();
                        getFragmentManager().beginTransaction()
                                .add(R.id.frameLayout_MenuActivity_containerLower, players)
                                .hide(players)
                                .commit();
                    }catch(ClassCastException e){
                        new SelectPersonalDatas(this, players, this.sharedPreferences.getInt("IDPERSONALDATA", 0)).execute();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_MenuActivity_containerLower, players)
                                .hide(players)
                                .commit();
                    }
                    break;
            }
        }
    }

    @Override
    public void changeVideogame(View view){
        if(view != null){
            int idVideogame = Integer.parseInt(view.getTag().toString());
            VideogameFragment videogameFragment = new VideogameFragment();
            new SelectVideogame(this, videogameFragment, idVideogame).execute();
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_MenuActivity_containerLower, videogameFragment)
                    .hide(videogameFragment)
                    .commit();
        }
    }

    @Override
    public void changePlayer(View view){
        if(view != null){
            int idPersonalData = Integer.parseInt(view.getTag().toString());
            ProfileFragment profileFragment = new ProfileFragment();
            new SelectPersonalData(this, profileFragment, idPersonalData).execute();
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_MenuActivity_containerLower, profileFragment, Integer.toString(idPersonalData))
                    .hide(profileFragment)
                    .commit();
        }
    }

    @Override
    public void follow(View view, Fragment fragment){
        try{
            ProfileFragment profileFragment = (ProfileFragment) fragment;
            new InsertPersonalDataPersonalData(this, view).execute();
        }catch(ClassCastException e) {
            new InsertPersonalDataVideogames(this, view).execute();
        }
        ((Button) view).setText("SEGUIDO");
    }

    @Override
    public void unFollow(View view, Fragment fragment){
        try{
            ProfileFragment profileFragment = (ProfileFragment) fragment;
            Log.v("LLEGOOOOOO","");
            new DeletePersonalDataPersonalData(this, view).execute();
        }catch(ClassCastException e) {
            new DeletePersonalDataVideogames(this, view).execute();
        }
        ((Button)view).setText("SEGUIR");
    }
}
