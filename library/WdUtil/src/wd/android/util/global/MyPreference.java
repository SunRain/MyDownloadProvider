package wd.android.util.global;

import java.util.Set;

import wd.android.util.util.EnvironmentInfo;
import wd.android.util.util.ObjectUtil;
import wd.android.util.util.EnvironmentInfo.SdkUtil;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Preference包装类，可将数据存储于SharedPreferences中
 */
public class MyPreference {
	private String packName = "";
	private Context mContext;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	/** 是否是事务模式，在事务模式下，提交的修改都不会写入文件 */
	private volatile boolean isTransactionMode = false;

	public MyPreference(Context context) {
		mContext = context;
		packName = context.getPackageName();
		settings = mContext.getSharedPreferences(packName, 0);
		editor = settings.edit();
	}

	/**
	 * 开启一个存储事务，开启后修改操作都不会写入文件，必须通过{@link #commitTransaction()}提交修改
	 */
	public void beginTransaction() {
		synchronized (this) {
			isTransactionMode = true;
		}
	}

	/**
	 * 提交存储事务
	 */
	public void commitTransaction() {
		synchronized (this) {
			isTransactionMode = false;
		}
		editor.commit();
	}

	private void commit() {
		synchronized (this) {
			if (!isTransactionMode) {
				editor.commit();
			}
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String read(String key, String defaultValue) {
		return settings.getString(key, defaultValue);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean read(String key, boolean defaultValue) {
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public float read(String key, float defaultValue) {
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int read(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long read(String key, long defaultValue) {
		return settings.getLong(key, defaultValue);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> read(String key, Set<String> defaultValue) {
		if (!SdkUtil.hasHoneycomb()) {
			return ObjectUtil.newHashSet();
		}
		return settings.getStringSet(key, defaultValue);
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void write(String key, String value) {
		editor.putString(key, value);
		commit();
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void write(String key, boolean value) {
		editor.putBoolean(key, value);
		commit();
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void write(String key, float value) {
		editor.putFloat(key, value);
		commit();
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void write(String key, int value) {
		editor.putInt(key, value);
		commit();
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	public void write(String key, long value) {
		editor.putLong(key, value);
		commit();
	}

	/**
	 * 写入数据
	 * 
	 * @param key
	 * @param value
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void write(String key, Set<String> value) {
		if (!SdkUtil.hasHoneycomb()) {
			commit();
			return;
		}
		editor.putStringSet(key, value);
		commit();
	}
}