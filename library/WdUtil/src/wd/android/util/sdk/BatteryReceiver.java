package wd.android.util.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * 电池监听器
 */
public class BatteryReceiver extends BaseBroadcastReceiver {

	private int intLevel;
	private int intScale = 100;

	/** 自定义电量变化监听器 */
	private BatteryListener listener;

	public BatteryReceiver(Context context, BatteryListener listener) {
		super(context);
		this.listener = listener;
	}

	/** 电量监听接口 */
	public interface BatteryListener {
		abstract void batteryChanged(int intLevel, int intScale);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		intLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		intScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);

		if (listener != null) {
			listener.batteryChanged(intLevel, intScale);
		}
	}

	@Override
	protected IntentFilter createIntentFilter() {
		IntentFilter intentFilter = super.createIntentFilter();
		intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		return intentFilter;
	}
}