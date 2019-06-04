package com.gwkj.qixiubaodian.obd.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OBDVarDataHelper extends SQLiteOpenHelper {

	private static final String name = "obdver";
	private static final int version = 1;
	private static OBDVarDataHelper instance=null;
	public static OBDVarDataHelper getInstance(Context context){
		if(instance==null){
			instance=new OBDVarDataHelper(context);
		}
		return instance;
	}
	public OBDVarDataHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("info", "create table");
		db.execSQL("CREATE TABLE IF NOT EXISTS varname (id integer primary key autoincrement, name varchar(40),value varchar(40))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
