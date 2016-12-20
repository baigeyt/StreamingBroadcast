package com.hyc.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.hyc.bean.BroadcastCode;

public class DBManagerCode {
	private SQLiteDatabase db;

	public void creatDB() {
		File dbf = new File(getDir() + "/baige/db");
		if (!dbf.exists()) {
			dbf.mkdirs();
		}
		db = SQLiteDatabase.openOrCreateDatabase(dbf.toString() + "/"
				+ "code.db", null);
		db.execSQL("create table if not exists code (_id INTEGER PRIMARY KEY,classname VARCHAR not null,classcode VARCHAR not null)");
		System.out.println("------db.creatDB-----------");
	}

	public void openDB() {
		File dbf = new File(Environment.getExternalStorageDirectory()
				+ "/baige/db");
		if (!dbf.exists()) {
			dbf.mkdirs();
		}
		db = SQLiteDatabase.openOrCreateDatabase(dbf.toString() + "/"
				+ "code.db", null);
		System.out.println("---DBManagerCode---db.openDB-----------");
	}

	public void insert(String classname, String classcode) {
		if (classname != null && classcode != null) {
			ContentValues values = new ContentValues();
			values.put("classname", classname.trim());
			values.put("classcode", classcode.trim());
			db.insert("code", "_id", values);

			System.out.println("---DBManagerCode---db.insert-----------");

		}
	}

	public String queryOne(String _classname) {
		String classcode = null;

		String[] columns = { "classcode" };
		String[] name = { _classname };
		System.out.println("查询播码编号11"+_classname); 

		Cursor c = db.query("code", columns, "classname=?", name, null, null,
				null);

		while (c.moveToNext()) {
			System.out.println("查询播码编号");
			classcode = c.getString(c.getColumnIndex("classcode"));

			return classcode;
		}

		return null;
	}

	public List<BroadcastCode> queryAll() {
		List<BroadcastCode> list = new ArrayList<BroadcastCode>();

		String[] columns = { "classname", "classcode" };
		BroadcastCode mBroadcastCode = null;

		Cursor c = db.query("code", columns, null, null, null, null, null);

		while (c.moveToNext()) {
			mBroadcastCode = new BroadcastCode();

			mBroadcastCode.setClasscode(c.getString(c
					.getColumnIndex("classcode")));
			mBroadcastCode.setClassname(c.getString(c
					.getColumnIndex("classname")));

			list.add(mBroadcastCode);

		}
		c.close();
		System.out.println("------DBManagerCode.query-----------");

		return list;
	}

	public void deleteDB() {
		db.delete("code", null, null);
		System.out.println("------DBManagerCode.deleteDB-----------");

	}

	public void closeDB() {
		db.close();
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
