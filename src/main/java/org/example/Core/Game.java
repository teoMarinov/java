package org.example.Core;

import org.example.Constants.GameDimensions;
import org.example.Constants.GameImagePaths;
import org.example.Shared.ImageLoader;
import org.example.Shared.MapLoader;

import javax.swing.JFrame;
import java.awt.*;

public class Game {
    public Game() {
        Image wallImage = ImageLoader.load(GameImagePaths.WALL);
        Image blueGhostImage = ImageLoader.load(GameImagePaths.GHOST_BLUE);
        Image orangeGhostImage = ImageLoader.load(GameImagePaths.GHOST_ORANGE);
        Image pinkGhostImage = ImageLoader.load(GameImagePaths.GHOST_PINK);
        Image redGhostImage = ImageLoader.load(GameImagePaths.GHOST_RED);
        MapLoader.init(
                wallImage,
                blueGhostImage,
                orangeGhostImage,
                pinkGhostImage,
                redGhostImage
        );
    }

    public void run() {
        JFrame frame = new JFrame("Pac Man");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT);

        Dimension boardSize = new Dimension(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT);
        Board pacmanGame = new Board(boardSize, Color.BLACK);
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
