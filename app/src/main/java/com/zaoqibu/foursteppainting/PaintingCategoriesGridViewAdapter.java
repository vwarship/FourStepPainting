package com.zaoqibu.foursteppainting;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;
import com.zaoqibu.foursteppainting.domain.PaintingCategories;
import com.zaoqibu.foursteppainting.util.BitmapUtil;
import com.zaoqibu.foursteppainting.util.MediaPlayerSingleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PaintingCategoriesGridViewAdapter extends BaseAdapter
{
	private Context context;
	private int calcGridItemWidth;
	
	private PaintingCategories paintingCategories;
	
	private Bitmap bitmap = null;
	private MediaPlayerSingleton mediaPlayer;
	
	public PaintingCategoriesGridViewAdapter(Context context, int calcGridItemWidth, PaintingCategories paintingCategories)
	{
		this.context = context;
		this.calcGridItemWidth = calcGridItemWidth;
		this.paintingCategories = paintingCategories;
		
		mediaPlayer = MediaPlayerSingleton.getInstance();
	}

	@Override
	public int getCount() {
		return paintingCategories.count();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		
		View item = null;
		
		if (convertView == null)
		{
			item = layoutInflater.inflate(R.layout.gridview_item_paintingcategories, parent, false);
			item.setLayoutParams(new GridView.LayoutParams(calcGridItemWidth, calcGridItemWidth));
		}
		else
		{
			item = convertView;
		}
		
		final PaintingCategory paintingCategory = paintingCategories.get(position);
		
		ImageView imageView = (ImageView)item.findViewById(R.id.itemImage);

        //TODO
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(paintingCategory.getIcon());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapUtil.decodeSampledBitmapFromStream(inputStream, 150, 150);
        imageView.setImageBitmap(bitmap);
        imageView.setBackgroundColor(Color.parseColor(paintingCategory.getBackgroundColor()));

		ImageView imageViewPlay = (ImageView)item.findViewById(R.id.imageViewPlay);
		imageViewPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//事件统计
				Map<String,String> map = new HashMap<String,String>();
				map.put("group", paintingCategory.getName());
				MobclickAgent.onEvent(context, "group_sound", map);
				
				//TODO
                mediaPlayer.play(context, paintingCategory.getSoundPath());
			}
		});
		
		return item;
	}
	
}
