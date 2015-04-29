package com.example.jozumaster.myapplication;

import android.widget.EditText;

/**
 * Created by JozuMaster on 24/04/2015.
 */
public interface OnLogin {
    public abstract void validate(EditText user, EditText password);
}
