package com.zaoqibu.foursteppainting;

import com.umeng.analytics.MobclickAgent;
import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.Picture;
import com.zaoqibu.foursteppainting.util.BitmapUtil;
import com.zaoqibu.foursteppainting.util.History;
import com.zaoqibu.foursteppainting.util.MediaPlayerSingleton;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class PaintingActivity extends FragmentActivity {
	public static final String ARG_PAINTING = "painting";
	private Painting painting;
	private int position = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_painting);
		
		painting = (Painting)getIntent().getExtras().getSerializable(ARG_PAINTING);
		
		ViewPager paintingPager = (ViewPager)findViewById(R.id.paintingPager);
		paintingPager.setAdapter(new PaintingPagerAdapter(getSupportFragmentManager(), painting));
		
		paintingPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	PaintingActivity.this.position = position;
            	MediaPlayerSingleton.getInstance().play(PaintingActivity.this, painting.get(position).getSoundPath());
            }
		});
		
		paintingPager.setCurrentItem(position);
		MediaPlayerSingleton.getInstance().play(this, painting.get(position).getSoundPath());

        //TODO
//		History history = new History(this);
//		history.putPainting(painting.getTag());
	}
	
	public class PaintingPagerAdapter extends FragmentPagerAdapter
	{
		private Painting painting;
		
		public PaintingPagerAdapter(FragmentManager fm, Painting painting) {
			super(fm);
			this.painting = painting;
		}

		@Override
		public Fragment getItem(int i) {
			Picture picture = painting.get(i);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable(PictureFragment.ARG_PICTURE, picture);
			
			Fragment fragment = new PictureFragment();
			fragment.setArguments(bundle);
			
			return fragment;
		}

		@Override
		public int getCount() {
			return painting.count();
		}
		
	}
	
	public static class PictureFragment extends Fragment
	{
		public static final String ARG_PAINTINGACTIVITY = "paintingActivity";
		public static final String ARG_PICTURE = "picture";
		private Bitmap bitmap = null;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			final Picture picture = (Picture)getArguments().getSerializable(ARG_PICTURE);
			
			View rootView = inflater.inflate(R.layout.activity_picture, container, false);
			
			ImageView imageViewPicture = (ImageView)rootView.findViewById(R.id.imageViewPicture);
            InputStream inputStream = null;
            try {
                inputStream = getActivity().getAssets().open(picture.getImagePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
			bitmap = BitmapUtil.decodeSampledBitmapFromStream(inputStream, 480, 800);
			imageViewPicture.setImageBitmap(bitmap);
			
			TextView textViewPictureContent = (TextView)rootView.findViewById(R.id.textViewPictureContent);
			textViewPictureContent.setText(String.format("%d. %s", picture.getOrder(), picture.getContent()));
			
			ImageButton imageButtonPicturePlay = (ImageButton)rootView.findViewById(R.id.imageButtonPicturePlay);
            imageButtonPicturePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //事件统计
                    MobclickAgent.onEvent(PictureFragment.this.getActivity(), "picture_sound");
                    MediaPlayerSingleton.getInstance().play(PictureFragment.this.getActivity(), picture.getSoundPath());
                }
            });

			return rootView;
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();
			if (bitmap != null && !bitmap.isRecycled())
			{
				bitmap.recycle();
				System.gc();
			}
		}

		public void onResume() {
			super.onResume();
			MobclickAgent.onPageStart(this.getClass().getSimpleName());
		}
		
		public void onPause() {
			super.onPause();
			MobclickAgent.onPageEnd(this.getClass().getSimpleName());
		}
		
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
