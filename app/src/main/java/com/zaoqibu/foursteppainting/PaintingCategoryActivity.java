package com.zaoqibu.foursteppainting;

import java.util.HashMap;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;
import com.zaoqibu.foursteppainting.util.GridViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PaintingCategoryActivity extends Activity
{
	public static final String ARG_PAINTING_CATEGORY = "paintingCategory";
	private PaintingCategory paintingCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paintingcategory);
		
		paintingCategory = (PaintingCategory)getIntent().getExtras().getSerializable(ARG_PAINTING_CATEGORY);
		
		getActionBar().setTitle(paintingCategory.getName());
		
		final int calcGridItemWidth = GridViewUtil.calcItemWidth(this);
		
		GridView gridView = (GridView)findViewById(R.id.gvPaintings);
		gridView.setNumColumns(GridViewUtil.getColumnNumber(this));
		gridView.setAdapter(new PaintingCategoryGridViewAdapter(this, calcGridItemWidth, paintingCategory));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Painting painting = paintingCategory.get(position);
				
				//事件统计
				Map<String,String> map = new HashMap<String,String>();
				map.put("group", getResources().getString(paintingCategory.getName()));
				map.put("painting", getResources().getString(painting.getName()));
				MobclickAgent.onEvent(PaintingCategoryActivity.this, "painting", map);
				
				Intent intent = new Intent(PaintingCategoryActivity.this, PaintingActivity.class);
				intent.putExtra(PaintingActivity.ARG_PAINTING, painting);
				startActivity(intent);
			}
		});
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getSimpleName());
		MobclickAgent.onResume(this);
	}
	
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}

}
