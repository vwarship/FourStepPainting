package com.zaoqibu.foursteppainting;

import java.util.HashMap;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.PaintingGroup;
import com.zaoqibu.foursteppainting.util.GridViewUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class PaintingGroupActivity extends Activity
{
	public static final String ARG_PAINTINGGROUP = "paintingGroup";
	private PaintingGroup paintingGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paintinggroup);
		
		paintingGroup = (PaintingGroup)getIntent().getExtras().getSerializable(ARG_PAINTINGGROUP);
		
		getActionBar().setTitle(paintingGroup.getName());
		
		final int calcGridItemWidth = GridViewUtil.calcItemWidth(this);
		
		GridView gridView = (GridView)findViewById(R.id.gridViewPaintingGroup);
		gridView.setNumColumns(GridViewUtil.getColumnNumber(this));
		gridView.setAdapter(new PaintingGroupGridViewAdapter(this, calcGridItemWidth, paintingGroup));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Painting painting = paintingGroup.get(position);
				
				//事件统计
				Map<String,String> map = new HashMap<String,String>();
				map.put("group", getResources().getString(paintingGroup.getName()));
				map.put("painting", getResources().getString(painting.getName()));
				MobclickAgent.onEvent(PaintingGroupActivity.this, "painting", map);
				
				Intent intent = new Intent(PaintingGroupActivity.this, PaintingActivity.class);
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
