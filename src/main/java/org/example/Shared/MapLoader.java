package org.example.Shared;

import org.example.Constants.GameDimensions;
import org.example.Constants.GameMap;
import org.example.Entities.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MapLoader {
    private static MapLoader INSTANCE;

    String[] gameMap = GameMap.levels[0];

    private final Image wallImage;
    private final Image cherryImage;

    private final Image blueGhostImage;
    private final Image orangeGhostImage;
    private final Image pinkGhostImage;
    private final Image redGhostImage;

    private final Set<Tile> walls = new HashSet<>();
    private final HashSet<Tile> foods = new HashSet<>();
    private final HashSet<Ghost> ghosts = new HashSet<>();
    private Player player;

    private MapLoader(Image wallImage, Image cherryImage, Image blueGhostImage, Image orangeGhostImage, Image pinkGhostImage, Image redGhostImage) {
        this.wallImage = wallImage;
        this.cherryImage = cherryImage;
        this.blueGhostImage = blueGhostImage;
        this.orangeGhostImage = orangeGhostImage;
        this.pinkGhostImage = pinkGhostImage;
        this.redGhostImage = redGhostImage;

        loadMap();
    }

    public static synchronized void init(Image wallImage, Image cherryImage, Image blueGhostImage, Image orangeGhostImage, Image pinkGhostImage, Image redGhostImage) {
        if (INSTANCE == null) {
            INSTANCE = new MapLoader(wallImage, cherryImage, blueGhostImage, orangeGhostImage, pinkGhostImage, redGhostImage);
        } else {
            throw new IllegalStateException("MapLoader already initialized");
        }
    }

    public static MapLoader getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("MapLoader not initialized. Call init(...) first.");
        }
        return INSTANCE;
    }

    private void loadMap() {
        for (int r = 0; r < GameDimensions.ROWS; r++) {
            for (int c = 0; c < GameDimensions.COLUMNS; c++) {
                char tileMapChar = gameMap[r].charAt(c);

                int x = c * GameDimensions.TILE_SIZE;
                int y = r * GameDimensions.TILE_SIZE;

                switch (tileMapChar) {
                    case 'X' -> {
                        Tile wall = new Tile(x, y, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, wallImage, 0);
                        walls.add(wall);
                    }
                    case 'b' -> {
                        Ghost blue = new Ghost(x, y, blueGhostImage);
                        ghosts.add(blue);
                    }
                    case 'o' -> {
                        Ghost orange = new Ghost(x, y, orangeGhostImage);
                        ghosts.add(orange);
                    }
                    case 'p' -> {
                        Ghost pink = new Ghost(x, y, pinkGhostImage);
                        ghosts.add(pink);
                    }
                    case 'r' -> {
                        Ghost red = new Ghost(x, y, redGhostImage);
                        ghosts.add(red);
                    }
                    case 'P' -> player = new Pacman(x, y);
                    case 'c' -> {
                        Tile cherry = new Cherry(x, y, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, cherryImage, 100);
                        foods.add(cherry);
                    }
                    case ' ' -> {
                        int foodPositionX = x + 14;
                        int foodPositionY = y + 14;
                        int foodWidth = 4;
                        int foodHeight = 4;
                        Tile food = new Food(foodPositionX, foodPositionY, foodWidth, foodHeight, 10);
                        foods.add(food);
                    }
                }
            }
        }
    }

    public Set<Tile> getWalls() {
        return walls;
    }

    public Set<Tile> getFoods() {
        return foods;
    }

    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    public Player getPlayer() {
        return player;
    }

    public void changeLevel(int level) {
        if (level > GameMap.levels.length) {
            throw new ArrayIndexOutOfBoundsException(level);
        }
        this.gameMap = GameMap.levels[level - 1];
        walls.clear();
        foods.clear();
        ghosts.clear();
        player = null;
        loadMap();
    }

    public void resetLevel() {
        walls.clear();
        foods.clear();
        ghosts.clear();
        player = null;
        loadMap();
    }
}
