package com.sirpomidor.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Dmitry on 09.04.2016.
 */
public class DropElement {

    private int x;
    private int y;
    private int width;
    private int height;
    private Texture texture;
    // TODO: 09.04.2016 Добавить состояние хороший или плохой

    public DropElement(int x, int y, int height, int width, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.height = height;
        this.width = width;
    }

    public void dispose() {
        texture.dispose();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    /** @param r the other {@link Rectangle}
     * @return whether this rectangle overlaps the other rectangle. */
    public boolean overlaps (Rectangle r) {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }
}
