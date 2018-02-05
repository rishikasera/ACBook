package com.example.homepc.acbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home pc on 15/01/2018.
 *//*
int	str	    int	    int	string	int
id	date	money	tt	pur	    clear
*/
public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AcManager";
    private static final String TABLE_CONTACTS = "cashManager";
    private static final String TABLE_SBI = "sbiTable";
    private static final String TABLE_HDFC = "hdfcTable";
    private static final String TABLE_HDFC_CC = "hdfcCCTable";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_MONEY = "money";
    private static final String KEY_TT = "tt";
    private static final String KEY_PUR = "pur";
    private static final String KEY_CLEAR = "clear";

    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DATE + " TEXT,"
            + KEY_MONEY + " INTEGER,"
            + KEY_TT + " INTEGER,"
            + KEY_PUR + " TEXT,"
            + KEY_CLEAR + " INTEGER"
            + ")";

    String CREATE_SBI_TABLE = "CREATE TABLE " + TABLE_SBI + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DATE + " TEXT,"
            + KEY_MONEY + " INTEGER,"
            + KEY_TT + " INTEGER,"
            + KEY_PUR + " TEXT,"
            + KEY_CLEAR + " INTEGER"
            + ")";

    String CREATE_HDFC_TABLE = "CREATE TABLE " + TABLE_HDFC + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DATE + " TEXT,"
            + KEY_MONEY + " INTEGER,"
            + KEY_TT + " INTEGER,"
            + KEY_PUR + " TEXT,"
            + KEY_CLEAR + " INTEGER"
            + ")";

    String CREATE_HDFC_CC_TABLE = "CREATE TABLE " + TABLE_HDFC_CC + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DATE + " TEXT,"
            + KEY_MONEY + " INTEGER,"
            + KEY_TT + " INTEGER,"
            + KEY_PUR + " TEXT,"
            + KEY_CLEAR + " INTEGER"
            + ")";

