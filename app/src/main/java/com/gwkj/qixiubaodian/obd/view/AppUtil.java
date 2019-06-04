package com.gwkj.qixiubaodian.obd.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {
	/**
	 * 调用安装apk界面
	 * 
	 * @param context
	 * @param paramFile
	 */
	public static void install(Context context, File paramFile) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
		context.startActivity(localIntent);
	}

	public static void install(Context context, String paramFile) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.setDataAndType(Uri.fromFile(new File(paramFile)), "application/vnd.android.package-archive");
		context.startActivity(localIntent);
	}

	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static String getImagePath(Activity ctx, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor actualimagecursor = ctx.managedQuery(uri, proj, null, null, null);
		String img_path;
		if (actualimagecursor != null) {
			int actual_image_column_index1 = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			img_path = actualimagecursor.getString(actual_image_column_index1);
		} else {
			img_path = uri.getPath();
		}
		return img_path;
	}

	/**
	 * 根据Id查找控件
	 * 
	 * @param view
	 * @param resId
	 * @return
	 */
	public static <T extends View> T findViewById(View view, int resId) {
		return (T) view.findViewById(resId);
	}

	/**
	 * 根据Id查找控件
	 * 
	 * @param
	 * @param resId
	 * @return
	 */
	public static <T extends View> T findViewById(Activity ctx, int resId) {
		return (T) ctx.findViewById(resId);
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static void hideSoftInput(Context ctx, View view) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 判断字符串是否为null或者""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static String checkDate(String dateStr) {
		SimpleDateFormat sfToday = new SimpleDateFormat("HH:mm");
		Date todayDate = new Date();
		sfToday.format(todayDate);
		if (dateStr == null || dateStr.equals("")) {
			return sfToday.format(todayDate);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			Calendar today = Calendar.getInstance();
			d = sf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			long intervalMilli = today.getTimeInMillis() - cal.getTimeInMillis();
			int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
			if (xcts == 0) {
				return sfToday.format(d);
			} else if (xcts == 1) {
				return "昨天";
				// }else if (xcts == 2) {
				// return "前天";
			} else {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sfToday.format(todayDate);
	}

	/** 输入日期 , 返回相对的时间 */
//	public static String checkDate1(String dateStr, String index) {
//		SimpleDateFormat sfToday = new SimpleDateFormat("HH:mm");
//		// Date todayDate=new Date();
//		// sfToday.format(todayDate);
//		if (dateStr == null || dateStr.equals("")) {
//			String ss = "";
//			if (index.equals("1")) {
//				ss = SharedPrefManager.getInstance().getWendaDate();
//			} else if (index.equals("2")) {
//				ss = SharedPrefManager.getInstance().getShengHuoDate();
//			} else if (index.equals("3")) {
//				ss = SharedPrefManager.getInstance().getQiuzhiDate();
//			} else if (index.equals("4")) {
//				ss = SharedPrefManager.getInstance().getZhaoPinDate();
//			}
//
//			if (ss == null || ss.equals("")) {
//				return "";
//			} else {
//				return formatDateInSpecial(ss, sfToday);
//			}
//		} else {
//			return formatDateInSpecial(dateStr, sfToday);
//		}
//
//	}

	private static String formatDateInSpecial(String dateStr, SimpleDateFormat sfToday) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date d;
		// try {
		// Calendar today = Calendar.getInstance();
		// d = sf.parse(dateStr);
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(d);
		// cal.set(Calendar.HOUR, 0);
		// cal.set(Calendar.MINUTE, 0);
		// cal.set(Calendar.SECOND, 0);
		// long intervalMilli = today.getTimeInMillis()
		// - cal.getTimeInMillis();
		// int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
		// if (xcts == 0) {
		// return sfToday.format(d);
		// } else if (xcts == 1) {
		// return "昨天";
		// } else if (xcts == 2) {
		// return "两天前";
		// } else {
		// SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		// return sf1.format(cal.getTime());
		// }
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// return "两天前";
		return formatDateInSpecial1(dateStr);
	}

	public static int isBefore(String time1, String time2) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = sf.parse(time1);
			Date date2 = sf.parse(time2);
			if (date1.getTime() > date2.getTime()) {
				return -1;
			} else if (date1.getTime() == date2.getTime()) {
				return 0;
			} else {
				return 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatDateToSpec(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sf.parse(dateStr);
			date = sf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (isToday(cal)) {

				return "";
			} else {
				if (isYestoday(cal)) {
					return "昨天";
				} else {
					SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
					return sf1.format(cal.getTime());
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static boolean isYestoday(Calendar cal) {
		Calendar yestirday = Calendar.getInstance();
		yestirday.add(Calendar.DATE, -1);
		yestirday.set(Calendar.HOUR, 0);
		yestirday.set(Calendar.MINUTE, 0);
		yestirday.set(Calendar.SECOND, 0);
		return (!cal.before(yestirday) && !cal.after(yestirday));
	}

	public static boolean isToday(Calendar cal) {

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return (!cal.before(today) && !cal.after(today));
	}

	public static String formatDateInSpecial1(String dateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			Calendar today = Calendar.getInstance();
			d = sf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			// cal.set(Calendar.HOUR, 0);
			// cal.set(Calendar.MINUTE, 0);
			// cal.set(Calendar.SECOND, 0);
			long intervalMilli = today.getTimeInMillis() - cal.getTimeInMillis();
			int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));

			if (xcts == 0) {
				// return sfToday.format(d);
				int xctmini = (int) (intervalMilli / (60 * 60 * 1000));
				if (xctmini == 0) {
					int xctsecond = (int) (intervalMilli / (60 * 1000));
					if (xctsecond == 0) {
						return "刚刚";
					} else {
						return xctsecond + "分钟前";
					}
				} else {
					return xctmini + "小时前";
				}
			} else if (xcts == 1) {
				return "昨天";
				// } else if (xcts == 2) {
				// return "两天前";
			} else {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String formatDateInSpecialIsToday(String dateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		try {
			Calendar today = Calendar.getInstance();
			d = sf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			// cal.set(Calendar.HOUR, 0);
			// cal.set(Calendar.MINUTE, 0);
			// cal.set(Calendar.SECOND, 0);
			long intervalMilli = today.getTimeInMillis() - cal.getTimeInMillis();
			int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
			if (isToday(cal)) {
				return null;
			} else {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String chatShowTimes(String oldtime, String nowtime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (oldtime == null || oldtime.equals("")) {// 判断是不是第一次时间
			return formatDateInSpecialIsToday(nowtime);// 判断是不是今天
		}

		String oldtimessp = oldtime.split(" ")[0];
		String nowtimesp = nowtime.split(" ")[0];
		if (oldtimessp.equals(nowtimesp)) {// 判断是不是同一天：是

			return null;//
		} else {

			return formatDateInSpecialIsToday(nowtime);// 判断是不是今天

		}

		// String oldtimestr = formatDateInSpecialIsToday(oldtime);//判断是不是今天
		// String nowtimestr = formatDateInSpecialIsToday(nowtime);
		// String oldtimev =oldtime;//默认值；
		// String nowtimev =nowtime;
		// Date d;
		//
		// if(oldtimestr!=null&&nowtimestr!=null){//都不是今天
		// if(oldtimestr.equals(nowtimestr)){
		// return null;//返回null，表示隐藏
		// }else{
		// return nowtimestr;
		// }
		// } else if(oldtimestr==null&&nowtimestr==null){//都是今天的
		// return checkShowTimes(oldtimev, nowtimev);
		// }else if(nowtimestr!=null){
		// Calendar cal = Calendar.getInstance();
		// if (oldtime != null) {
		// try {
		// d = sf.parse(nowtimev);
		// cal.setTime(d);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm:ss");
		// return sf1.format(cal.getTime());
		// }

	}

	public static String checkShowTimes(String oldtime, String nowtime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			Calendar today = Calendar.getInstance();
			if (oldtime == null) {
				return formatDateInSpecial1(nowtime);
			} else {
				Date t = sf.parse(nowtime);
				today.setTime(t);

			}
			Calendar cal = Calendar.getInstance();
			if (oldtime != null) {
				d = sf.parse(oldtime);

			} else {
				d = sf.parse(nowtime);
			}
			cal.setTime(d);
			// cal.set(Calendar.HOUR, 0);
			// cal.set(Calendar.MINUTE, 0);
			// cal.set(Calendar.SECOND, 0);
			long intervalMilli = today.getTimeInMillis() - cal.getTimeInMillis();
			int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));

			if (xcts == 0) {
				// return sfToday.format(d);
				int xctmini = (int) (intervalMilli / (60 * 60 * 1000));
				if (xctmini == 0) {
					int xctsecond = (int) (intervalMilli / (60 * 1000));
					if (xctsecond >= 10) {
						// return xctsecond+"分钟前";
						SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm:ss");
						return sf1.format(cal.getTime());

					} else {
						return null;
					}
				} else {
					SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm:ss");
					return sf1.format(cal.getTime());
				}
			} else if (xcts == 1) {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			} else {
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
				return sf1.format(cal.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取手机支持的存储卡路径,利用java反射,Android3.0以上
	 * 
	 * @category Environment.getExternalStorageDirectory()获取存储卡的路径，
	 *           但是现在有很多手机内置有一个存储空间， 同时还支持外置sd卡插入，
	 *           这样通过Environment.getExternalStorageDirectory()方法获取到的就是内置存储卡的位置
	 * @param ctx
	 * @return
	 */
	public static List<String> getSdCardPath(Context ctx) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		StorageManager storageManager = (StorageManager) ctx.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			Object invoke = getVolumePathsMethod.invoke(storageManager, params);
			for (int i = 0; i < ((String[]) invoke).length; i++) {
				localArrayList.add(((String[]) invoke)[i]);
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return localArrayList;
	}

	/**
	 * 
	 * @param mImageView
	 * @param imageUrl
	 */
//	public static void getImageViewLoa(ImageView mImageView, String imageUrl) {
//		// 显示图片的配置
//		@SuppressWarnings("deprecation")
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.advertising_default_1)
//		// 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.advertising_default_1)
//				// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.advertising_default_1)
//				// 设置图片加载/解码过程中错误时候显示的图片
//				.cacheInMemory(true)
//				// 是否緩存都內存中
//				.cacheOnDisc(true)
//				// 是否緩存到sd卡上
//				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
//
//		ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);
//	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 获取设备号信息
	 * 
	 * @return
	 */
	public static String getdeviceid(Context context) {
		String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
		return ANDROID_ID;
	}
    public static String getOpenId(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		String openid=sp.getString("openId", "");
		return openid;
	}
	/**
	 * 获取sd卡路径
	 * 
	 * @return
	 */
	public static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	/**
	 * 获取手机的IMEI
	 * 
	 * @param context
	 * @return
	 */
//	public static String IMEI(Context context) {
//		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//		String IMEI = telephonyManager.getDeviceId();
//		return IMEI;
//	}

	/**
	 * 按质量压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static ByteArrayInputStream compressImage(Bitmap image) {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		return isBm;
	}

	/**
	 * 按质量和比例压缩图片小于100k
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageAndSize(Bitmap image) {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Options opt = new Options();
		opt.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opt);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 按比例压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImageBySimpleSize(Bitmap image) {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Options opt = new Options();
		opt.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opt);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 高斯模糊毛玻璃效果
	 * 
	 * @param sentBitmap
	 * @param radius
	 * @param canReuseInBitmap
	 * @return
	 */
	public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

		// Stack Blur v1.0 from
		// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
		//
		// Java Author: Mario Klingemann <mario at quasimondo.com>
		// http://incubator.quasimondo.com
		// created Feburary 29, 2004
		// Android port : Yahel Bouaziz <yahel at kayenko.com>
		// http://www.kayenko.com
		// ported april 5th, 2012

		// This is a compromise between Gaussian Blur and Box blur
		// It creates much better looking blurs than Box Blur, but is
		// 7x faster than my Gaussian Blur implementation.
		//
		// I called it Stack Blur because this describes best how this
		// filter works internally: it creates a kind of moving stack
		// of colors whilst scanning through the image. Thereby it
		// just has to add one new block of color to the right side
		// of the stack and remove the leftmost color. The remaining
		// colors on the topmost layer of the stack are either added on
		// or reduced by one, depending on if they are on the right or
		// on the left side of the stack.
		//
		// If you are using this algorithm in your code please add
		// the following line:
		//
		// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

		Bitmap bitmap;
		if (canReuseInBitmap) {
			bitmap = sentBitmap;
		} else {
			bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
		}

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		return (bitmap);
	}

	public static String getUser_Agent() {
		String ua = "Android;" + getOSVersion() + ";" + getVendor() + "-" + getDevice() + ";" + getPro();
		return ua;
	}

	/**
	 * device model name, e.g: GT-I9100
	 * 
	 * @return the user_Agent
	 */
	public static String getDevice() {
		return Build.MODEL;
	}

	public static String getPro() {
		return Build.MANUFACTURER;
	}

	public static boolean isHuaWei() {
		final String HUAWEI = "HUAWEI";
		return HUAWEI.equals(getPro());
	}

	/**
	 * device factory name, e.g: Samsung
	 * 
	 * @return the vENDOR
	 */
	public static String getVendor() {
		return Build.BRAND;
	}

	/**
	 * @return the SDK version
	 */
	public static int getSDKVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * @return the OS version
	 */
	public static String getOSVersion() {
		return Build.VERSION.RELEASE;
	}

	public static void callPhone(Context ctx, String phoneNo) {
		if (phoneNo == null || phoneNo.isEmpty()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
		ctx.startActivity(intent);
	}

}
