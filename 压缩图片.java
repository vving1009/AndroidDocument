 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
 
/**
 * Created by xiaoqi on 2016/8/15.
 */
public class CompressUtils {
	/**
	 * 按质量压缩
	 * @param bitmap
	 * @return
	 */
		public static Bitmap compressImage(Bitmap bitmap){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int options = 100;
			//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			while ( baos.toByteArray().length / 1024>100) {
				//清空baos
				baos.reset();
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
				options -= 10;//每次都减少10
			}
			//把压缩后的数据baos存放到ByteArrayInputStream中
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			//把ByteArrayInputStream数据生成图片
			Bitmap newBitmap = BitmapFactory.decodeStream(isBm, null, null);
			return newBitmap;
		}
 
	/**
	 * 按图片尺寸压缩 参数为路径
	 * @param imgPath 图片路径
	 * @param pixelW 目标图片宽度
	 * @param pixelH 目标图片高度
	 * @return
	 */
	public static Bitmap compressImageFromPath(String imgPath, int pixelW, int pixelH) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(imgPath,options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = computeSampleSize(options , pixelH > pixelW ? pixelH : pixelW ,pixelW * pixelH );
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
		return bitmap;
	}
 
	/**
	 * 按图片尺寸压缩 参数是bitmap
	 * @param bitmap
	 * @param pixelW
	 * @param pixelH
	 * @return
	 */
	public static Bitmap compressImageFromBitmap(Bitmap bitmap, int pixelW, int pixelH) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			os.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeStream(is, null, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = computeSampleSize(options , pixelH > pixelW ? pixelH : pixelW ,pixelW * pixelH );
		is = new ByteArrayInputStream(os.toByteArray());
		Bitmap newBitmap = BitmapFactory.decodeStream(is, null, options);
		return newBitmap;
	}
 
	/**
	 * 对图片进行缩放指定大小
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap scaleTo(Bitmap bitmap, int width, int height){
		int originalWidth = bitmap.getWidth();
		int originalHeight = bitmap.getHeight();
		float xScale = (float)width / originalWidth;
		float yScale = (float)height / originalHeight;
		Matrix matrix = new Matrix();
		matrix.setScale(xScale,yScale);
		return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);
	}
 
	/**
	 * 以最省内存的方式读取本地资源的图片
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
 
	/**
	 * android源码提供给我们的动态计算出图片的inSampleSize方法
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}
 
	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
}