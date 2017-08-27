package com.example.suraj.notes.notes_database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by achara on 8/15/2017.
 */

@Database(name = InitDataBase.NAME,version = InitDataBase.VERSION)
public class InitDataBase {
    public static final String NAME="Notes";
    public static final int VERSION=1;
}
