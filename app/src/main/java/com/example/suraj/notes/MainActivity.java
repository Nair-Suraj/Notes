package com.example.suraj.notes;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load main fragment which display list of notes
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content,new MainFragment(),"MAIN FRAGMENT").commit();
    }

}
