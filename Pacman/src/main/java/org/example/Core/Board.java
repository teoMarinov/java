package org.example.Core;

import org.example.Constants.GameDimensions;
import org.example.Entities.*;
import org.example.Entities.Character;
import org.example.Shared.GameUtils;
import org.example.Shared.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final MapLoader mapLoader;

    private final Set<Tile> walls;
    private final Set<Tile> foods;
    private final Set<Ghost> ghosts;
    private final Player player;

    private final Timer gameLoop;


    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean gamePaused = false;

    Board(Dimension size, Color color) {
        setPreferredSize(size);
        setBackground(color);
        addKeyListener(this);
        setFocusable(true);

        this.mapLoader = MapLoader.getInstance();
        this.walls = this.mapLoader.getWalls();
        this.foods = this.mapLoader.getFoods();
        this.ghosts = this.mapLoader.getGhosts();
        this.player = this.mapLoader.getPlayer();

        gameLoop = new Timer(50, this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);

        for (Character ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight(), null);
        }

        for (Tile wall : walls) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
        }

        g.setColor(Color.WHITE);
        for (Tile food : foods) {
            if (food instanceof Cherry) {
                g.drawImage(food.getImage(), food.getX(), food.getY(), food.getWidth(), food.getHeight(), null);
            } else {
                g.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight());
            }
        }
        //score
        int halfTileSize = GameDimensions.TILE_SIZE / 2;
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);

        int textHeight = fm.getAscent();

        int y = (GameDimensions.BOARD_HEIGHT + textHeight) / 2;

        if (gameLost) {
            String text = "Game Over: " + player.getScore();

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
            g.drawString("x" + player.getLives() + " Score: " + player.getScore(), halfTileSize, halfTileSize);
        }
    }

    private void resetPositions() {
        player.reset();

        for (Character ghost : ghosts) {
            ghost.reset();
        }
    }

    private void checkForCollisions() {
        for (Character ghost : ghosts) {
            if (GameUtils.checkCollision(player, ghost)) {
                player.decreaseLives();
                if (player.getLives() == 0) {
                    setGameLost(true);
                    return;
                }

                resetPositions();
            }
        }

        for (Tile food : foods) {
            if (GameUtils.checkCollision(player, food)) {
                foods.remove(food);
                player.increaseScore(food.getScore());
                if (food instanceof Cherry && player.getLives() < 3) {
                    player.increaseLives();
                }
                break;
            }
        }

        if (foods.isEmpty()) {
            setGameWon(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gamePaused) {
            gameLoop.stop();
        }

        player.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
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
            player.reset();
            player.setLives(3);
            player.setScore(0);
            gameLost = false;
            gameWon = false;
            gamePaused = false;
            gameLoop.start();
        }

        if (gameLost) {
            mapLoader.resetLevel();
        }
        if (gameWon) {
            mapLoader.changeLevel(2);
        }

        if (!gameLoop.isRunning()) {
            gameLoop.start();
            setGamePaused(false);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> player.updateDirection('U');
            case KeyEvent.VK_DOWN -> player.updateDirection('D');
            case KeyEvent.VK_LEFT -> player.updateDirection('L');
            case KeyEvent.VK_RIGHT -> player.updateDirection('R');
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
