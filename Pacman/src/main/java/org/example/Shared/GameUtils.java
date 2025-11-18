package org.example.Shared;

import org.example.Entities.GameObject;

public final class GameUtils {
    private GameUtils() {
    }

    public static boolean checkCollision(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }


}
