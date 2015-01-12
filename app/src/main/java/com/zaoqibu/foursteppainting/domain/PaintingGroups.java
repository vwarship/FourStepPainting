package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.List;

public class PaintingGroups implements Serializable
{
	private static final long serialVersionUID = -8331458682445234717L;
	
	private List<PaintingGroup> paintingGroups;

	public void setDataSource(List<PaintingGroup> paintingGroups)
	{
		this.paintingGroups = paintingGroups;
	}
	
	public int count() {
		return paintingGroups.size();
	}

	public PaintingGroup get(int index) {
		return paintingGroups.get(index);
	}

}
