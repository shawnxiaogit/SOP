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
	        db.execSQL(sql);//��Ҫ�쳣����			
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
		
        /*SQLiteDatabase db= getWritableDatabase();//��ȡ��дSQLiteDatabase����
        //ContentValues����map��������Ǽ�ֵ��
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
	����1������
	����2���������ݰ���������Ϣ��String������ŵĶ�������
	����3���൱��sql���where��sql��where��д�����ݷŵ�������ˣ����磺tage>?
	����4��������ڲ���3��д��?��֪����Ϊʲôдtage>?�˰ɣ����Ǹ�������Ǵ���?��ֵ ��������new String[]{"30"}
	����5�����飬�������ˣ��������ʹ�null
	����6��having���벻�����Ŀ���SQL
	����7��orderBy����
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
