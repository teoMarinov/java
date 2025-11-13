package org.example.Entities;

import java.awt.*;

public class Tile extends GameObject {
    private final Image image;
    private final int score;

    public Tile(int x, int y, int width, int height, Image image, int score) {
        super(x, y, width, height);
        this.image = image;
        this.score = score;
    }

    public Image getImage() {
        return image;
    }

    public int getScore() {
        return score;
    }
}
