package yosumo.src.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import yosumo.src.R;

/**
 * Clase manejadora de las operaciones de la BD
 * Created by a-pol_000 on 9/7/2016.
 */
public class ManagerDB extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;
        // Contacts table name
        private static final String TABLE_SHOPS = "";
        // Shops Table Columns names
        private static final String KEY_ID = "";
        private static final String KEY_NAME = "";
        private static final String KEY_SH_ADDR = "";

        public ManagerDB(Context context, String dataBaseName ) {
            super(context, dataBaseName , null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHOPS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_SH_ADDR + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
// Creating tables again
            onCreate(db);
        }
}
