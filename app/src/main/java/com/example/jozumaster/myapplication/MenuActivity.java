package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by JozuMaster on 25/04/2015.
 */
public class MenuActivity extends Activity implements OnChange {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MenuFragment menu = new MenuFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_MenuActivity_containerLower, menu)
                .commit();
    }

    @Override
    public void change(View view){

    }
}
