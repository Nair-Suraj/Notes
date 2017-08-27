package com.example.suraj.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suraj.notes.notes_database.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by achara on 8/12/2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NotesHolder> {
    List<Notes> userNotes;
    private int mSelectedPosition;
    private FloatingActionButton fab;
    public static boolean editMode = false;
    private Activity activity;

    public static ArrayList<Integer> selectedPositions;

    public RecyclerAdapter(FloatingActionButton fab, List<Notes> userNotes, Activity activity) {
        this.userNotes = userNotes;
        this.fab = fab;
        this.activity = activity;
        selectedPositions = new ArrayList<>();
    }

    class NotesHolder extends RecyclerView.ViewHolder {
        TextView header, noteText;
        ImageView selectImg;
        FrameLayout frameLayout;


        public NotesHolder(View itemView) {
            super(itemView);
            noteText = (TextView) itemView.findViewById(R.id.noteText);
            selectImg = (ImageView) itemView.findViewById(R.id.selectImg);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.framelayout);
            header = (TextView) itemView.findViewById(R.id.noteTextHeader);
        }
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);


        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotesHolder holder, final int position) {
        if (editMode) {
            holder.selectImg.setVisibility(View.VISIBLE);
            if (fab.getTag().equals("New"))
                fab.setImageResource(R.drawable.ic_delete);
            fab.setTag("Delete");
            if (selectedPositions.contains(position)) {
                holder.selectImg.setImageResource(R.drawable.ic_check);
            } else
                holder.selectImg.setImageResource(R.drawable.ic_uncheck);

        } else {
            holder.selectImg.setVisibility(View.GONE);
        }
        holder.noteText.setText(userNotes.get(position).getNoteText());
        holder.header.setText(userNotes.get(position).getNoteTextHeader());

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMode) {
                    if (!selectedPositions.contains(position)) {
                        selectedPositions.add(position);
                        holder.selectImg.setVisibility(View.VISIBLE);
                        holder.selectImg.setImageResource(R.drawable.ic_check);
                    } else {
                        selectedPositions.remove(selectedPositions.indexOf(position));
                        holder.selectImg.setImageResource(R.drawable.ic_uncheck);
                    }
                } else {
                    NotesFragment notesFragment = new NotesFragment();
                    String userText = userNotes.get(position).getNoteText();
                    String userTextHeader = userNotes.get(position).getNoteTextHeader();
                    Bundle bundle = new Bundle();
                    bundle.putString("TEXT", userText);
                    bundle.putString("TEXT_HEADER", userTextHeader);
                    bundle.putInt("ID", userNotes.get(position).getId());
                    notesFragment.setArguments(bundle);
                    activity.getFragmentManager().beginTransaction().replace(android.R.id.content, notesFragment).addToBackStack(null).commit();
                }
            }
        });
        holder.frameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editMode = true;
                mSelectedPosition = position;
                selectedPositions.add(position);
                notifyDataSetChanged();
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return userNotes.size();
    }

    public void setUserNotes(List<Notes> userNotes) {
        this.userNotes = userNotes;
    }
}
