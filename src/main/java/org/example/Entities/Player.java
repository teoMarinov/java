package org.example.Entities;

import java.awt.*;

public class Player extends Character {
    private int score = 0;
    private int lives = 3;
    private char nextDirection;

    public Player(int x, int y, Image image) {
        super(x, y, image);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    @Override
    public void move() {
        super.updateDirection(this.nextDirection);
        super.move();
    }

    @Override
    public void updateDirection(char direction) {
        this.nextDirection = direction;
    }
}
