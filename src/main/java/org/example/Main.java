package org.example;

import javax.swing.JFrame;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {
        final int ROWS = 21;
        final int COLUMNS = 19;
        final int TILE_SIZE = 32;
        final int BOARD_WIDTH = COLUMNS * TILE_SIZE;
        final int BOARD_HEIGHT = ROWS * TILE_SIZE;

        JFrame frame = new JFrame("Pac Man");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);

        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.pack();
        pacManGame.requestFocus();
        frame.setVisible(true);
    }
}
