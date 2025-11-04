//package org.example;
//
//import javax.swing.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameTest {
//
//    void run_shouldCreateBoardAndFrame() {
//        Game game = new Game();
//
//        SwingUtilities.invokeLater(game::run);
//
//        Board board = new Board();
//        assertNotNull(board);
//        assertEquals(GameDimensions.BOARD_WIDTH, board.getWidth());
//        assertEquals(GameDimensions.BOARD_HEIGHT, board.getHeight());
//    }
//}