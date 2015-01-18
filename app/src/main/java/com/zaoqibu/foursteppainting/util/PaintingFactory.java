package com.zaoqibu.foursteppainting.util;

import java.lang.reflect.Field;

import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.Picture;
import com.zaoqibu.foursteppainting.R;

public class PaintingFactory  {
	private static int getDrawableIdWithResource(String name) {
		try {
			Field field = R.drawable.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private static int getStringIdWithResource(String name) {
		try {
			Field field = R.string.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
//	private static int getRawIdWithResource(String name) {
//		try {
//			Field field = R.raw.class.getField(name);
//			return Integer.parseInt(field.get(null).toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return 0;
//	}

}
