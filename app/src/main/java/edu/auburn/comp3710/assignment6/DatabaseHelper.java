package edu.auburn.comp3710.assignment6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SpendingManagement.db";
    public static final String TABLE_NAME = "Money";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 4);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, Date int, Amount DOUBLE, Event varchar(255))");
    }


    public void Create(int date, double amount, String event){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues content = new ContentValues();
        content.put("Date", date);
        content.put("Amount", amount);
        content.put("Event", event);
        database.insert(TABLE_NAME,null, content);
    }

    public Cursor SearchData(String Sdate,String Edate,String LowBoundPrice,String HighBoundPrice) {




        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = null;

        if (LowBoundPrice.equals("n") && HighBoundPrice.equals("n")){
            int getSdate = Integer.parseInt(Sdate);
            int getEdate = Integer.parseInt(Edate);
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= " +getSdate + " AND Date <= " + getEdate,null);
        }

        else if (Sdate.equals("n") && Edate.equals("n")){
            double getLowBound = Double.parseDouble(LowBoundPrice);
            double getHighBound = Double.parseDouble(HighBoundPrice);
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME+ " WHERE Amount >= " + getLowBound + " AND Amount <= " + getHighBound,null);
        }

        else{
            int getSdate = Integer.parseInt(Sdate);
            int getEdate = Integer.parseInt(Edate);
            double getLowBound = Double.parseDouble(LowBoundPrice);
            double getHighBound = Double.parseDouble(HighBoundPrice);
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Amount >= " + getLowBound + " AND Amount <= " + getHighBound + " AND Date >=" + getSdate + " AND Date <=" + getEdate,null);
        }


        return result;
    }


    public Cursor ReadData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor result = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}