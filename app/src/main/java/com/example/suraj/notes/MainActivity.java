package com.example.suraj.notes;


import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load main fragment which display list of notes
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content,new MainFragment(),"MAIN FRAGMENT").commit();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Inconsolata-Regular.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if(RecyclerAdapter.editMode){
            RecyclerAdapter.editMode=false;
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(android.R.id.content,new MainFragment(),"MAIN FRAGMENT").commit();
        }
        else {
            super.onBackPressed();
        }
    }
}
