package com.example.suraj.notes.notes_database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by achara on 8/15/2017.
 */

@Table(database = InitDataBase.class)
public class Notes extends BaseModel {
    public static final String TEXT = "TEXT";
    public static final String TEXT_HEADER = "TEXT_HEADER";
    public static final String ID = "ID";
    public static final String COLOR_CODE = "COLOR_CODE";
    public static final String LAST_EDIT="LAST_EDIT";
    @Column
    @PrimaryKey(autoincrement = true)

    int id;

    @Column
    String noteText;

    @Column
    String noteTextHeader;

    @Column
    int colorCode;

    @Column
    String lastEdited;
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

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }
}
