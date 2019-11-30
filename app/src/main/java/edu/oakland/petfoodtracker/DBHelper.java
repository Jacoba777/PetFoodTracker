package edu.oakland.petfoodtracker;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/edu.oakland.petfoodtracker/databases/";
    private static String DB_NAME = "petfeed"; //yourDB file name
    private SQLiteDatabase db;
    private Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.
        }
        if (checkDB != null) {

            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void createDataBase() throws Exception {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (Exception e) {

                throw new Error("Error copying database");

            }
        }

    }


    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (db != null)
            db.close();

        super.close();

    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input file
        InputStream myInput = myContext.getAssets().open(DB_NAME + ".db");

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db file that was created by DBHelper as an output file
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams or the output file
        myOutput.flush();//write to output file
        myOutput.close();
        myInput.close();

    }

    public static SQLiteDatabase getDB(Context context)
    {
        DBHelper dbHelper = new DBHelper(context);
        try {
            dbHelper.createDataBase();}
        catch(Exception ex){
            ex.printStackTrace();}
        try{
            dbHelper.openDataBase();}
        catch(SQLException ex){
            ex.printStackTrace();}

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db;
    }

    public static Cursor queryDB(SQLiteDatabase db, String query)
    {
        ArrayList<String> res = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
