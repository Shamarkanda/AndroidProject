package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public class LoginActivity extends Activity implements OnLogin{
    private ProgressDialog spinner;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.spinner = new ProgressDialog(this);
        this.spinner.setMessage("Cargando...");
        this.spinner.setIndeterminate(false);
        LoginFragment loginFragment = new LoginFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_LoginActivity_container, loginFragment)
                .commit();
    }

    @Override
    public void validate(EditText username, EditText password){
        new SelectPersonalData(this, this.spinner, username, password).execute();
    }

    @Override
    public void newRegister(){
        RegisterFragment registerFragment = new RegisterFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_LoginActivity_container, registerFragment)
                .commit();
        new SelectProvinces(this, registerFragment).execute();
    }

    @Override
    public void register(EditText email, EditText username, EditText password, AutoCompleteTextView province, EditText date, CheckBox genreMan, CheckBox genreWoman, boolean isEmptyRegisterTexts){
        if(isEmptyRegisterTexts){
            Toast.makeText(this, "Rellene todos los datos, por favor", Toast.LENGTH_SHORT).show();
        }else{
            new InsertLogin(this, this.spinner, email, username, password, province, date, genreMan, genreWoman).execute();
        }
    }
}
