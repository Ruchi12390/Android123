package com.example.first;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "database1.db";
    private static final int DATABASE_VERSION = 4;

    // Products table
    private static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";

    // Sellers table
    private static final String TABLE_SELLERS = "sellers1";
    public static final String SELLER_ID = "seller_id";
    public static final String SELLER_EMAIL = "email";
    public static final String SELLER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create products table
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE_URI + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        // Create sellers table
        String CREATE_SELLERS_TABLE = "CREATE TABLE " + TABLE_SELLERS + "("
                + SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SELLER_EMAIL + " TEXT,"
                + SELLER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_SELLERS_TABLE);

        Log.d("DatabaseHelper", "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables if the database is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELLERS);
        onCreate(db);
        Log.d("DatabaseHelper", "Database upgraded, tables recreated");
    }

    public long addProduct(String productName, String productDescription, String productPrice, String productCategory, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, productName);
        contentValues.put(COLUMN_DESCRIPTION, productDescription);
        contentValues.put(COLUMN_PRICE, productPrice);
        contentValues.put(COLUMN_CATEGORY, productCategory);
        contentValues.put(COLUMN_IMAGE_URI, imageUri);

        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        db.close();
        return result;
    }

    public Cursor getProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, COLUMN_CATEGORY + "=?", new String[]{category}, null, null, null);
    }
    public Cursor getDistinctCategoriesWithImages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT " + COLUMN_CATEGORY + ", " + COLUMN_IMAGE_URI + " FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, new String[]{COLUMN_CATEGORY}, null, null, null, null, null);
    }

    public Cursor getDistinctCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT " + COLUMN_CATEGORY + " FROM " + TABLE_PRODUCTS, null);
    }
    public long addSeller(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SELLER_EMAIL, email);
        contentValues.put(SELLER_PASSWORD, password);

        long result = db.insert(TABLE_SELLERS, null, contentValues);
        db.close();
        return result; // Return the row ID of the newly inserted row, or -1 if an error occurred.
    }

    public boolean checkSellerLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SELLERS, new String[]{SELLER_ID},
                SELLER_EMAIL + "=? AND " + SELLER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }
}
