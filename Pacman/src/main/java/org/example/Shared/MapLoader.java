package org.example.Shared;

import org.example.Constants.GameDimensions;
import org.example.Constants.GameImagePaths;
import org.example.Constants.GameMap;
import org.example.Entities.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MapLoader {
    private static final MapLoader INSTANCE = new MapLoader();

    String[] gameMap = GameMap.levels[0];

    private final Image wallImage = ImageLoader.load(GameImagePaths.WALL);
    private final Image cherryImage = ImageLoader.load(GameImagePaths.CHERRY);

    private final Image blueGhostImage = ImageLoader.load(GameImagePaths.GHOST_BLUE);
    private final Image orangeGhostImage = ImageLoader.load(GameImagePaths.GHOST_ORANGE);
    private final Image pinkGhostImage = ImageLoader.load(GameImagePaths.GHOST_PINK);
    private final Image redGhostImage = ImageLoader.load(GameImagePaths.GHOST_RED);

    private final Set<Tile> walls = new HashSet<>();
    private final HashSet<Tile> foods = new HashSet<>();
    private final HashSet<Ghost> ghosts = new HashSet<>();
    private Player player;

    private MapLoader() {
        loadMap();
    }

    public static MapLoader getInstance() {
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
        this.resetLevel();
    }

    public void resetLevel() {
        walls.clear();
        foods.clear();
        ghosts.clear();
        player = null;
        loadMap();
    }
}
