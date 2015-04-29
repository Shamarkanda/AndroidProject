package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public class LoginFragment extends Fragment {
    private EditText user, password;
    private static OnLogin activityListenerVoid = new OnLogin(){
        @Override
        public void validate(EditText user, EditText password){}
    };
    private OnLogin activityListener = activityListenerVoid;

    public LoginFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        this.user = (EditText) rootView.findViewById(R.id.editText_LoginFragment_user);
        this.password = (EditText) rootView.findViewById(R.id.editText_LoginFragment_password);
        Button login = (Button) rootView.findViewById(R.id.button_LoginFragment_login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.validate(LoginFragment.this.user,LoginFragment.this.password);
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
