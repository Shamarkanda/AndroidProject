package com.example.jozumaster.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JozuMaster on 04/05/2015.
 */
public class ProfileFragment extends Fragment {
    private Person person;

    public ProfileFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Person person = (Person) this.getArguments().getSerializable(SelectPersonalData.KEY_PERSON);
        TextView username = (TextView) rootView.findViewById(R.id.textView_ProfileFragment_textUser);
        username.setText(person.getUsername());
        return rootView;
    }


}
