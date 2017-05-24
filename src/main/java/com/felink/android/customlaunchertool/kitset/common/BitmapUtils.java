package com.felink.android.customlaunchertool.kitset.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.io.FileOutputStream;

/**
 * 图片处理相关内容
 */
public class BitmapUtils{
	
	/**
	 * 根据颜色值，产生一个颜色矩阵， 这个矩阵让产生的颜色效果
	 * 目标Rd=(Rs*Rm)/255;Gd=(Rs*Gm)/255;Bd=(Rs*Bm)/255;Ad=As;
	 * */
	private static float[] createColorFilterArray(int color) {
		float r = 1;
		float g = 1;
		float b = 1;
		float a = 1;
		float[] colorArray = new float[20];
		for (int i = 0; i < colorArray.length; i++) {
			colorArray[i] = 0;
		}
		r = Color.red(color) / 255f;
		g = Color.green(color) / 255f;
		b = Color.blue(color) / 255f;
		a = Color.alpha(color) / 255f;

		colorArray[0] = r;
		colorArray[5] = g;
		colorArray[10] = b;
		colorArray[18] = a;

		return colorArray;
	}

	public static Bitmap CreateColorImage(Bitmap srcBitmap, int color) {
		Paint paint = new Paint();
		float colorArrar[] = createColorFilterArray(color);
		paint.setColorFilter(new ColorMatrixColorFilter(colorArrar));
		Bitmap destBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
				srcBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(destBitmap);
		canvas.drawBitmap(srcBitmap, 0, 0, paint);
		return destBitmap;
	}

	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if(null != drawable && drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1?Config.ARGB_8888:Config.RGB_565);
			if(bitmap == null) {
				return null;
			} else {
				Canvas canvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				drawable.draw(canvas);
				return bitmap;
			}
		} else {
			return null;
		}
	}

	public static boolean saveBitmap2file(Bitmap bmp, String filePath, Bitmap.CompressFormat format) {
		if(bmp != null && !bmp.isRecycled()) {
			byte quality = 100;
			FileOutputStream stream = null;

			try {
				stream = new FileOutputStream(filePath);
			} catch (Exception var6) {
				var6.printStackTrace();
			}

			return null == stream?false:bmp.compress(format, quality, stream);
		} else {
			return false;
		}
	}

}
