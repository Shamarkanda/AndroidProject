package com.example.jozumaster.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

/**
 * Created by JozuMaster on 08/05/2015.
 */
public class RegisterFragment extends DialogFragment {
    private EditText email, username, password, date;
    private AutoCompleteTextView province;
    private CheckBox genreMan, genreWoman;
    private static OnLogin activityListenerVoid = new OnLogin() {
        @Override
        public void validate(EditText user, EditText password) {}
        @Override
        public void newRegister() {}
        @Override
        public void register(EditText email, EditText username, EditText password, AutoCompleteTextView province, EditText date, CheckBox genreMan, CheckBox genreWoman, boolean isEmptyRegisterTexts){}
    };
    private OnLogin activityListener = RegisterFragment.activityListenerVoid;

    public RegisterFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        this.email = (EditText) rootView.findViewById(R.id.editText_RegisterFragment_email);
        this.username = (EditText) rootView.findViewById(R.id.editText_RegisterFragment_username);
        this.password = (EditText) rootView.findViewById(R.id.editText_RegisterFragment_password);
        this.province = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView_RegisterFragment_provinces);
        this.genreMan = (CheckBox) rootView.findViewById(R.id.checkBox_RegisterFragment_man);
        this.genreWoman = (CheckBox) rootView.findViewById(R.id.checkBox_RegisterFragment_woman);
        this.date = (EditText) rootView.findViewById(R.id.editText_RegisterFragment_date);
        this.date.setInputType(InputType.TYPE_NULL);
        ImageButton register = (ImageButton) rootView.findViewById(R.id.imageButton_RegisterFragment_register);
        this.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date = "";
                        date += day + "/";
                        date += month + "/";
                        date += year;
                        RegisterFragment.this.date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment.this.activityListener.register(RegisterFragment.this.email, RegisterFragment.this.username, RegisterFragment.this.password, RegisterFragment.this.province,
                        RegisterFragment.this.date, RegisterFragment.this.genreMan, RegisterFragment.this.genreWoman, RegisterFragment.this.isEmptyRegisterTexts());
            }
        });
        this.genreMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterFragment.this.genreMan.isChecked()){
                    RegisterFragment.this.genreWoman.setChecked(false);
                }
            }
        });
        this.genreWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterFragment.this.genreWoman.isChecked()){
                    RegisterFragment.this.genreMan.setChecked(false);
                }
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
        this.activityListener = RegisterFragment.activityListenerVoid;
    }

    private boolean isEmptyRegisterTexts(){
        boolean isOneSelectedGenre = this.genreMan.isChecked() || this.genreWoman.isChecked();
        return this.isEmpty(this.email) || this.isEmpty(this.username) || this.isEmpty(this.password) || this.isEmpty(this.province) || this.isEmpty(this.date) || !isOneSelectedGenre;
    }

    private boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().equals("");
    }
}
