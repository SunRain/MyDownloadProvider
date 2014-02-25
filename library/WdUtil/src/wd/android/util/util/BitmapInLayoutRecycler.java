package wd.android.util.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 工具类 释放布局中所有Imageview组件占用的图片，可设置是否释放背景图 用于退出时释放资源，调用完成后，请不要刷新界面
 */
public class BitmapInLayoutRecycler {
	/**
	 * 释放Imageview占用的图片资源 用于退出时释放资源，调用完成后，请不要刷新界面
	 * 
	 * @param layout
	 *            需要释放图片的布局 *
	 * @param flagWithBackgroud
	 *            是否释放背景图 true:释放;false:不释放
	 */
	public static void recycle(ViewGroup layout, boolean flagWithBackgroud) {
		for (int i = 0; i < layout.getChildCount(); i++) {
			// 获得该布局的所有子布局
			View subView = layout.getChildAt(i);
			// 判断子布局属性，如果还是ViewGroup类型，递归回收
			if (subView instanceof ViewGroup) {
				// 递归回收
				recycle((ViewGroup) subView, flagWithBackgroud);
			} else {
				// 是Imageview的子例
				if (subView instanceof ImageView) {
					recycleImageViewBitMap((ImageView) subView,
							flagWithBackgroud);
				}
			}
		}
	}

	private static void recycleImageViewBitMap(ImageView imageView,
			boolean flagWithBackgroud) {
		if (imageView == null) {
			return;
		}
		// 回收占用的Bitmap
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		rceycleBitmapDrawable(drawable);
		// 如果flagWithBackgroud为true,则同时回收背景图
		if (flagWithBackgroud) {
			BitmapDrawable bd = (BitmapDrawable) imageView.getBackground();
			rceycleBitmapDrawable(bd);
		}
	}

	private static void rceycleBitmapDrawable(BitmapDrawable bd) {
		if (bd != null) {
			Bitmap bitmap = bd.getBitmap();
			rceycleBitmap(bitmap);
		}
		bd = null;
	}

	private static void rceycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

}
