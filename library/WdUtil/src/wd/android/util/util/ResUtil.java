package wd.android.util.util;

import android.content.Context;

/**
 * 通过字段名称动态获取id
 */
public class ResUtil {
	public static int getLayoutId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "layout");
	}

	public static int getStringId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "string");
	}

	public static int getDrawableId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "drawable");
	}

	public static int getStyleId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "style");
	}

	public static int getId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "id");
	}

	public static int getColorId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "color");
	}

	public static int getDimenId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "dimen");
	}

	public static int getAnimId(Context paramContext, String paramString) {
		return getIdentifier(paramContext, paramString, "anim");
	}

	private static int getIdentifier(Context context, String paramString,
			String resTag) {
		return context.getResources().getIdentifier(paramString, resTag,
				context.getPackageName());
	}
}
