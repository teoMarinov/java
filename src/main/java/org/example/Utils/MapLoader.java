package org.example.Utils;

import org.example.Constants.GameDimensions;
import org.example.Entities.Character;
import org.example.Entities.Packman;
import org.example.Entities.Player;
import org.example.Entities.Tile;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MapLoader {
    private static MapLoader INSTANCE;

    String[] tileMap;

    private final Image wallImage;
    private final Image blueGhostImage;
    private final Image orangeGhostImage;
    private final Image pinkGhostImage;
    private final Image redGhostImage;
    private final ImageLoader imageLoader;

    private final Set<Tile> walls = new HashSet<>();
    private final HashSet<Tile> foods = new HashSet<>();
    private final HashSet<Character> ghosts = new HashSet<>();
    private Player player;

    private MapLoader(String[] tileMap, Image wallImage, Image blueGhostImage, Image orangeGhostImage, Image pinkGhostImage, Image redGhostImage, ImageLoader imageLoader) {
        this.tileMap = tileMap;
        this.wallImage = wallImage;
        this.blueGhostImage = blueGhostImage;
        this.orangeGhostImage = orangeGhostImage;
        this.pinkGhostImage = pinkGhostImage;
        this.redGhostImage = redGhostImage;
        this.imageLoader = imageLoader;

        loadMap();
    }

    public static synchronized MapLoader init(String[] tileMap, Image wallImage, Image blueGhostImage, Image orangeGhostImage, Image pinkGhostImage, Image redGhostImage, ImageLoader imageLoader) {
        if (INSTANCE == null) {
            INSTANCE = new MapLoader(tileMap, wallImage, blueGhostImage, orangeGhostImage, pinkGhostImage, redGhostImage, imageLoader);
        }
        return INSTANCE;
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
                char tileMapChar = tileMap[r].charAt(c);

                int x = c * GameDimensions.TILE_SIZE;
                int y = r * GameDimensions.TILE_SIZE;

                switch (tileMapChar) {
                    case 'X' -> {
                        Tile wall = new Tile(x, y, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, wallImage);
                        walls.add(wall);
                    }
                    case 'b' -> {
                        org.example.Entities.Character blue = new org.example.Entities.Character(x, y, blueGhostImage);
                        ghosts.add(blue);
                    }
                    case 'o' -> {
                        org.example.Entities.Character orange = new org.example.Entities.Character(x, y, orangeGhostImage);
                        ghosts.add(orange);
                    }
                    case 'p' -> {
                        org.example.Entities.Character pink = new org.example.Entities.Character(x, y, pinkGhostImage);
                        ghosts.add(pink);
                    }
                    case 'r' -> {
                        org.example.Entities.Character red = new org.example.Entities.Character(x, y, redGhostImage);
                        ghosts.add(red);
                    }
                    case 'P' -> player = new Packman(x, y, imageLoader);
                    case ' ' -> {
                        int foodPositionX = x + 14;
                        int foodPositionY = y + 14;
                        int foodWidth = 4;
                        int foodHeight = 4;
                        Tile food = new Tile(foodPositionX, foodPositionY, foodWidth, foodHeight, null);
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

    public Set<Character> getGhosts() {
        return ghosts;
    }

    public Player getPlayer() {
        return player;
    }

    public void updateTileMap(String[] newTileMap) {
        this.tileMap = newTileMap;
        walls.clear();
        foods.clear();
        ghosts.clear();
        player = null;
        loadMap();
    }
}
