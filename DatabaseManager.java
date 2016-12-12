package com.lindsaykroeger.mygame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;

/**
 * Created by Lindsay on 11/29/2016.
 */

public class DatabaseManager {

    public static DatabaseManager instance = null;

    public static final String DATABASE_NAME = "galaga";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "highscores";

    public static final String TABLE_COL_ID = "_id";
    public static final String TABLE_COL_NAME = "name";
    public static final String TABLE_COL_SCORE = "score";

    private Context context;

    private SQLiteDatabase database;

    public static class ScoresSQLiteOpenHelper extends SQLiteOpenHelper {

        public ScoresSQLiteOpenHelper(Context context) {
            super(context, DatabaseManager.DATABASE_NAME, null, DatabaseManager.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String generateNewTable = "create table " +
                    DatabaseManager.TABLE_NAME + " (" +
                    DatabaseManager.TABLE_COL_ID + " integer primary key autoincrement not null," +
                    DatabaseManager.TABLE_COL_NAME + " text not null, " +
                    DatabaseManager.TABLE_COL_SCORE + " real default 0.0 " + ")";
            db.execSQL(generateNewTable);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            String dropExistingTable = "DROP TABLE IF EXISTS " + DatabaseManager.TABLE_NAME;
            db.execSQL(dropExistingTable);
            onCreate(db);

        }
    }

    public static DatabaseManager getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    private DatabaseManager(Context context) {
        this.context = context;

        ScoresSQLiteOpenHelper helper = new ScoresSQLiteOpenHelper(context);
        database = helper.getWritableDatabase();

    }

    public ContentValues prepareTransaction(Scores scores) {

        ContentValues temp = new ContentValues();
        temp.put(TABLE_COL_NAME, scores.getName());
        temp.put(TABLE_COL_SCORE, scores.getScore());
        return temp;
    }

    public void addRow(Scores scores){
        ContentValues values = prepareTransaction(scores);
        try{
            database.insert(TABLE_NAME, null, values);
        }
        catch(SQLException e){
            Log.e("DatabaseManager", "Database Error! ALERT!");
        }
    }


    public Cursor getCursorForAllTableData() {

        Cursor cursor = this.database.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;

    }

    public Scores[] getAllData(){
        Cursor cursor = this.database.rawQuery(
                "select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        ArrayList<Scores> arraylist = new
                ArrayList<Scores>();
        while(cursor.isAfterLast() == false)
        {
            int number = cursor.getInt(
                    cursor.getColumnIndex(TABLE_COL_ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(TABLE_COL_NAME));
            int highScore = cursor.getInt(
                    cursor.getColumnIndex(TABLE_COL_SCORE));
            Scores score = new Scores(name, highScore, number);
            arraylist.add(score);
            cursor.moveToNext();
        }
        cursor.close();
        return arraylist.toArray(new Scores[0]);
    }

}


