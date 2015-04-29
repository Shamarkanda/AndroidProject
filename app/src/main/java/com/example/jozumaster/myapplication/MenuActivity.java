package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by JozuMaster on 25/04/2015.
 */
public class MenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView message = (TextView) findViewById(R.id.textView_MenuActivity_welcome);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString(LoginActivity.KEY_USER);
        message.setText("Bienvenido " + user);
    }
}
