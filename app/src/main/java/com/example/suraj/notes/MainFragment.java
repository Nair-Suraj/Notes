package com.example.suraj.notes;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by achara on 8/6/2017.
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,container,false);
        ActionBar actionBar=getActivity().getActionBar();
        if(actionBar!=null){
            actionBar.setTitle(R.string.app_name);
        }
        FloatingActionButton fab=(FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load Notes Fragment

                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(android.R.id.content,new NotesFragment(),"MAIN FRAGMENT").addToBackStack(null).commit();
            }
        });


        ArrayList<String> header=new ArrayList<>();
        ArrayList<String> notesList=new ArrayList<>();

        populateData(header,notesList);

        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new RecyclerAdapter(header,notesList));
        return view;
    }

    public void populateData(ArrayList<String> header,ArrayList<String> notesList){
     header.add("12-07-1225");
        header.add("12-07-1225");
        notesList.add("asdsad" +
                "adadad");
        notesList.add("adasdasdasdasdassdfsfsfsdfdasd");
    }
}
