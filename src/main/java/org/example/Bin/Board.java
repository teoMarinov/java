package org.example.Bin;

import org.example.Constants.GameDimensions;
import org.example.Entities.Character;
import org.example.Entities.Player;
import org.example.Entities.Tile;
import org.example.Utils.GameUtils;
import org.example.Utils.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Set;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final GameUtils gameUtils;

    private final Set<Tile> walls;
    private final Set<Tile> foods;
    private final Set<org.example.Entities.Character> ghosts;
    private final Player player;

    private final Timer gameLoop;
    private final char[] directions = {'U', 'D', 'R', 'L'}; // up, down, left, right
    private final Random random = new Random();

    private int score = 0;
    private int lives = 3;
    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean gamePaused = false;

    Board(Dimension size, Color color, MapLoader mapLoader, GameUtils gameUtils) {
        setPreferredSize(size);
        setBackground(color);
        addKeyListener(this);
        setFocusable(true);

        this.walls = mapLoader.getWalls();
        this.foods = mapLoader.getFoods();
        this.ghosts = mapLoader.getGhosts();
        this.player = mapLoader.getPlayer();

        this.gameUtils = gameUtils;

        for (org.example.Entities.Character ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            this.gameUtils.changeToViableDirection(ghost, newDirection, walls);
        }
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);

        for (org.example.Entities.Character ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight(), null);
        }

        for (Tile wall : walls) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
        }

        g.setColor(Color.WHITE);
        for (Tile food : foods) {
            g.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight());
        }
        //score
        int halfTileSize = GameDimensions.TILE_SIZE / 2;
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);

        int textHeight = fm.getAscent();

        int y = (GameDimensions.BOARD_HEIGHT + textHeight) / 2;

        if (gameLost) {
            String text = "Game Over: " + score;

            int textWidth = fm.stringWidth(text);
            int x = (GameDimensions.BOARD_WIDTH - textWidth) / 2;

            g.setColor(Color.RED);
            g.drawString(text, x, y);
        } else if (gameWon) {
            String text = "Good Game!";

            int textWidth = fm.stringWidth(text);
            int x = (GameDimensions.BOARD_WIDTH - textWidth) / 2;

            g.setColor(Color.GREEN);
            g.drawString(text, x, y);
        } else if (gamePaused) {
            String text = "PAUSED!";

            int textWidth = fm.stringWidth(text);
            int x = (GameDimensions.BOARD_WIDTH - textWidth) / 2;

            g.setColor(Color.WHITE);
            g.drawString(text, x, y);
        } else {
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("x" + lives + " Score: " + score, halfTileSize, halfTileSize);
        }
    }

    private void resetPositions() {
        player.reset();

        for (org.example.Entities.Character ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.setDirection(newDirection);
        }
    }

    private void checkForCollisions() {
        for (org.example.Entities.Character ghost : ghosts) {
            if (this.gameUtils.checkCollision(player, ghost)) {
                lives -= 1;
                if (lives == 0) {
                    setGameLost(true);
                    return;
                }

                resetPositions();
            }
        }

        for (Tile food : foods) {
            if (this.gameUtils.checkCollision(player, food)) {
                foods.remove(food);
                score += 10;
                break;
            }
        }

        if (foods.isEmpty()) {
            setGameWon(true);
        }
    }

    private void moveGhosts() {
        for (Character ghost : ghosts) {
            for (Tile wall : walls) {
                // TODO: Fix move ghost logic, now it stops at a wall.
                if (this.gameUtils.checkCollision(ghost, wall)) {
                    char newDirection = directions[random.nextInt(4)];
                    System.out.println("Changing direction: " + newDirection);
                    this.gameUtils.changeToViableDirection(ghost, newDirection, walls);
                }
                this.gameUtils.moveCharacter(ghost, walls);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gamePaused) {
            gameLoop.stop();
        }

        this.gameUtils.moveCharacter(player, walls);
        moveGhosts();
        checkForCollisions();
        repaint();
        if (gameLost || gameWon) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameLost || gameWon) {
//            loadMap();
            player.reset();
            lives = 3;
            score = 0;
            gameLost = false;
            gameWon = false;
            gamePaused = false;
            gameLoop.start();
        }

        if (!gameLoop.isRunning()) {
            gameLoop.start();
            setGamePaused(false);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> this.gameUtils.changeToViableDirection(player, 'U', walls);
            case KeyEvent.VK_DOWN -> this.gameUtils.changeToViableDirection(player, 'D', walls);
            case KeyEvent.VK_LEFT -> this.gameUtils.changeToViableDirection(player, 'L', walls);
            case KeyEvent.VK_RIGHT -> this.gameUtils.changeToViableDirection(player, 'R', walls);
            default -> gamePaused = true;
        }
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
}
