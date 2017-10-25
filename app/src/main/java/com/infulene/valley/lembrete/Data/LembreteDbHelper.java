package com.infulene.valley.lembrete.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 24-Oct-17.
 *
 *
 * Classe responsa
 */

public class LembreteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lembretes.db";

    private static final int DATABASE_VERSION = 1;

    public LembreteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final  String SQL_CREATE_LEMBRETES = "CREATE TABLE " + LembreteContract.LembreteEntry.TABLE_NAME + " (" +
                LembreteContract.LembreteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LembreteContract.LembreteEntry.COLUMN_TITULO + " TEXT, " +
                LembreteContract.LembreteEntry.COLUMN_HORA + " TEXT NOT NULL, " +
                LembreteContract.LembreteEntry.COLUMN_DETALHES + " TEXT, " +
                LembreteContract.LembreteEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_LEMBRETES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LembreteContract.LembreteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
