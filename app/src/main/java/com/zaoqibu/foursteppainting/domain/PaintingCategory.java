package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zaoqibu.foursteppainting.GridViewDataItem;

public class PaintingCategory implements Serializable, GridViewDataItem
{
	private static final long serialVersionUID = -9205263284164026171L;

    private Paintings paintings;
    private String name;
    private String codeName;
    private String backgroundColor;

    public PaintingCategory(String name, String codeName, String backgroundColor) {
        this.name = name;
        this.codeName = codeName;
        this.backgroundColor = backgroundColor;
    }

    public void setPaintings(Paintings paintings) {
        this.paintings = paintings;
    }

	public Painting get(int index) {
		return paintings.get(index);
	}
	
	public int count() {
		return paintings.count();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIcon() {
		return String.format("%s/%s.png", codeName, codeName);
	}

	@Override
	public String getSoundPath() {
		return String.format("%s/%s.mp3", codeName, codeName);
	}

    public String getCodeName() {
        return codeName;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

}
