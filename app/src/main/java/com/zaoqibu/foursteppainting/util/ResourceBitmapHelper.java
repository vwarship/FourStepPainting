package com.zaoqibu.foursteppainting.util;

import java.lang.reflect.Field;

import com.zaoqibu.foursteppainting.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class ResourceBitmapHelper
{
	private Resources mResources;
	private Size mZoomSize;
	
	public Size getZoomSize()
	{
		return mZoomSize;
	}
	
    public class Size
    {
        public Size(int w, int h) 
        {
            width = w;
            height = h;
        }
        
        public int width;
        public int height;
    };
	
	public ResourceBitmapHelper(Resources resources)
	{
		mResources = resources;
	}
	
	public BitmapDrawable getDrawableFromResourceName(String resourceName, int containerWidth)
	{
		return getDrawableFromResourceId(getResourcesDrawableId(resourceName), containerWidth, true);
	}
	
	public BitmapDrawable getDrawableFromResourceNameWithNoCompress(String resourceName, int containerWidth)
	{
		return getDrawableFromResourceId(getResourcesDrawableId(resourceName), containerWidth, false);
	}
	
	public static int getResourcesDrawableId(String name)
	{
		try {
			Field field = R.drawable.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public BitmapDrawable getDrawableFromResourceId(int resourceId, int containerWidth, boolean isCompress)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();

		Size imageSize = calculateImageSize(resourceId, options);
		Size zoomSize = calculateImageZoomSize(resourceId, containerWidth, imageSize);
		
		float compressNum = 1;
		if (isCompress)
			compressNum = 2;
		//为了减少占用内存，再缩小一半尺寸。
		Size zoomOutHalfSize = new Size((int)(zoomSize.width/compressNum), (int)(zoomSize.height/compressNum));
		Bitmap bitmap = decodeSampledBitmapFromResource(resourceId, imageSize, zoomOutHalfSize, options);
		
		BitmapDrawable drawable = new BitmapDrawable(mResources, bitmap);
    	//显示要使用原缩放的大小。
    	drawable.setBounds(0, 0, zoomSize.width, zoomSize.height);
    	
    	return drawable;
	}
	
	private Size calculateImageZoomSize(int resourceId, int containerWidth, Size imageSize)
	{
        double ratio = (double)containerWidth/imageSize.width;
		int drawImageWidth = (int)(imageSize.width*ratio);
		int drawImageHeight = (int)(imageSize.height*ratio);
		
		return new Size(drawImageWidth, drawImageHeight);
	}
	
	private Bitmap decodeSampledBitmapFromResource(int resourceId, Size imageSize, Size zoomSize, BitmapFactory.Options options) 
	{
		// 调用上面定义的方法计算inSampleSize值
	    options.inSampleSize = calculateSampleSize(imageSize, zoomSize);
	    
	    // 使用获取到的inSampleSize值再次解析图片
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(mResources, resourceId, options);
	}
	
	private Size calculateImageSize(int resourceId, BitmapFactory.Options options)
	{
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(mResources, resourceId, options);
	    
	    return new Size(options.outWidth, options.outHeight);
	}

	private int calculateSampleSize(Size imageSize, Size zoomSize)
	{
		int inSampleSize = 1;
		
		if (imageSize.width > zoomSize.width || imageSize.height > zoomSize.height)
		{
			// 计算出实际宽高和目标宽高的比率
			final int widthRatio = Math.round((float) imageSize.width / (float) zoomSize.width);
			final int heightRatio = Math.round((float) imageSize.height / (float) zoomSize.height);
			
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		
		return inSampleSize;
	}

}
