package com.hyc.db;

import java.io.File;

import com.hyc.bean.StuInfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManagerBroadcast {
	private SQLiteDatabase db;

	public void creatDB() {
		File dbf = new File(getDir() + "/baige/db");
		if (!dbf.exists()) {
			dbf.mkdirs();
		}
		db = SQLiteDatabase.openOrCreateDatabase(dbf.toString() + "/"
				+ "broadcast.db", null);
		db.execSQL("create table if not exists broadcast (_id INTEGER PRIMARY KEY ,stu_cardno VARCHAR not null,stu_name VARCHAR not null,classname VARCHAR not null,classcode VARCHAR not null)");
		System.out.println("-----------DBManagerBroadcast.creatDB-----------");
	}

	public void openDB() {
		File dbf = new File(Environment.getExternalStorageDirectory()
				+ "/baige/db");
		if (!dbf.exists()) {
			dbf.mkdirs();
		}
		db = SQLiteDatabase.openOrCreateDatabase(dbf.toString() + "/"
				+ "broadcast.db", null);
		System.out.println("---DBManagerBroadcast---db.openDB-----------");
	}

	public void insert(String classname, String classcode, String stucardno,
			String stuname) {
		if (classname != null && classcode != null) {
			ContentValues values = new ContentValues();
			values.put("classname", classname);
			values.put("classcode", classcode);
			values.put("stu_name", stuname);
			values.put("stu_cardno", stucardno);

			db.insert("broadcast", "_id", values);

			System.out.println("---DBManagerCode---db.insert-----------");

		}
	}
	
    public StuInfo getFirstData(){
    	StuInfo sif = new StuInfo();
//    	String columns[] = new String[]{"stu_cardno","classname","classcode","stu_name"};
    	Cursor c = db.rawQuery(
				"select * from broadcast limit 0,1", null);
    	while (c.moveToNext()) {
    		sif.setId(c.getInt(c.getColumnIndex("_id")));
			sif.setCardNum(c.getString(c.getColumnIndex("stu_cardno")));
			sif.setClassName(c.getString(c.getColumnIndex("classname")));
			sif.setClassCoded(c.getString(c.getColumnIndex("classcode")));
			sif.setName(c.getString(c.getColumnIndex("stu_name")));
		}
		c.close();
		return sif;
    }
    
    public String getSecondClassCode(){
    	String secondClass = null;
    	Cursor c = db.rawQuery(
				"select classcode from broadcast limit 1,1", null);
    	while (c.moveToNext()) {
    		secondClass = c.getString(c.getColumnIndex("classcode"));
		}
		c.close();
		return secondClass;
    }
    
    public String getThirdClassCode(){
    	String thirdClass = null;
    	Cursor c = db.rawQuery(
				"select classcode from broadcast limit 2,1", null);
    	while (c.moveToNext()) {
    		thirdClass = c.getString(c.getColumnIndex("classcode"));
		}
		c.close();
		return thirdClass;
    }
    
    public void deleteRecord(int id){
    	db.delete("broadcast", "_id=?", new String[]{String.valueOf(id)});
    }

	private File getDir() {
		// 得到SD卡根目录
		File dir = Environment.getExternalStorageDirectory();
		if (dir.exists()) {
			return dir;
		} else {
			dir.mkdirs();
			return dir;
		}
	}
}
