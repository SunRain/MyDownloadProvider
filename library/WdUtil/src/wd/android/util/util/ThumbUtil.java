package wd.android.util.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;

/**
 * 缩略图Util类
 */
public class ThumbUtil {
	public static Cursor queryThumbnails(Activity context) {
		String[] columns = new String[] {
				// Thumbnails表示的缩略图的意思
				MediaStore.Images.Thumbnails.DATA,
				MediaStore.Images.Thumbnails._ID,
				MediaStore.Images.Thumbnails.IMAGE_ID };
		return context.managedQuery(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, columns,
				null, null, MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);
	}

	public static Cursor queryThumbnails(Activity context, String selection,
			String[] selectionArgs) {
		String[] columns = new String[] { MediaStore.Images.Thumbnails.DATA,
				MediaStore.Images.Thumbnails._ID,
				MediaStore.Images.Thumbnails.IMAGE_ID };
		// public final Cursor managedQuery (Uri uri, String[] projection,
		// String selection, String[] selectionArgs, String sortOrder)
		// uri The URI of the content provider to query.
		// projection List of columns to return.
		// selection SQL WHERE clause.
		// selectionArgs The arguments to selection, if any ?s are pesent
		// sortOrder SQL ORDER BY clause.
		return context.managedQuery(
				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, columns,
				selection, selectionArgs,
				MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);
	}

	// 通过缩略图的ID来查询
	public static Bitmap queryThumbnailById(Activity context, int thumbId) {
		String selection = MediaStore.Images.Thumbnails._ID + " = ?";
		String[] selectionArgs = new String[] { thumbId + "" };
		Cursor cursor = queryThumbnails(context, selection, selectionArgs);
		if (cursor.moveToFirst()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			cursor.close();
			return BitmapUtil.decodeBitmap(path, 100, 100);
		} else {
			cursor.close();
			return null;
		}
	}

	public static Bitmap[] queryThumbnailsByIds(Activity context,
			Integer[] thumbIds) {
		Bitmap[] bitmaps = new Bitmap[thumbIds.length];
		for (int i = 0; i < bitmaps.length; i++) {
			bitmaps[i] = queryThumbnailById(context, thumbIds[i]);
		}
		return bitmaps;
	}

	/**
	 * * 获取全部
	 * 
	 * *
	 * 
	 * @param context
	 *            * @return
	 * 
	 * */
	public static List<Bitmap> queryThumbnailList(Activity context) {
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();
		Cursor cursor = queryThumbnails(context);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			Bitmap b = BitmapUtil.decodeBitmap(path, 100, 100);
			bitmaps.add(b);
		}
		cursor.close();
		return bitmaps;
	}

	public static List<Bitmap> queryThumbnailListByIds(Activity context,
			int[] thumbIds) {
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();
		for (int i = 0; i < thumbIds.length; i++) {
			Bitmap b = queryThumbnailById(context, thumbIds[i]);
			bitmaps.add(b);
		}
		return bitmaps;
	}

	public static Cursor queryImages(Activity context) {
		String[] columns = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };
		return context.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
	}

	public static Cursor queryImages(Activity context, String selection,
			String[] selectionArgs) {
		String[] columns = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME };
		return context.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
				selection, selectionArgs,
				MediaStore.Images.Media.DEFAULT_SORT_ORDER);
	}

	public static Bitmap queryImageById(Activity context, int imageId) {
		String selection = MediaStore.Images.Media._ID + "=?";
		String[] selectionArgs = new String[] { imageId + "" };
		Cursor cursor = queryImages(context, selection, selectionArgs);
		if (cursor.moveToFirst()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			cursor.close();
			return BitmapUtil.decodeBitmap(path, 220, 220);
		} else {
			cursor.close();
			return null;
		}
	}

	/**
	 * * 根据缩略图的Id获取对应的大图 *
	 * 
	 * @param context
	 *            *
	 * @param thumbId
	 *            *
	 * @return
	 * */
	public static Bitmap queryImageByThumbnailId(Activity context,
			Integer thumbId) {
		String selection = MediaStore.Images.Thumbnails._ID + " = ?";
		String[] selectionArgs = new String[] { thumbId + "" };
		Cursor cursor = queryThumbnails(context, selection, selectionArgs);
		if (cursor.moveToFirst()) {
			int imageId = cursor
					.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
			cursor.close();
			return queryImageById(context, imageId);
		} else {
			cursor.close();
			return null;
		}
	}
}
