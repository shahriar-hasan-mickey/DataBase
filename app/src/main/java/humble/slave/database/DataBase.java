package humble.slave.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Userdetails(input1 TEXT PRIMARY KEY, input2 TEXT, input3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Userdetails");
    }


    public Boolean insertUserData(String input1, String input2, String input3) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("input1", input1);
        contentValues.put("input2", input2);
        contentValues.put("input3", input3);
        long result = DB.insert("Userdetails", null, contentValues);
        return result != -1;
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
    }

    public Boolean updateUserData(String input1, String input2, String input3) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("input2", input2);
        contentValues.put("input3", input3);
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE input1 = ?", new String[]{input1});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "input1=?", new String[]{input1});
            return result != -1;
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
        } else {
            return false;
        }
    }

    public Boolean deleteUserData(String input1) {
        SQLiteDatabase DB = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE input1 = ?", new String[]{input1});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "input1=?", new String[]{input1});
            return result != -1;
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
        } else {
            return false;
        }
    }

    public Cursor getUserData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Userdetails", null);
    }

    public Cursor getSwitchState(String input1){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Userdetails WHERE input1=?", new String[]{input1});
    }

}
