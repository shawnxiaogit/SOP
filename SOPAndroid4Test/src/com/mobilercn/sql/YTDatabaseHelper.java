package com.mobilercn.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class YTDatabaseHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME    = "";
	public static final int    DATABASE_VERSION = 1;
	
	YTDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		/*String sql = "CREATE TABLE tb_test (_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,class_jb TEXT  NOT NULL,class_ysbj TEXT  NOT NULL,title TEXT  NOT NULL,content_ysbj TEXT  NOT NULL)";
        
		try{
	        db.execSQL(sql);//需要异常捕获			
		}
		catch(Exception e){}
		*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		/*
		String sql = "drop table "+tbname;
        db.execSQL(sql);
        onCreate(db);
        */
	}
	
	public long insert(String tname, int tage, String ttel){
		
        /*SQLiteDatabase db= getWritableDatabase();//获取可写SQLiteDatabase对象
        //ContentValues类似map，存入的是键值对
        ContentValues contentValues = new ContentValues();
        contentValues.put("tname", tname);
        contentValues.put("tage", tage);
        contentValues.put("ttel", ttel);
        return db.insert(tbname, null, contentValues);
        */
		
		return 0;
    }

	public void delete(String _id){
		/*
        SQLiteDatabase db= getWritableDatabase();
        db.delete(tbname, 
                "_id=?", 
                new String[]{_id});
        */
	}
	
	public void update(String _id,String tname, int tage, String ttel){
		
		/*
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tname", tname);
        contentValues.put("tage", tage);
        contentValues.put("ttel", ttel);
        db.update(tbname, contentValues, "_id=?", new String[]{_id});
        */
        
	}
	
	/*
	参数1：表名
	参数2：返回数据包含的列信息，String数组里放的都是列名
	参数3：相当于sql里的where，sql里where后写的内容放到这就行了，例如：tage>?
	参数4：如果你在参数3里写了?（知道我为什么写tage>?了吧），那个这里就是代替?的值 接上例：new String[]{"30"}
	参数5：分组，不解释了，不想分组就传null
	参数6：having，想不起来的看看SQL
	参数7：orderBy排序
	*/
	public Cursor select(){
		
		/*
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                tbname, 
                new String[]{"_id","tname","tage","ttel","taddr"}, 
                null, 
                null, 
                null, 
                null, 
                "_id desc"
                );
        */
		
		return null;
	}
}
