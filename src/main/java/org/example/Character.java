package org.example;

import java.awt.*;

public class Character extends GameObject {
    private Image image;

    private final int startX;
    private final int startY;

    private char direction = 'U'; // U D L R

    private int velocityX = 0;
    private int velocityY = 0;

    public Character(int x, int y, Image image) {
        super(x, y, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE);
        this.startX = x;
        this.startY = y;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public char getDirection() {
        return direction;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        char prevDirection = this.direction;
        this.velocityY = velocityY;
    }

    void move() {
        this.x += this.velocityX;
        this.y += this.velocityY;
        this.moveBetweenBorders();
    }

    private void moveBetweenBorders() {
        if (this.x > GameDimensions.BOARD_WIDTH) {
            this.x = 0;
        }
        if (this.x < 0) {
            this.x = GameDimensions.BOARD_WIDTH;
        }
        if (this.y > GameDimensions.BOARD_HEIGHT) {
            this.y = 0;
        }
        if (this.y < 0) {
            this.y = GameDimensions.BOARD_HEIGHT;
        }
    }

    void updateDirection(char direction) {
        setDirection(direction);
        updateVelocity();
    }

    private void updateVelocity() {
        if (this.direction == 'U') {
            setVelocityX(0);
            setVelocityY(-8);
        } else if (this.direction == 'D') {
            setVelocityX(0);
            setVelocityY(8);
        } else if (this.direction == 'L') {
            setVelocityX(-8);
            setVelocityY(0);
        } else if (this.direction == 'R') {
            setVelocityX(8);
            setVelocityY(0);
        }
    }

    void revertMove(int x, int y, char direction) {
        setX(x);
        setY(y);
        updateDirection(direction);
    }

    void reset() {
        super.setX(this.startX);
        super.setY(this.startY);
    }

}
