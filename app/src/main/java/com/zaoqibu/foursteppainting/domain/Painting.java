package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zaoqibu.foursteppainting.GridViewDataItem;

public class Painting implements Serializable, GridViewDataItem
{
	private static final long serialVersionUID = 3970057122837909051L;

    private List<Picture> pictures = new ArrayList<Picture>();
    private String name;
    private String codeName;
    private String sound;

	public Painting(String name, String codeName, String sound) {
        this.name = name;
        this.codeName = codeName;
        this.sound = sound;
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
	public String getName() {
		return name;
	}
	
	@Override
	public String getIcon() {
		return pictures.get(count()-1).getImagePath();
	}

	@Override
	public String getSoundPath() {
		return sound;
	}

    public String getCodeName() {
        return codeName;
    }

}
