package com.example.suraj.notes.notes_database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by achara on 8/15/2017.
 */

@Table(database = InitDataBase.class)
public class Notes extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)

    int id;

    @Column
    String noteText;

    @Column
    String noteTextHeader;

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNoteTextHeader() {
        return noteTextHeader;
    }

    public void setNoteTextHeader(String noteTextHeader) {
        this.noteTextHeader = noteTextHeader;
    }

}
