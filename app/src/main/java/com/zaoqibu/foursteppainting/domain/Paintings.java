package com.zaoqibu.foursteppainting.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vwarship on 2015/1/16.
 */
public class Paintings implements Serializable {
    private List<Painting> paintings = new ArrayList<Painting>();

    public void add(Painting painting) {
        paintings.add(painting);
    }

    public Painting get(int index) {
        return paintings.get(index);
    }

    public int count() {
        return paintings.size();
    }

}