/*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                                 Constructer
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }




 /*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // Creating Tables
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_SBI_TABLE);
        db.execSQL(CREATE_HDFC_TABLE);
        db.execSQL(CREATE_HDFC_CC_TABLE);


        addContact1(db);
       // db.close();
    }



/*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // Upgrading database
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SBI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HDFC_CC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HDFC);

        // Create tables again
        onCreate(db);//db.close();;
    }




  /*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // addContact in database
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/


    void addContact2(String table,int money) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, "null"); // Contact Phone
        values.put(KEY_MONEY, 0); // Contact Phone
        values.put(KEY_TT, 1); // Contact Phone
        values.put(KEY_PUR, "null"); // Contact Phone
        values.put(KEY_CLEAR, money);

        if(table.equals(TABLE_CONTACTS)){
            db.insert(TABLE_CONTACTS, null, values);
        }else if(table.equals(TABLE_SBI)){
            db.insert(TABLE_SBI, null, values);
        }else if(table.equals(TABLE_HDFC)){
            db.insert(TABLE_HDFC, null, values);
        }else if(table.equals(TABLE_HDFC_CC)){
            db.insert(TABLE_HDFC_CC, null, values);
        }

        // Inserting Row
        //db.insert(table, null, values);
        //2nd argument is String containing nullColumnHack

    }


    void addContact1(SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, "NULL"); // Contact Phone
        values.put(KEY_MONEY, 0); // Contact Phone
        values.put(KEY_TT, 0); // Contact Phone
        values.put(KEY_PUR, "NULL"); // Contact Phone
        values.put(KEY_CLEAR, 0);

        db.insert(TABLE_CONTACTS, null, values);
        db.insert(TABLE_SBI, null, values);
        db.insert(TABLE_HDFC, null, values);
        db.insert(TABLE_HDFC_CC, null, values);
    }


    // code to add the new contact
    void addContact(Record detail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, detail.get_date()); // Contact Phone
        values.put(KEY_MONEY, detail.get_money()); // Contact Phone
        values.put(KEY_TT, detail.get_tt()); // Contact Phone
        values.put(KEY_PUR, detail.get_pur()); // Contact Phone
        values.put(KEY_CLEAR, detail.get_clear());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    void addContactInTable(Record detail, String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, detail.get_date()); // Contact Phone
        values.put(KEY_MONEY, detail.get_money()); // Contact Phone
        values.put(KEY_TT, detail.get_tt()); // Contact Phone
        values.put(KEY_PUR, detail.get_pur()); // Contact Phone
        values.put(KEY_CLEAR, detail.get_clear());

        if(table.equals(TABLE_CONTACTS)){
            db.insert(TABLE_CONTACTS, null, values);
        }else if(table.equals(TABLE_SBI)){
            db.insert(TABLE_SBI, null, values);
        }else if(table.equals(TABLE_HDFC)){
            db.insert(TABLE_HDFC, null, values);
        }else if(table.equals(TABLE_HDFC_CC)){
            db.insert(TABLE_HDFC_CC, null, values);
        }

        // Inserting Row
        //db.insert(table, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }



   /*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // contacts Count
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/




    // Getting contacts Count
    public int getDetailsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        Log.d("Reading: ", "Step 2.1");
        SQLiteDatabase db1 = this.getReadableDatabase();
        Log.d("Reading: ", "Step 2.2");
        Cursor cursor = db1.rawQuery(countQuery, null);
        //cursor.close();
        //db1.close();
        // return count
        return cursor.getCount();
    }


    // Getting contacts Count
    public int getDetailsCountFromTable(String table) {


        String countQuery = "";
        if(table.equals(TABLE_CONTACTS)){
            countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        }else if(table.equals(TABLE_SBI)){
            countQuery = "SELECT * FROM " + TABLE_SBI;
        }else if(table.equals(TABLE_HDFC)){
            countQuery = "SELECT * FROM " + TABLE_HDFC;
        }else if(table.equals(TABLE_HDFC_CC)){
            countQuery = "SELECT * FROM " + TABLE_HDFC_CC;
        }

        Log.d("Reading: ", "Step 2.1");
        SQLiteDatabase db1 = this.getReadableDatabase();
        Log.d("Reading: ", "Step 2.2");
        Cursor cursor = db1.rawQuery(countQuery, null);
        //cursor.close();
        //db1.close();
        // return count
        return cursor.getCount();
    }

 /*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // code to get the single contact
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/



    Record getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_DATE, KEY_MONEY,KEY_TT,KEY_PUR,KEY_CLEAR }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Record detail = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)));
        // return contact
        return detail;
    }

    Record getContactFromTable(int id, final String table) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table, new String[] { KEY_ID,
                        KEY_DATE, KEY_MONEY,KEY_TT,KEY_PUR,KEY_CLEAR }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Record detail = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)));
        // return contact
        return detail;
    }



/*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // code to get all contacts in a list view
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/




    // code to get all contacts in a list view
    public List<Record> getAllContacts() {
        List<Record> contactList = new ArrayList<Record>();
        // Select All Query
        String selectQuery = "SELECT * FROM" + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Record contact = new Record();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_date(cursor.getString(1));
                contact.set_money(Integer.parseInt(cursor.getString(2)));
                contact.set_tt(Integer.parseInt(cursor.getString(3)));
                contact.set_pur(cursor.getString(4));
                contact.set_clear(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // code to get all contacts in a list view
    public List<Record> getAllContactsFromTable(String table) {
        List<Record> contactList = new ArrayList<Record>();
        // Select All Query
        Log.e("->->->->->->->->-> ", "Table match : "+table);

        String selectQuery = "";
        if(table.equals(TABLE_CONTACTS.toString())){
            selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
            Log.d("->->->->->->->->-> ", "SELECT * FROM  + TABLE_CONTACTS");
        }else if(table.equals(TABLE_SBI.toString())){
            selectQuery = "SELECT * FROM " + TABLE_SBI;
            Log.d("->->->->->->->->-> ", "SELECT * FROM  + TABLE_SBI");
        }else if(table.equals(TABLE_HDFC.toString())){
            selectQuery = "SELECT * FROM " + TABLE_HDFC;
            Log.d("->->->->->->->->-> ", "SELECT * FROM  + TABLE_HDFC");
        }else if(table.equals(TABLE_HDFC_CC.toString())){
            selectQuery = "SELECT * FROM " + TABLE_HDFC_CC;
            Log.d("->->->->->->->->-> ", "SELECT * FROM  + TABLE_HDFC");
        }else{
            Log.e("->->->->->->->->-> ", "No Table match");
        }

        //selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("QUERY SELECT * ","**********************************************************");
        Log.d("QUERY SELECT * ", "About to Start");
        Log.d("QUERY SELECT * ","**********************************************************");

        Cursor cursor = db.rawQuery(selectQuery, null);


        Log.d("QUERY SELECT * ","**********************************************************");
        Log.d("QUERY SELECT * ", "Finish");
        Log.d("QUERY SELECT * ","**********************************************************");

        int addConCount = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                addConCount++;
                Record contact = new Record();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_date(cursor.getString(1));
                contact.set_money(Integer.parseInt(cursor.getString(2)));
                contact.set_tt(Integer.parseInt(cursor.getString(3)));
                contact.set_pur(cursor.getString(4));
                contact.set_clear(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                contactList.add(contact);
                Log.d("Adding Conntect  ","**********************************************************");
                Log.d("Adding Conntect : ", addConCount+"");
                Log.d("Adding Conntect ","**********************************************************");
            } while (cursor.moveToNext());
        }else{
            Log.e("No record found  ","**********************************************************");
            Log.e("No record found : ", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            Log.e("No record found ","**********************************************************");
        }



        // return contact list
        return contactList;
    }



/*###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################3
*
*                                          // code to delete contact
*
* ###############################################################################################################3
* ###############################################################################################################3
* ###############################################################################################################*/





    public boolean deleteAllRecord(){
        String qury = "DELETE FROM "+TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,null,null);
        db.rawQuery(qury, null);
        addContact(new Record("null",0,0,"first",0));
        db.close();;
        return true;
    }


    public boolean deleteAllRecordFromTable(final String table){
        String qury = "DELETE FROM "+ table ;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,null,null);
        db.rawQuery(qury, null);
        addContactInTable(new Record("null",0,0,"first",0),table);
        db.close();;
        return true;
    }


    public boolean deleteAllRecordAllTable(){
        String qury1 = "DELETE FROM "+TABLE_CONTACTS;
        String qury2 = "DELETE FROM "+TABLE_SBI;
        String qury3 = "DELETE FROM "+TABLE_HDFC;
        String qury4 = "DELETE FROM "+TABLE_HDFC_CC;

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS,null,null);
        db.delete(TABLE_SBI,null,null);
        db.delete(TABLE_HDFC,null,null);
        db.delete(TABLE_HDFC_CC,null,null);

        db.rawQuery(qury1, null);
        db.rawQuery(qury2, null);
        db.rawQuery(qury3, null);
        db.rawQuery(qury4, null);

        addContactInTable(new Record("null",0,0,"first",0),TABLE_CONTACTS);
        addContactInTable(new Record("null",0,0,"first",0),TABLE_SBI);
        addContactInTable(new Record("null",0,0,"first",0),TABLE_HDFC);
        addContactInTable(new Record("null",0,0,"first",0),TABLE_HDFC_CC);

        db.close();;
        return true;
    }

}

