package com.zaoqibu.foursteppainting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;
import com.zaoqibu.foursteppainting.domain.PaintingCategories;
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
	private PaintingCategories paintingCategories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        createBannerAnimation();
        initPaintingCategoryGridView();
		
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
	}

    private void createBannerAnimation() {
        ImageView imageViewBanner = (ImageView)findViewById(R.id.banner);
        AnimationDrawable animBanner = (AnimationDrawable)imageViewBanner.getBackground();
        animBanner.start();
    }

    private void initPaintingCategoryGridView() {
        initPaintingGroups();

        final int colNum = 3;
        final int calcGridItemWidth = GridViewUtil.calcItemWidth(this, colNum);

        GridView gvPaintingCategory = (GridView)findViewById(R.id.gvPaintingCategory);
        gvPaintingCategory.setAdapter(new PaintingCategoriesGridViewAdapter(this, calcGridItemWidth, paintingCategories));
        gvPaintingCategory.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PaintingCategory paintingCategory = paintingCategories.get(position);

                if (position == 0)    //历史记录
                    paintingCategory = getPaintingGroupWithHistory();

                //事件统计
                Map<String, String> map = new HashMap<String, String>();
                map.put("group", getResources().getString(paintingCategory.getName()));
                MobclickAgent.onEvent(MainActivity.this, "group", map);

                Intent intent = new Intent(MainActivity.this, PaintingCategoryActivity.class);
                intent.putExtra(PaintingCategoryActivity.ARG_PAINTING_CATEGORY, paintingCategory);
                startActivity(intent);
            }
        });
    }

    private void initPaintingGroups()
	{
		List<PaintingCategory> paintingCategories = new ArrayList<PaintingCategory>();
		
		paintingCategories.add(getPaintingGroupWithHistory());
		addPaintingGroupWithVehicle(paintingCategories);
		addPaintingGroupWithInsect(paintingCategories);
		addPaintingGroupWithBird(paintingCategories);
		addPaintingGroupWithFamilyReading(paintingCategories);
		
		this.paintingCategories = new PaintingCategories();
		this.paintingCategories.setDataSource(paintingCategories);
	}
	
	//历史记录
	private PaintingCategory getPaintingGroupWithHistory()
	{
		PaintingCategory group = new PaintingCategory(R.string.history, R.drawable.group_history, R.raw.group_history);
		
		History history = new History(this);
		int n = history.count();
		for (int i=0; i<n; ++i)
			group.add( PaintingFactory.create(history.getPainting(i)) );
		
		return group;
	}
	
	//交通工具
	private void addPaintingGroupWithVehicle(List<PaintingCategory> groups)
	{
		PaintingCategory group = new PaintingCategory(R.string.vehicle, R.drawable.group_vehicle, R.raw.group_vehicle);
		
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
	private void addPaintingGroupWithInsect(List<PaintingCategory> groups)
	{
		PaintingCategory group = new PaintingCategory(R.string.insect, R.drawable.group_insect, R.raw.group_insect);
		
		String[] names = {/*"qingting",*/ "piaochong", "mayi", "yinghuochong", /*"hudie",*/ "tanglang", "mifeng", /*"lujiaochong",*/ "zhameng", "mifeng_", 
				"hudie_", "maomaochong", /*"wenzi",*/ "chan", "wenzi_"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}
	
	//鸟类
	private void addPaintingGroupWithBird(List<PaintingCategory> groups)
	{
		PaintingCategory group = new PaintingCategory(R.string.bird, R.drawable.group_bird, R.raw.group_bird);
		
		String[] names = {"juzuiniao", "ying", "wuya", "gezi", "yanzi", "maotouying", /*"fengniao",*/ "tujiu", "qie", /*"yingwu",*/ 
				"tiane", /*"tihu",*/ "huolieniao", "juzuiniao_", "yuanyang", "maotouying_", "niao", "tuoniao", "zhuomuniao", "kongque"};
		
		for (String name : names)
			group.add(PaintingFactory.create(name));
		
		groups.add(group);
	}
	
	//亲子阅读
	private void addPaintingGroupWithFamilyReading(List<PaintingCategory> groups)
	{
		PaintingCategory group = new PaintingCategory(R.string.familyreading, R.drawable.group_family_reading, R.raw.group_family_reading);
		
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
