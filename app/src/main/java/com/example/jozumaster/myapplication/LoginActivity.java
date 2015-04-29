package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public class LoginActivity extends Activity implements OnLogin{
    public static String KEY_USER = "USER";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void validate(EditText user, EditText password){
        if(password.getText().toString().equals("1234")){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra(LoginActivity.KEY_USER, user.getText().toString());
            startActivity(intent);
        }
    }
}
