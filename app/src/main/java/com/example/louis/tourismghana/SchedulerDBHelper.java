package com.example.louis.tourismghana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Louis on 4/21/2018.
 */

public class SchedulerDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Scheduler_DB";
    public static final String TABLE_NAME = "Tour_Schedule";
    public static final String COL_ID = "ID";
    public static final String COL_DATE = "DATE";
    public static final String COL_PLAN = "PLAN";
    public static final int DB_VERSION = 1;
    public Cursor db_cursor;

    public SchedulerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PLAN + " TEXT NOT NULL,"
                + COL_DATE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String updateQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;

        sqLiteDatabase.execSQL(updateQuery);
        onCreate(sqLiteDatabase);
    }

    public void addPlan(String plan, String date)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLAN, plan);
        contentValues.put(COL_DATE, date);

        sqLiteDatabase.insert(SchedulerDBHelper.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void deletePlan(String plan)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String deleteQuery = "DELETE * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + plan;
        sqLiteDatabase.delete(TABLE_NAME, COL_PLAN + " = ?", new String[] {plan});
        //sqLiteDatabase.execSQL(deleteQuery);
        sqLiteDatabase.close();
    }

    public ArrayList<String> getPlanList()
    {
        ArrayList<String> planList = new ArrayList<>();
        String selectQuery = "SELECT " + COL_PLAN + ", " + COL_DATE + " FROM " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        db_cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (db_cursor.moveToNext())
        {
            String tour_plan = db_cursor.getString(0);
            planList.add(tour_plan);
        }

        db_cursor.close();
        sqLiteDatabase.close();
        return planList;
    }
}
