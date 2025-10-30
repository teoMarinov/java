package org.example;

import java.util.HashSet;

public class GameUtils {
    private GameUtils() {
    }

    @SafeVarargs
    static void moveCharacter(Character c, HashSet<Tile>... allObstacles) {
        int prevCharacterX = c.getX();
        int prevCharacterY = c.getY();
        char prevCharacterDirection = c.getDirection();

        c.move();
        for (HashSet<Tile> obstacles : allObstacles) {
            for (Tile obstacle : obstacles) {
                if (checkCollision(c, obstacle)) {
                    c.setlPositionAndDirection(prevCharacterX, prevCharacterY, prevCharacterDirection);
                    break;
                }
            }
        }
    }

    @SafeVarargs
    static void changeToViableDirection(Character c, char direction, HashSet<Tile>... allObstacles) {
        int prevCharacterX = c.getX();
        int prevCharacterY = c.getY();
        char prevCharacterDirection = c.getDirection();

        c.updateDirection(direction);
        c.move();
        for (HashSet<Tile> obstacles : allObstacles) {
            for (GameObject obstacle : obstacles) {
                if (checkCollision(c, obstacle)) {
                    c.setlPositionAndDirection(prevCharacterX, prevCharacterY, prevCharacterDirection);
                    break;
                }
            }
        }
        c.setlPositionAndDirection(prevCharacterX, prevCharacterY, c.getDirection());
    }

    static boolean checkCollision(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }
}
