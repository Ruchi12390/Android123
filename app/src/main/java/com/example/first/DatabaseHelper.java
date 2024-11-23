package com.example.first;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "handm.db";
    private static final int DATABASE_VERSION = 8;

    // Products table
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";

    public static final String CART_ID = "cart_id";
    public static final String CART_BUYER_ID = "buyer_id";
    public static final String CART_PRODUCT_ID = "product_id";

    // Sellers table
    public static final String TABLE_SELLERS = "sellers1";
    public static final String SELLER_ID = "seller_id";
    public static final String SELLER_EMAIL = "email";
    public static final String SELLER_PASSWORD = "password";
    private static final String TABLE_WISHLIST = "wishlist";
    public static final String TABLE_CART = "cart";
    private static final String SELLER_TABLE_SELLERS = "sellers";

    // Column names
    private static final String SELLER_COLUMN_ID = "id";
    private static final String SELLER_COLUMN_NAME = "name";
    private static final String SELLER_COLUMN_EMAIL = "email";
    private static final String SELLER_COLUMN_PASSWORD = "password";
    private static final String SELLER_COLUMN_PHONE = "phone";
    private static final String SELLER_COLUMN_ADDRESS = "address";

    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_BUYER_ID = "buyer_id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    // Columns for wishlist and cart
    public static final String TABLE_BUYERS = "buyers";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_BUYER_ID = "buyer_id";
    public static final String ORDER_SELLER_ID = "seller_id";
    public static final String ORDER_PRODUCT_ID = "product_id";
    public static final String ORDER_PRODUCT_NAME = "product_name";
    public static final String BUYER_ID = "buyer_id";
    public static final String BUYER_NAME = "buyer_name";
    public static final String BUYER_EMAIL = "buyer_email";
    public static final String BUYER_PHONE = "buyer_phone";
    public static final String BUYER_ADDRESS = "buyer_address";
    public static final String BUYER_PASSWORD = "buyer_password";
    public static final String BUYER_UPI = "buyer_upi";
    public static final String BUYER_BANK_ACCOUNT = "buyer_bank_account";
    public static final String BUYER_IFSC = "buyer_ifsc";
    public static final String BUYER_GST = "buyer_gst";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create products table
        String CREATE_BUYER_TABLE = "CREATE TABLE " + TABLE_BUYERS + " (" +
                BUYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BUYER_NAME + " TEXT, " +
                BUYER_EMAIL + " TEXT, " +
                BUYER_PHONE + " TEXT, " +
                BUYER_ADDRESS + " TEXT, " +
                BUYER_PASSWORD + " TEXT, " +
                BUYER_UPI + " TEXT, " +
                BUYER_BANK_ACCOUNT + " TEXT, " +
                BUYER_IFSC + " TEXT, " +
                BUYER_GST + " TEXT" +
                ")";
        db.execSQL(CREATE_BUYER_TABLE);


        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                BUYER_ID + " INTEGER, " + // Assuming this is the seller's ID
                "FOREIGN KEY(" + BUYER_ID + ") REFERENCES " + TABLE_BUYERS + "(" + BUYER_ID + ")" + // Foreign key referencing buyers table
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        // Create sellers table
        String CREATE_SELLERS_TABLE = "CREATE TABLE " + TABLE_SELLERS + "("
                + SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SELLER_EMAIL + " TEXT,"
                + SELLER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_SELLERS_TABLE);
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ORDER_BUYER_ID + " TEXT,"
                + ORDER_SELLER_ID + " INTEGER,"
                + ORDER_PRODUCT_ID + " INTEGER,"
                + ORDER_PRODUCT_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_ORDERS_TABLE);


        String CREATE_WISHLIST_TABLE = "CREATE TABLE " + TABLE_WISHLIST + "("
                + COLUMN_BUYER_ID + " INTEGER,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID + "),"
                + "PRIMARY KEY(" + COLUMN_BUYER_ID + ", " + COLUMN_PRODUCT_ID + "))";
        db.execSQL(CREATE_WISHLIST_TABLE);

        // Create cart table
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_BUYER_ID + " INTEGER,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID + "),"
                + "PRIMARY KEY(" + COLUMN_BUYER_ID + ", " + COLUMN_PRODUCT_ID + "))";
        db.execSQL(CREATE_CART_TABLE);

        Log.d("DatabaseHelper", "Tables created successfully");
    }
    public void addBuyer(Buyer buyer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BUYER_NAME, buyer.getName());
        values.put(BUYER_EMAIL, buyer.getEmail());
        values.put(BUYER_PHONE, buyer.getPhone());
        values.put(BUYER_ADDRESS, buyer.getAddress());
        values.put(BUYER_PASSWORD, buyer.getPassword());
        values.put(BUYER_UPI, buyer.getUpi());
        values.put(BUYER_BANK_ACCOUNT, buyer.getBankAccount());
        values.put(BUYER_IFSC, buyer.getIfsc());
        values.put(BUYER_GST, buyer.getGst());

        // Inserting Row
        db.insert(TABLE_BUYERS, null, values);
        db.close(); // Closing database connection
    }
    public boolean loginBuyer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUYERS + " WHERE " + BUYER_EMAIL + "=? AND " + BUYER_PASSWORD + "=?", new String[]{email, password});

        boolean result = cursor.getCount() > 0; // If cursor has records, login is successful
        cursor.close();
        return result;
    }
    public String getBuyerIdFromProduct(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String buyerId = null;

        // Query to get the buyer ID associated with the product
        String query = "SELECT p." + BUYER_ID +
                " FROM " + TABLE_PRODUCTS + " p" +
                " WHERE p." + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                buyerId = cursor.getString(0); // Fetch the buyer ID
            } else {
                // Log no rows found
                System.out.println("No rows found for Product ID: " + productId);
            }
            cursor.close();
        } else {
            // Log query failure
            System.out.println("Query failed for Product ID: " + productId);
        }

        db.close();
        return buyerId;
    }


    public String getBuyerUPIFromProduct(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String buyerUPI = null;

        String query = "SELECT b." + BUYER_ID +
                " FROM " + TABLE_PRODUCTS + " p" +
                " INNER JOIN " + TABLE_BUYERS + " b" +
                " ON p." + BUYER_ID + " = b." + BUYER_ID +
                " WHERE p." + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                buyerUPI = cursor.getString(0);
            } else {
                // Log no rows found
                System.out.println("No rows found for Product ID: " + productId);
            }
            cursor.close();
        } else {
            // Log query failure
            System.out.println("Query failed for Product ID: " + productId);
        }

        db.close();
        return buyerUPI;
    }


    public int getBuyerId(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + BUYER_ID + " FROM " + TABLE_BUYERS + " WHERE " + BUYER_EMAIL + "=? AND " + BUYER_PASSWORD + "=?", new String[]{email, password});

        int buyerId = -1; // Default value if not found
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                buyerId = cursor.getInt(cursor.getColumnIndex(BUYER_ID));
            }
            cursor.close();
        }
        return buyerId;
    }


    public boolean isProductInCart(int buyerId, int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_BUYER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(buyerId), String.valueOf(productId)});

        boolean isInCart = cursor.getCount() > 0; // Check if any rows exist
        cursor.close();
        return isInCart;
    }
    public long removeProductFromCart1(int buyerId, String productName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Fetch the product ID using the product name
        int productId = getProductIdByName(productName);

        if (productId != -1) {
            return db.delete(TABLE_CART, COLUMN_BUYER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?",
                    new String[]{String.valueOf(buyerId), String.valueOf(productId)});
        }

        return 0; // Return 0 if the product was not found
    }



    public long removeProductFromCart(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CART, COLUMN_BUYER_ID + " = ? AND " + COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(buyerId), String.valueOf(productId)});
    }


    public ArrayList<Product> getWishlistItems(int buyerId) {
        ArrayList<Product> wishlistItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Adjust the query to select only the required columns
        String query = "SELECT " + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_PRICE + ", "
                + COLUMN_IMAGE_URI
                + " FROM " + TABLE_WISHLIST
                + " INNER JOIN " + TABLE_PRODUCTS
                + " ON " + TABLE_WISHLIST + "." + COLUMN_PRODUCT_ID + " = " + TABLE_PRODUCTS + "." + COLUMN_ID
                + " WHERE " + TABLE_WISHLIST + "." + COLUMN_BUYER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(buyerId)});

        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String productDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String productPrice = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI));

                // Create Product object using the four-parameter constructor
                Product product = new Product(productName, productDescription, productPrice, imageUri);
                wishlistItems.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return wishlistItems;
    }
    public ArrayList<Product1> getCartItems(int buyerId) {
        ArrayList<Product1> cartItems = new ArrayList<>(); // Change to ArrayList<Product1>
        SQLiteDatabase db = this.getReadableDatabase();

        // Adjust the query to select only the required columns from the cart
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_PRICE + ", "
                + COLUMN_IMAGE_URI
                + " FROM " + TABLE_CART
                + " INNER JOIN " + TABLE_PRODUCTS
                + " ON " + TABLE_CART + "." + COLUMN_PRODUCT_ID + " = " + TABLE_PRODUCTS + "." + COLUMN_ID
                + " WHERE " + TABLE_CART + "." + COLUMN_BUYER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(buyerId)});

        if (cursor.moveToFirst()) {
            do {
                // Get the productId from the correct column (COLUMN_ID)
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)); // Use COLUMN_ID for product ID
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String productDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String productPrice = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI));

                // Create Product1 object using the five-parameter constructor
                Product1 product = new Product1(productId, productName, productDescription, productPrice, imageUri);
                cartItems.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cartItems; // Return ArrayList<Product1>
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables if the database is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELLERS);
        onCreate(db);
        Log.d("DatabaseHelper", "Database upgraded, tables recreated");
    }
    public int getProductIdByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{COLUMN_ID},
                COLUMN_NAME + "=?", new String[]{productName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
            return productId;
        }
        cursor.close();
        return -1; // Return -1 if the product is not found
    }


    public long addProduct(String name, String description, String price, String category, String imageUri, int sellerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_IMAGE_URI, imageUri);
        values.put(BUYER_ID, sellerId); // Add seller ID to the values

        // Inserting Row
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
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

    public long addToWishlist(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BUYER_ID, buyerId);
        contentValues.put(COLUMN_PRODUCT_ID, productId);

        long result = db.insert(TABLE_WISHLIST, null, contentValues);
        db.close();
        return result;
    }

    public long addToCart(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BUYER_ID, buyerId);
        contentValues.put(COLUMN_PRODUCT_ID, productId);

        long result = db.insert(TABLE_CART, null, contentValues);
        db.close();
        return result;
    }

    public Cursor getWishlist(int buyerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_WISHLIST, null, COLUMN_BUYER_ID + "=?", new String[]{String.valueOf(buyerId)}, null, null, null);
    }

    public Cursor getCart(int buyerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CART, null, COLUMN_BUYER_ID + "=?", new String[]{String.valueOf(buyerId)}, null, null, null);
    }
    public boolean checkBuyerLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("buyers", new String[]{COLUMN_ID}, // Assuming there's a buyers table
                "email=? AND password=?", // Use your actual column names
                new String[]{email, password}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public long addProductToWishlist(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("buyer_id", buyerId);
        contentValues.put("product_id", productId);

        return db.insert("wishlist", null, contentValues); // Assuming wishlist table exists
    }
    public boolean isProductInWishlist(int sellerId, int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM wishlist WHERE buyer_id = ? AND product_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(sellerId), String.valueOf(productId)});

        boolean isInWishlist = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isInWishlist = count > 0; // If count > 0, the product is in the wishlist
        }
        cursor.close();
        return isInWishlist;
    }
    public long removeProductFromWishlist(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("wishlist", "buyer_id = ? AND product_id = ?", new String[]{String.valueOf(buyerId), String.valueOf(productId)});
    }

    public long addProductToCart(int buyerId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("buyer_id", buyerId);
        contentValues.put("product_id", productId);

        return db.insert("cart", null, contentValues); // Assuming cart table exists
    }

    // Method to get the buyer ID after successful login
    public int getSellerId(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            Log.d("DatabaseHelper", "Trying to fetch seller ID for email: " + email);
            cursor = db.query(TABLE_SELLERS, new String[]{SELLER_ID},
                    SELLER_EMAIL + "=? AND " + SELLER_PASSWORD + "=?",
                    new String[]{email, password}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int sellerId = cursor.getInt(cursor.getColumnIndex(SELLER_ID));
                Log.d("DatabaseHelper", "Seller ID found: " + sellerId);
                return sellerId;
            } else {
                Log.d("DatabaseHelper", "No matching seller found for provided email and password.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching seller ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return -1; // Return -1 if not found
    }




}
