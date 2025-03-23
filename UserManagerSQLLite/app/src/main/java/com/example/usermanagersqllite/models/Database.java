package com.example.usermanagersqllite.models;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "uSERmANAGER";
    private static final String TABLE_CONTACTS = "USERS";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "UserName";
    private static final String KEY_PH_NO = "Password";
    private static final String KEY_PHONE_NUMBER = "PhoneNumber";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_AGE = "Age";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_PH_NO + " TEXT, "
                + KEY_PHONE_NUMBER + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_AGE + " INTEGER" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) { // If upgrading from version 1 to 2
            db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + KEY_PHONE_NUMBER + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + KEY_EMAIL + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + KEY_ADDRESS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + KEY_AGE + " INTEGER");
        }
    }


    public void onDeleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, null, null);

        db.close();
    }

    public void addUser(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getUserName());
        values.put(KEY_PH_NO, contact.getPassword());
        values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_AGE, contact.getAge());

        db.insert(TABLE_CONTACTS, null, values);

        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{
                        KEY_ID, KEY_NAME, KEY_PH_NO, KEY_EMAIL, KEY_ADDRESS, KEY_PHONE_NUMBER, KEY_AGE
                },
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst())
            cursor.moveToFirst();

        cursor.close();
        db.close();

        return new User(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getInt(6));
    }

    public List<User> getAllUsers() {
        List<User> UserList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                );

                UserList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return UserList;
    }

    public int updateUser(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getUserName());
        values.put(KEY_PH_NO, contact.getPassword());
        values.put(KEY_AGE, contact.getAge());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(KEY_ADDRESS, contact.getAddress());

        int rowsUpdated = db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});

        db.close();

        return rowsUpdated;
    }

    public void deleteUser(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});

        db.close();
    }

    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{KEY_ID, KEY_NAME, KEY_PH_NO},
                KEY_NAME + " = ? AND " + KEY_PH_NO + " = ?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));

            cursor.close();
            db.close();

            return user;
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return null;
    }

    public boolean deleteUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        int deletedRows = db.delete(TABLE_CONTACTS, KEY_NAME + " = ?", new String[]{username});

        db.close();

        return deletedRows > 0;
    }


    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{
                        KEY_ID, KEY_NAME, KEY_PH_NO, KEY_PHONE_NUMBER, KEY_EMAIL, KEY_ADDRESS, KEY_AGE
                },
                KEY_NAME + " = ?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Integer.parseInt(cursor.getString(6))
            );
            cursor.close();
            db.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return null;
    }

}
