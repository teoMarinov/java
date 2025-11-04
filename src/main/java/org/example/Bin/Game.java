package org.example.Bin;

import org.example.Constants.GameDimensions;
import org.example.Constants.GameImagePaths;
import org.example.Constants.GameMap;
import org.example.Utils.GameUtils;
import org.example.Utils.ImageLoader;
import org.example.Utils.MapLoader;

import javax.swing.JFrame;
import java.awt.*;

public class Game {
    private final ImageLoader imageLoader = ImageLoader.getInstance();
    private final GameUtils gameUtils = GameUtils.getInstance();

    private Board getBoard(Dimension boardSize) {
        Color backgroundColor = Color.black;

        Image wallImage = imageLoader.load(GameImagePaths.WALL);
        Image blueGhostImage = imageLoader.load(GameImagePaths.GHOST_BLUE);
        Image orangeGhostImage = imageLoader.load(GameImagePaths.GHOST_ORANGE);
        Image pinkGhostImage = imageLoader.load(GameImagePaths.GHOST_PINK);
        Image redGhostImage = imageLoader.load(GameImagePaths.GHOST_RED);
        String[] tileMap = GameMap.levelOne;

        MapLoader mapLoader = MapLoader.init(
                tileMap,
                wallImage,
                blueGhostImage,
                orangeGhostImage,
                pinkGhostImage,
                redGhostImage,
                imageLoader
        );

        return new Board(boardSize, backgroundColor, mapLoader, gameUtils);
    }

    public void run() {
        JFrame frame = new JFrame("Pac Man");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT);

        Dimension boardSize = new Dimension(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT);
        Board pacmanGame = getBoard(boardSize);
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
