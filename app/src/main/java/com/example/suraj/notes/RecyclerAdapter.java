package com.example.suraj.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by achara on 8/12/2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NotesHolder> {
    ArrayList<String> notesListHeader;
    ArrayList<String> notesList;

    public RecyclerAdapter(ArrayList<String> notesListHeader,ArrayList<String> notesList) {
        this.notesListHeader = notesListHeader;
        this.notesList=notesList;
    }

    static final class NotesHolder extends RecyclerView.ViewHolder {
        TextView header,noteText;
        public NotesHolder(View itemView) {
            super(itemView);
            header=(TextView)itemView.findViewById(R.id.header);
            noteText=(TextView)itemView.findViewById(R.id.noteText);
        }
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {
        holder.header.setText(notesListHeader.get(position));
        holder.noteText.setText(notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
