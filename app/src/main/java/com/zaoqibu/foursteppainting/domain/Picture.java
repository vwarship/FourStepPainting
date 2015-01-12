package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;

public class Picture implements Serializable 
{
	private static final long serialVersionUID = -1946454758821523228L;

	private int order;
	private int drawable;
	private int content;
	private int sound;
	
	public Picture(int order, int drawable, int content, int sound)
	{
		this.order = order;
		this.drawable = drawable;
		this.content = content;
		this.sound = sound;
	}
	
	public int getOrder() {
		return order;
	}
	
	public int getDrawable() {
		return drawable;
	}
	
	public int getContent() {
		return content;
	}

	public int getSound() {
		return sound;
	}
	
}
