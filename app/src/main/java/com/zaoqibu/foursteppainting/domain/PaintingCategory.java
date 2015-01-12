package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zaoqibu.foursteppainting.GridViewDataItem;

public class PaintingCategory implements Serializable, GridViewDataItem
{
	private static final long serialVersionUID = -9205263284164026171L;

	private int name;
	private int icon;
	private int sound;
	
	private List<Painting> paintings = new ArrayList<Painting>();
	
	public PaintingCategory(int name, int icon, int sound)
	{
		this.name = name;
		this.icon = icon;
		this.sound = sound;
	}
	
	public void add(Painting painting)
	{
		paintings.add(painting);
	}
	
	public Painting get(int index)
	{
		return paintings.get(index);
	}
	
	public int count()
	{
		return paintings.size();
	}

	@Override
	public int getName() {
		return name;
	}

	@Override
	public int getIcon() {
		return icon;
	}

	@Override
	public int getSound() {
		return sound;
	}

}
