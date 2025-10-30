package org.example;

import javax.swing.JFrame;

public class Game {
    void run() {
        JFrame frame = new JFrame("Pac Man");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT);

        Board pacmanGame = new Board();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
