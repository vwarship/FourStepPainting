package com.zaoqibu.foursteppainting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.PaintingGroup;
import com.zaoqibu.foursteppainting.domain.PaintingGroups;
import com.zaoqibu.foursteppainting.util.GridViewUtil;
import com.zaoqibu.foursteppainting.util.History;
import com.zaoqibu.foursteppainting.util.PaintingFactory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity 
{
	private PaintingGroups paintingGroups;
	
	public MainActivity()
	{
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView imageViewBanner = (ImageView)findViewById(R.id.banner);
		AnimationDrawable animBanner = (AnimationDrawable)imageViewBanner.getBackground();
		animBanner.start();
		
		initPaintingGroups();
		
		final int calcGridItemWidth = GridViewUtil.calcItemWidth(this);
		
		GridView gridView = (GridView)findViewById(R.id.gridView);
		gridView.setNumColumns(GridViewUtil.getColumnNumber(this));
		gridView.setAdapter(new GridViewAdapter(this, calcGridItemWidth, paintingGroups));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				PaintingGroup paintingGroup = paintingGroups.get(position);

				if (position == 0)	//历史记录
					paintingGroup = getPaintingGroupWithHistory();
				
				//事件统计
				Map<String,String> map = new HashMap<String,String>();
				map.put("group", getResources().getString(paintingGroup.getName()));
				MobclickAgent.onEvent(MainActivity.this, "group", map);
				
				Intent intent = new Intent(MainActivity.this, PaintingGroupActivity.class);
				intent.putExtra(PaintingGroupActivity.ARG_PAINTINGGROUP, paintingGroup);
				startActivity(intent);
			}
		});
		
//		MobclickAgent.setDebugMode( true );	//调试模式
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
	}
	
	private void initPaintingGroups()
	{
		List<PaintingGroup> paintingGroups = new ArrayList<PaintingGroup>();
		
		paintingGroups.add(getPaintingGroupWithHistory());
		addPaintingGroupWithVehicle(paintingGroups);
		addPaintingGroupWithInsect(paintingGroups);
		addPaintingGroupWithBird(paintingGroups);
		addPaintingGroupWithFamilyReading(paintingGroups);
		
		this.paintingGroups = new PaintingGroups();
		this.paintingGroups.setDataSource(paintingGroups); 
	}
	
	//历史记录
	private PaintingGroup getPaintingGroupWithHistory()
	{
		PaintingGroup group = new PaintingGroup(R.string.history, R.drawable.group_history, R.raw.group_history);
		
		History history = new History(this);
		int n = history.count();
		for (int i=0; i<n; ++i)
			group.add( PaintingFactory.create(history.getPainting(i)) );
		
		return group;
	}
	
	//交通工具
	private void addPaintingGroupWithVehicle(List<PaintingGroup> groups)
	{
		PaintingGroup group = new PaintingGroup(R.string.vehicle, R.drawable.group_vehicle, R.raw.group_vehicle);
		
		String[] names = {/*"feiting",*/ "reqiqiu", "lunchuan", "fanchuan", "shoutuiche", "zixingche", "motuoche", "mianbaoche", "paoche", "jiaoche", 
				"jipuche", "changpengjipuche", /*"keche", "shuangcengkeche",*/ "xiaohuoche", "dahuoche", "kache", "saiche", "chanche", "tuoche", 
				/*"fandouche",*/ "yaluji", "tuolaji", "jiuyuanche", "watuji", "xiaofangche", "jiuhuche", "tanke", "zhuangjiache", /*"qianshuiting", */
				"kuaiting", "huoche", "huojian", "feiji", "zhishengji", "zhandouji", /*"youguanche",*/ "jiaobanche", "kechuan", "qingguilieche", 
				"huolun", "feidie", "renliche"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}
	
	//昆虫
	private void addPaintingGroupWithInsect(List<PaintingGroup> groups)
	{
		PaintingGroup group = new PaintingGroup(R.string.insect, R.drawable.group_insect, R.raw.group_insect);
		
		String[] names = {/*"qingting",*/ "piaochong", "mayi", "yinghuochong", /*"hudie",*/ "tanglang", "mifeng", /*"lujiaochong",*/ "zhameng", "mifeng_", 
				"hudie_", "maomaochong", /*"wenzi",*/ "chan", "wenzi_"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}
	
	//鸟类
	private void addPaintingGroupWithBird(List<PaintingGroup> groups)
	{
		PaintingGroup group = new PaintingGroup(R.string.bird, R.drawable.group_bird, R.raw.group_bird);
		
		String[] names = {"juzuiniao", "ying", "wuya", "gezi", "yanzi", "maotouying", /*"fengniao",*/ "tujiu", "qie", /*"yingwu",*/ 
				"tiane", /*"tihu",*/ "huolieniao", "juzuiniao_", "yuanyang", "maotouying_", "niao", "tuoniao", "zhuomuniao", "kongque"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}
	
	//亲子阅读
	private void addPaintingGroupWithFamilyReading(List<PaintingGroup> groups)
	{
		PaintingGroup group = new PaintingGroup(R.string.familyreading, R.drawable.group_family_reading, R.raw.group_family_reading);
		
		String[] names = {"feiting", "keche", "shuangcengkeche", "fandouche", "qianshuiting", "youguanche", "qingting", "hudie", "lujiaochong", "wenzi", "fengniao", "yingwu", "tihu"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case R.id.action_about:
		{
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			MobclickAgent.onEvent(this, "about");
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
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
