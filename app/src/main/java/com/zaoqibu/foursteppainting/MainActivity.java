package com.zaoqibu.foursteppainting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.PaintingCategories;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;
import com.zaoqibu.foursteppainting.util.CategoriesXmlParser;
import com.zaoqibu.foursteppainting.util.GridViewUtil;
import com.zaoqibu.foursteppainting.util.MediaPlayerSingleton;
import com.zaoqibu.foursteppainting.util.VibratorUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
        initPaintingCategories();

        final int colNum = 3;
        final int calcGridItemWidth = GridViewUtil.calcItemWidth(this, colNum);

        GridView gvPaintingCategory = (GridView)findViewById(R.id.gvPaintingCategory);
        gvPaintingCategory.setAdapter(new PaintingCategoriesGridViewAdapter(this, calcGridItemWidth, paintingCategories));
        gvPaintingCategory.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                VibratorUtil.vibrate(MainActivity.this);
                PaintingCategory paintingCategory = paintingCategories.get(position);

                //事件统计
                Map<String, String> map = new HashMap<String, String>();
                map.put("group", paintingCategory.getName());
                MobclickAgent.onEvent(MainActivity.this, "group", map);

                Intent intent = new Intent(MainActivity.this, PaintingCategoryActivity.class);
                intent.putExtra(PaintingCategoryActivity.ARG_PAINTING_CATEGORY, paintingCategory);
                startActivity(intent);
            }
        });
    }

    private void initPaintingCategories() {
        CategoriesXmlParser parser = new CategoriesXmlParser();
        InputStream inputStream = null;
        PaintingCategories paintingCategories = new PaintingCategories();
        try {
            inputStream = getAssets().open("categories.xml");
            paintingCategories = parser.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.paintingCategories = paintingCategories;
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

    @Override
    protected void onDestroy() {
        MediaPlayerSingleton.getInstance().release();
        super.onDestroy();
    }

}
