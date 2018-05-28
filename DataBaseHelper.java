package com.android.app.sqlitetest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.provider.BaseColumns;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "currency.db";
    private static final String TABLE_NAME = "rate";
    private static final int VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + Table._ID + " integer primary key autoincrement, " +
                Table.NATION_FROM + " text NOT NULL, " + Table.NATION_TO + " text NOT NULL, " + Table.RATE + " text NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(String nation1, String nation2, String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Table.NATION_FROM, nation1);
        cv.put(Table.NATION_TO, nation2);
        cv.put(Table.RATE, rate);
        long row = db.insert(TABLE_NAME, null, cv);
        //String sql="insert into 表名（列名，……） values （值，……）";
        //db.execSQL(sql);
        return row;
    }

    public void delete(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = column + " = ?";
        String[] whereArgs = {value};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        //db.execSQL("delete from 表名 where 条件");
    }

    public void update(int id, String nation1, String nation2, String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = Table._ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};

        ContentValues cv = new ContentValues();
        cv.put(Table.NATION_FROM, nation1);
        cv.put(Table.NATION_TO, nation2);
        cv.put(Table.RATE, rate);
        db.update(TABLE_NAME, cv, whereClause, whereArgs);
        //String sql="update 表名 set 列名=xxx where xxx;
        //db.execSQL(sql);
    }

    /*public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
    table：表名称
    colums：列名称数组
    selection：条件子句，相当于where
    selectionArgs：条件语句的参数数组
    groupBy：分组
    having：分组条件
    orderBy：排序类
    limit：分页查询的限制
    Cursor：返回值，相当于结果集ResultSet*/

    /*游标（Cursor）方法
    方法名称	    方法描述
    getCount()	总记录条数
    isFirst()	判断是否第一条记录
    isLast()	判断是否最后一条记录
    moveToFirst()	移动到第一条记录
    moveToLast()	移动到最后一条记录
    move(int offset)	移动到指定的记录
    moveToNext()	移动到吓一条记录
    moveToPrevious()	移动到上一条记录
    getColumnIndex(String columnName)	获得指定列索引的int类型值*/

    public Cursor query() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
        //return db.rawQuery("select * from " + TABLE_NAME + " where " + Table._ID + " = ?",new Int[]{10});
    }

    public static class Table implements BaseColumns {
        public static final String NATION_FROM = "nation_from";
        public static final String NATION_TO = "nation_to";
        public static final String RATE = "rate";
    }
}
