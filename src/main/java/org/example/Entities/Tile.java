package org.example.Entities;

import java.awt.*;

public class Tile extends GameObject {
    private final Image image;

    public Tile(int x, int y, int width, int height, Image image) {
        super(x, y, width, height);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
