package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public class LoginFragment extends Fragment {
    private EditText username, password;
    private static OnLogin activityListenerVoid = new OnLogin(){
        @Override
        public void validate(EditText user, EditText password){}
        @Override
        public void newRegister(){}
        @Override
        public void register(EditText email, EditText username, EditText password, AutoCompleteTextView province, EditText date, CheckBox genreMan, CheckBox genreWoman, boolean isEmptyRegisterTexts){}
    };
    private OnLogin activityListener = activityListenerVoid;

    public LoginFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        this.username = (EditText) rootView.findViewById(R.id.editText_LoginFragment_username);
        this.password = (EditText) rootView.findViewById(R.id.editText_LoginFragment_password);
        Button validate = (Button) rootView.findViewById(R.id.button_LoginFragment_login);
        Button register = (Button) rootView.findViewById(R.id.button_LoginFragment_register);
        SharedPreferences myPreferences = this.getActivity().getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        this.username.setText(myPreferences.getString("USERNAME", ""));
        this.password.setText(myPreferences.getString("PASSWORD", ""));
        validate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.validate(LoginFragment.this.username,LoginFragment.this.password);
            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.newRegister();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.activityListener = (OnLogin) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.activityListener = LoginFragment.activityListenerVoid;
    }
}
