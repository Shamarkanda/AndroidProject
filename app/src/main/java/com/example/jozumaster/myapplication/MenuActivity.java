package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by JozuMaster on 25/04/2015.
 */
public class MenuActivity extends Activity implements OnChange {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Serializable person = this.getIntent().getExtras().getSerializable(SelectPersonalData.KEY_PERSON);
        ProfileFragment profile = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectPersonalData.KEY_PERSON, person);
        profile.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_MenuActivity_containerLower, profile)
                .commit();
    }

    @Override
    public void change(View view){
        if(view != null) {
            String option = view.getTag().toString();
            switch (option) {
                case "MENU":
                    MenuFragment menu = new MenuFragment();
                    new SelectCategories(this, menu).execute();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_MenuActivity_containerLower, menu)
                            .commit();
                    break;
                case "PERFIL":
                    ProfileFragment profile = new ProfileFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_MenuActivity_containerLower, profile)
                            .commit();
                    break;
            }
        }
    }
}
