package com.example.suraj.notes;

import android.app.Activity;
import android.graphics.Color;
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
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import static com.example.suraj.notes.notes_database.Notes.COLOR_CODE;
import static com.example.suraj.notes.notes_database.Notes.ID;
import static com.example.suraj.notes.notes_database.Notes.LAST_EDIT;
import static com.example.suraj.notes.notes_database.Notes.TEXT;
import static com.example.suraj.notes.notes_database.Notes.TEXT_HEADER;
import static com.example.suraj.notes.notes_database.Notes.TODO_DATE;

/**
 * Created by achara on 8/12/2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NotesHolder> {

    List<Notes> userNotes;
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
        View separator;


        public NotesHolder(View itemView) {
            super(itemView);
            noteText = (TextView) itemView.findViewById(R.id.noteText);
            selectImg = (ImageView) itemView.findViewById(R.id.selectImg);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.framelayout);
            header = (TextView) itemView.findViewById(R.id.noteTextHeader);
            separator=itemView.findViewById(R.id.separator);
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
        String noteText=userNotes.get(position).getNoteText();
        String noteheader=userNotes.get(position).getNoteTextHeader();
        if(!noteText.equals("")&&!noteheader.equals("")){
            holder.noteText.setText(noteText);
            holder.header.setText(noteheader);
            holder.noteText.setTextColor(getTextColorList().get(userNotes.get(position).getColorCode()));
            holder.header.setTextColor(getTextColorList().get(userNotes.get(position).getColorCode()));
        }else if(!noteText.equals("")){
            holder.noteText.setText(noteText);
            holder.noteText.setTextSize(18);
            holder.separator.setVisibility(View.GONE);
            holder.header.setVisibility(View.GONE);
            holder.noteText.setTextColor(getTextColorList().get(userNotes.get(position).getColorCode()));
            holder.noteText.setTypeface(TypefaceUtils.load(activity.getAssets(),"fonts/Inconsolata-Bold.ttf"));
        }else if(!noteheader.equals("")){
            holder.noteText.setVisibility(View.GONE);
            holder.separator.setVisibility(View.GONE);
            holder.header.setText(noteheader);
            holder.header.setTextColor(getTextColorList().get(userNotes.get(position).getColorCode()));
        }

        holder.header.setTypeface(TypefaceUtils.load(activity.getAssets(),"fonts/Inconsolata-Bold.ttf"));

        holder.frameLayout.setBackgroundColor(userNotes.get(position).getColorCode());
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
                    bundle.putString(TEXT, userText);
                    bundle.putString(TEXT_HEADER, userTextHeader);
                    bundle.putInt(ID, userNotes.get(position).getId());
                    bundle.putInt(COLOR_CODE,userNotes.get(position).getColorCode());
                    bundle.putString(LAST_EDIT,userNotes.get(position).getLastEdited());
                    bundle.putString(TODO_DATE,userNotes.get(position).getTodoDate());
                    notesFragment.setArguments(bundle);
                    activity.getFragmentManager().beginTransaction().replace(android.R.id.content, notesFragment).addToBackStack(null).commit();
                }
            }
        });
        holder.frameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editMode = true;
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

    private HashMap<Integer,Integer> getTextColorList() {

        HashMap<Integer,Integer> colorCodes=new HashMap<>();
        colorCodes.put(Color.parseColor("#FF0000"),Color.parseColor("#FFFFFF"));
        colorCodes.put(Color.parseColor("#FFC0CB"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#800080"),Color.parseColor("#FFFFFF"));
        colorCodes.put(Color.parseColor("#0000ff"),Color.parseColor("#FFFFFF"));
        colorCodes.put(Color.parseColor("#008080"),Color.parseColor("#FFFFFF"));
        colorCodes.put(Color.parseColor("#f8bbd0"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#c0ca33"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#00796b"),Color.parseColor("#FFFFFF"));
        colorCodes.put(Color.parseColor("#00e5ff"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#FFEBCD"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#fffde7"),Color.parseColor("#000000"));
        colorCodes.put(Color.parseColor("#2979ff"),Color.parseColor("#FFFFFF"));

        return colorCodes;

    }
}
