package org.example.Utils;

import org.example.Entities.Character;
import org.example.Entities.GameObject;
import org.example.Entities.Tile;

import java.util.Set;

public final class GameUtils {
    private static final GameUtils INSTANCE = new GameUtils();

    private GameUtils() {
    }

    public static GameUtils getInstance() {
        return INSTANCE;
    }


    @SafeVarargs
    public final void moveCharacter(org.example.Entities.Character c, Set<Tile>... allObstacles) {
        int prevCharacterX = c.getX();
        int prevCharacterY = c.getY();
        char prevCharacterDirection = c.getDirection();

        c.move();
        for (Set<Tile> obstacles : allObstacles) {
            for (Tile obstacle : obstacles) {
                if (checkCollision(c, obstacle)) {
                    c.setlPositionAndDirection(prevCharacterX, prevCharacterY, prevCharacterDirection);
                    break;
                }
            }
        }
    }

    @SafeVarargs
    public final void changeToViableDirection(Character c, char direction, Set<Tile>... allObstacles) {
        int prevCharacterX = c.getX();
        int prevCharacterY = c.getY();
        char prevCharacterDirection = c.getDirection();

        c.updateDirection(direction);
        c.move();
        for (Set<Tile> obstacles : allObstacles) {
            for (GameObject obstacle : obstacles) {
                if (checkCollision(c, obstacle)) {
                    c.setlPositionAndDirection(prevCharacterX, prevCharacterY, prevCharacterDirection);
                    break;
                }
            }
        }
        c.setlPositionAndDirection(prevCharacterX, prevCharacterY, c.getDirection());
    }

    public boolean checkCollision(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }
}
