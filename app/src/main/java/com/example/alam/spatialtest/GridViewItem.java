package com.example.alam.spatialtest;

/**
 * Created by alam on 1/11/17.
 */

public class GridViewItem    {
    private int image;
    private boolean isHighLight;
    private boolean isWrongHighLight;
    private boolean isCorrectHighLight;

    public GridViewItem() {
        // empty constructor
    }

    public GridViewItem(int image, boolean is) {
        this.image = image;
        this.isHighLight = is;
    }

    public boolean isHighLight() {
        return isHighLight;
    }

    /**
     * set if item is highlight or not
     */
    public void setHighLight(boolean isHighLight) {
        this.isHighLight = isHighLight;
    }
    public boolean isCorrectHighLight() {
        return isCorrectHighLight;
    }

    /**
     * set if item is highlight or not
     */
    public void setCorrectHighLight(boolean isCorrectHighLight) {
        this.isCorrectHighLight = isCorrectHighLight;
    }
    public boolean isWrongHighLight() {
        return isWrongHighLight;
    }

    /**
     * set if item is highlight or not
     */
    public void setWrongHighLight(boolean isWrongHighLight) {
        this.isWrongHighLight = isWrongHighLight;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
