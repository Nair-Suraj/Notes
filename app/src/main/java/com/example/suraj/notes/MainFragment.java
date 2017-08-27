package com.example.suraj.notes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suraj.notes.notes_database.Notes;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by achara on 8/6/2017.
 */

public class MainFragment extends Fragment {
    public RecyclerAdapter adapter;
    public FloatingActionButton fab;
    private List<Notes> userNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        getActivity().setTitle(R.string.app_name);
        fab = (FloatingActionButton) view.findViewById(R.id.fabNewNotes);


        initDatabase();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        userNotes = SQLite.select()
                .from(Notes.class)
                .queryList();
        adapter = new RecyclerAdapter(fab, userNotes, getActivity());
        recyclerView.setAdapter(adapter);

        if(userNotes.size()==0){
            TapTargetView.showFor(getActivity(),
                    TapTarget.forView(fab, "Note it down....\nCreate your first note")
                            .targetCircleColor(R.color.colorPrimaryDark)
                            .dimColor(R.color.colorPrimaryDark)
                            .cancelable(false)
                            .textTypeface(TypefaceUtils.load(getActivity().getAssets(),"fonts/Inconsolata-Regular.ttf"))
                            .textColor(android.R.color.black)
                            .icon(getActivity().getResources().getDrawable(R.drawable.ic_edit, null)),

                    new TapTargetView.Listener() {
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);
                            fabClick();
                        }
                    });
        }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fabClick();
                }
            });


        return view;
    }

    public void initDatabase() {
        FlowManager.init(new FlowConfig.Builder(getActivity()).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }

    public void fabClick() {
        if (RecyclerAdapter.editMode) {
            if (fab.getTag().equals("Delete")) {
                ArrayList<Integer> selectedItemList = RecyclerAdapter.selectedPositions;

                for (int i = 0; i < selectedItemList.size(); i++) {
                    userNotes.get(selectedItemList.get(i)).delete();

                }
                selectedItemList.clear();
                adapter.setUserNotes(userNotes);
                adapter.notifyDataSetChanged();
                RecyclerAdapter.editMode = false;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(android.R.id.content, new MainFragment(), "MAIN FRAGMENT").commit();
            }
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(android.R.id.content, new NotesFragment(), "NOTES FRAGMENT").addToBackStack(null).commit();
        }
    }
}
