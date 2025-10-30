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
        if ("UDLR".indexOf(direction) >= 0) {
            this.direction = direction;
        } else {
            System.out.println("Invalid direction");
        }
    }

    private void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    private void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    void move() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
        this.moveBetweenBorders();
    }

    private void moveBetweenBorders() {
        if (getX() > GameDimensions.BOARD_WIDTH) {
            setX(0);
        }
        if (getX() < 0) {
            setX(GameDimensions.BOARD_WIDTH);
        }
        if (getY() > GameDimensions.BOARD_HEIGHT) {
            setY(0);
        }
        if (getY() < 0) {
            setY(GameDimensions.BOARD_HEIGHT);
        }
    }

    void updateDirection(char direction) {
        setDirection(direction);
        updateVelocity();
    }

    private void updateVelocity() {
        switch (this.direction) {
            case 'U' -> {
                setVelocityX(0);
                setVelocityY(-8);
            }
            case 'D' -> {
                setVelocityX(0);
                setVelocityY(8);
            }
            case 'L' -> {
                setVelocityX(-8);
                setVelocityY(0);
            }
            case 'R' -> {
                setVelocityX(8);
                setVelocityY(0);
            }
        }
    }

    void setlPositionAndDirection(int x, int y, char direction) {
        setX(x);
        setY(y);
        updateDirection(direction);
    }

    void reset() {
        super.setX(this.startX);
        super.setY(this.startY);
        setDirection('R');
        setVelocityX(0);
        setVelocityY(0);
    }
}
