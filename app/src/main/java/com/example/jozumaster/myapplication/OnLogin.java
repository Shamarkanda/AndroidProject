package com.example.jozumaster.myapplication;

import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public interface OnLogin {
    public abstract void validate(EditText user, EditText password);
    public abstract void newRegister();
    public abstract void register(EditText email, EditText username, EditText password, AutoCompleteTextView province, EditText date, CheckBox genreMan, CheckBox genreWoman, boolean isEmptyRegisterTexts);
}
