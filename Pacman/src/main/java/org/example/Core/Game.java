package org.example.Core;

import org.example.Constants.GameDimensions;
import javax.swing.JFrame;
import java.awt.*;

public class Game {
    public Game() {
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
