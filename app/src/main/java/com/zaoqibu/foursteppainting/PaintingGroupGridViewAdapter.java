package com.zaoqibu.foursteppainting;

import java.util.HashMap;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.PaintingGroup;
import com.zaoqibu.foursteppainting.util.BitmapUtil;
import com.zaoqibu.foursteppainting.util.MediaPlayerSingleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PaintingGroupGridViewAdapter extends BaseAdapter
{
	private Context context;
	private int calcGridItemWidth;
	
	private PaintingGroup paintingGroup;
	private MediaPlayerSingleton mediaPlayer;

	public PaintingGroupGridViewAdapter(Context context, int calcGridItemWidth, PaintingGroup paintingGroup)
	{
		this.context = context;
		this.calcGridItemWidth = calcGridItemWidth;
		this.paintingGroup = paintingGroup;
		
		mediaPlayer = MediaPlayerSingleton.getInstance();
	}

	@Override
	public int getCount() {
		return paintingGroup.count();
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
			item = layoutInflater.inflate(R.layout.gridview_item, parent, false);
			item.setLayoutParams(new GridView.LayoutParams(calcGridItemWidth, calcGridItemWidth));
		}
		else
		{
			item = convertView;
		}
		
		final Painting painting = paintingGroup.get(position);
		
		ImageView imageView = (ImageView)item.findViewById(R.id.itemImage);
		Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(context.getResources(), painting.getIcon(), 100, 100);
		imageView.setImageBitmap(bitmap);

		TextView textView = (TextView)item.findViewById(R.id.itemText);
		textView.setText(painting.getName());

		ImageView imageViewPlay = (ImageView)item.findViewById(R.id.imageViewPlay);
		imageViewPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//事件统计
				Map<String,String> map = new HashMap<String,String>();
				map.put("group", context.getResources().getString(paintingGroup.getName()));
				map.put("painting", context.getResources().getString(painting.getName()));
				MobclickAgent.onEvent(context, "painting_sound", map);

				mediaPlayer.play(context, painting.getSound());
			}
		});
		
		return item;
	}

}
