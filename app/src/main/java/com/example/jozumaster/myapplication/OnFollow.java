package com.example.jozumaster.myapplication;

import android.app.Fragment;
import android.view.View;

/**
 * Created by JozuMaster on 29/05/2015.
 */
public interface OnFollow {
    public abstract void follow(View view, Fragment fragment);
    public abstract void unFollow(View view, Fragment fragment);
}
