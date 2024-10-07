package com.example.first;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_PRODUCTS = "products";

    // Column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating database and products table...");
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE_URI + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        Log.d("DatabaseHelper", "Products table created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
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
        if (result == -1) {
            Log.e("DatabaseHelper", "Product insert failed.");
        } else {
            Log.d("DatabaseHelper", "Product inserted successfully. ID: " + result);
        }

        db.close();
        return result;
    }

    public Cursor getProductById(long productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, COLUMN_ID + "=?", new String[]{String.valueOf(productId)}, null, null, null);
    }

    public Cursor getProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, COLUMN_CATEGORY + "=?", new String[]{category}, null, null, null);
    }
}
