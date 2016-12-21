package com.hyc.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
    public List<StuInfo> getFirstData(){
    	List<StuInfo>  listSif = new ArrayList<StuInfo>();
    	String columns[] = new String[]{"_id","stu_cardno","classname","classcode","stu_name"};
    	Cursor c = db.query("broadcast", columns, null, null, null, null, null);
    	while (c.moveToNext()) {
    		StuInfo	sif = new StuInfo();
    		sif.setId(c.getInt(c.getColumnIndex("_id")));
			sif.setCardNum(c.getString(c.getColumnIndex("stu_cardno")));
			sif.setClassName(c.getString(c.getColumnIndex("classname")));
			sif.setClassCoded(c.getString(c.getColumnIndex("classcode")));
			sif.setName(c.getString(c.getColumnIndex("stu_name")));
			if(listSif.size()>0){
				int eqnum = 0;
				for(int i=0;i<listSif.size();i++){
					if(sif.getClassCoded().equals(listSif.get(i).getClassCoded())){
						eqnum++;
					}
				}	
				if(eqnum==0){
					listSif.add(sif);
				}
			}else{
				listSif.add(sif);
			}
		}
		c.close();
		return listSif;
    }
    
    public void deleteRecord(int id){
    	db.delete("broadcast", "_id=?", new String[]{String.valueOf(id)});
    }
    
    public void deleteAll(){
    	db.delete("broadcast", null, null);
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
