package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zaoqibu.foursteppainting.GridViewDataItem;

public class Painting implements Serializable, GridViewDataItem
{
	private static final long serialVersionUID = 3970057122837909051L;

	private int name;
	private int sound;
	private List<Picture> pictures = new ArrayList<Picture>();
	private String tag;

	public Painting(int name, int sound, String tag)
	{
		this.name = name;
		this.sound = sound;
		this.tag = tag;
	}

	public void add(Picture picture)
	{
		pictures.add(picture);
	}
	
	public Picture get(int index)
	{
		return pictures.get(index);
	}
	
	public int count()
	{
		return pictures.size();
	}
	
	@Override
	public int getName() {
		return name;
	}
	
	@Override
	public int getIcon() 
	{
		return pictures.get(count()-1).getDrawable();
	}

	@Override
	public int getSound() {
		return sound;
	}
	
	public String getTag() {
		return tag;
	}

}
