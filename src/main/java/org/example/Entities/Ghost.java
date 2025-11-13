package org.example.Entities;

import org.example.Constants.GameDimensions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Character {
    public Ghost(int x, int y, Image image) {
        super(x, y, image);
        char[] directions = {'R', 'L', 'U', 'D'};
        setDirection(directions[new Random().nextInt(4)]);
    }

    @Override
    public void move() {
        if (!isOutsideTheMap()) {
            chooseDirection();
        }
        super.move();
    }

    private void chooseDirection() {
        ArrayList<java.lang.Character> viableDirections = new ArrayList<>();
        char currentDirection = this.getDirection();

        super.move();
        if (isMoveViable()) {
            viableDirections.add(currentDirection);
            this.revertMove();
        }

        if (currentDirection == 'L' || currentDirection == 'R') {
            this.setDirection('U');
            super.updateVelocity();
            super.move();
            if (isMoveViable()) {
                viableDirections.add('U');
                this.revertMove();
            }

            this.setDirection('D');
            super.updateVelocity();
            super.move();
            if (isMoveViable()) {
                viableDirections.add('D');
                this.revertMove();
            }
        }
        if (currentDirection == 'U' || currentDirection == 'D') {
            this.setDirection('L');
            super.updateVelocity();
            super.move();
            if (isMoveViable()) {
                viableDirections.add('L');
                this.revertMove();
            }

            this.setDirection('R');
            super.updateVelocity();
            super.move();
            if (isMoveViable()) {
                viableDirections.add('R');
                this.revertMove();
            }
        }

        if (viableDirections.isEmpty()) {
            return;
        }

        char randomDirection = viableDirections.get(new Random().nextInt(viableDirections.size()));
        this.setDirection(randomDirection);
        super.updateVelocity();
    }

//    private boolean detectPlayer () {
//        MapLoader mapLoader = MapLoader.getInstance();
//        Set<Tile> walls = mapLoader.getWalls();
//        Player pacman = mapLoader.getPlayer();
//
//        // Check if the ghost x and the player x are equal
//        // If they match that means they align vertically
//        // Get both player's Ys
//        // Check if there are any walls with matching X and y between the two
//        // If yes, then do nothing
//        // If no change the ghost's direction to move towards the player.
//        // Additional
//        if (this.getX() == pacman.getX()) {
//
//        }
//    };

    private boolean isMoveViable() {
        return this.getPrevX() != this.getX() || this.getPrevY() != this.getY();
    }

    private boolean isOutsideTheMap() {
        return this.getY() < 0 || this.getY() + this.getHeight() > GameDimensions.BOARD_HEIGHT || this.getX() < 0 || this.getX() + this.getWidth() > GameDimensions.BOARD_WIDTH;
    }
}
