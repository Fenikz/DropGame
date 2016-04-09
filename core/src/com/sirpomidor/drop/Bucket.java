package com.sirpomidor.drop;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Dmitry on 09.04.2016.
 */
public class Bucket implements Collapseable {
    private int x;
    private int y;
    private int width;
    private int height;
    private Texture texture;

    public Bucket(int x, int y, int width, int height, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getX() {
        return x;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }
}
