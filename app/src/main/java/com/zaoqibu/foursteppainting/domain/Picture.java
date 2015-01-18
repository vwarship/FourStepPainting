package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;

public class Picture implements Serializable 
{
	private static final long serialVersionUID = -1946454758821523228L;

	private int order;
	private String imagePath;
	private String content;
	private String soundPath;
	
	public Picture(int order, String imagePath, String content, String soundPath)
	{
		this.order = order;
		this.imagePath = imagePath;
		this.content = content;
		this.soundPath = soundPath;
	}
	
	public int getOrder() {
		return order;
	}

    public String getImagePath() {
        return imagePath;
    }

    public String getContent() {
        return content;
    }

    public String getSoundPath() {
        return soundPath;
    }
}
