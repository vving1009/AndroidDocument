 
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
	 * ������ѹ��
	 * @param bitmap
	 * @return
	 */
		public static Bitmap compressImage(Bitmap bitmap){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int options = 100;
			//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			while ( baos.toByteArray().length / 1024>100) {
				//���baos
				baos.reset();
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
				options -= 10;//ÿ�ζ�����10
			}
			//��ѹ���������baos��ŵ�ByteArrayInputStream��
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			//��ByteArrayInputStream��������ͼƬ
			Bitmap newBitmap = BitmapFactory.decodeStream(isBm, null, null);
			return newBitmap;
		}
 
	/**
	 * ��ͼƬ�ߴ�ѹ�� ����Ϊ·��
	 * @param imgPath ͼƬ·��
	 * @param pixelW Ŀ��ͼƬ���
	 * @param pixelH Ŀ��ͼƬ�߶�
	 * @return
	 */
	public static Bitmap compressImageFromPath(String imgPath, int pixelW, int pixelH) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true����ֻ���߲�������
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(imgPath,options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = computeSampleSize(options , pixelH > pixelW ? pixelH : pixelW ,pixelW * pixelH );
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
		return bitmap;
	}
 
	/**
	 * ��ͼƬ�ߴ�ѹ�� ������bitmap
	 * @param bitmap
	 * @param pixelW
	 * @param pixelH
	 * @return
	 */
	public static Bitmap compressImageFromBitmap(Bitmap bitmap, int pixelW, int pixelH) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		if( os.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			os.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��
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
	 * ��ͼƬ��������ָ����С
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
	 * ����ʡ�ڴ�ķ�ʽ��ȡ������Դ��ͼƬ
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
 
	/**
	 * androidԴ���ṩ�����ǵĶ�̬�����ͼƬ��inSampleSize����
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