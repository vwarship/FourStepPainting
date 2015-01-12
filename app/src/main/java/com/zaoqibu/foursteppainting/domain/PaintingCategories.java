package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.List;

public class PaintingCategories implements Serializable
{
	private static final long serialVersionUID = -8331458682445234717L;
	
	private List<PaintingCategory> paintingCategories;

	public void setDataSource(List<PaintingCategory> paintingCategories)
	{
		this.paintingCategories = paintingCategories;
	}
	
	public int count() {
		return paintingCategories.size();
	}

	public PaintingCategory get(int index) {
		return paintingCategories.get(index);
	}

}
