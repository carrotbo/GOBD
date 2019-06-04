package com.gwkj.qixiubaodian.obd.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 公共的缓存工具类
 */
public class BaseCacheUtil {

	// private final static boolean isCache = true;// 是否缓存的标志位

	private final static String SP_NAME = "basedata";
	private static SharedPreferences sp;

	private static SharedPreferences getSp(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp;
	}

	/** 获取boolean数据 */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, false);
	}

	/** 获取boolean数据 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		// if (!isCache) {
		// return false;
		// }
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, defValue);
	}

	/** 存boolean缓存 */
	public static void setBoolean(Context context, String key, boolean value) {
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/** 获取String数据,默认值为"" */
	public static String getString(Context context, String key) {
		// if (!isCache) {
		// return null;
		// }
		SharedPreferences sp = getSp(context);
		return sp.getString(key, "");
	}

	/** 存String缓存 */
	public static void setString(Context context, String key, String value) {
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/** 获取long数据,不带默认值 */
	public static long getLong(Context context, String key) {
		// if (!isCache) {
		// return null;
		// }
		SharedPreferences sp = getSp(context);
		return sp.getLong(key, 0);
	}

	/** 存long缓存 */
	public static void setLong(Context context, String key, long value) {
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	/** 获取long数据,不带默认值 */
	public static int getInt(Context context, String key) {
		// if (!isCache) {
		// return null;
		// }
		SharedPreferences sp = getSp(context);
		return sp.getInt(key, 0);
	}

	/** 存long缓存 */
	public static void setInt(Context context, String key, int value) {
		SharedPreferences sp = getSp(context);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
}
