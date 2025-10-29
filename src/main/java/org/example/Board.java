package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Board extends JPanel implements ActionListener, KeyListener {
    private Tile tile;

    private final Image wallImage;
    private final Image blueGhostImage;
    private final Image orangeGhostImage;
    private final Image pinkGhostImage;
    private final Image redGhostImage;

    private final Image pacmanUpImage;
    private final Image pacmanDownImage;
    private final Image pacmanLeftImage;
    private final Image pacmanRightImage;

    private final String[] tileMap = GameMap.levelOne;

    private final HashSet<Tile> walls = new HashSet<Tile>();
    private final HashSet<Tile> foods = new HashSet<Tile>();
    private final HashSet<Character> ghosts = new HashSet<Character>();
    private Player player;

    private final Timer gameLoop;
    private final char[] directions = {'U', 'D', 'R', 'L'}; // up, down, left, right
    private final Random random = new Random();

    private int score = 0;
    private int lives = 3;
    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean gamePaused = false;

    Board() {
        setPreferredSize(new Dimension(GameDimensions.BOARD_WIDTH, GameDimensions.BOARD_HEIGHT));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        wallImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/wall.png"))).getImage();
        blueGhostImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/blueGhost.png"))).getImage();
        orangeGhostImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/orangeGhost.png"))).getImage();
        pinkGhostImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pinkGhost.png"))).getImage();
        redGhostImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/redGhost.png"))).getImage();

        pacmanUpImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacmanUp.png"))).getImage();
        pacmanDownImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacmanDown.png"))).getImage();
        pacmanLeftImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacmanLeft.png"))).getImage();
        pacmanRightImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacmanRight.png"))).getImage();

        loadMap();
        for (Character ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            GameUtils.changeToViableDirection(ghost, newDirection, walls);
        }
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }

    private void loadMap() {
        for (int r = 0; r < GameDimensions.ROWS; r++) {
            for (int c = 0; c < GameDimensions.COLUMNS; c++) {
                char tileMapChar = tileMap[r].charAt(c);

                int x = c * GameDimensions.TILE_SIZE;
                int y = r * GameDimensions.TILE_SIZE;

                switch (tileMapChar) {
                    case 'X':
                        Tile wall = new Tile(x, y, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, wallImage);
                        walls.add(wall);
                        break;
                    case 'b':
                        Character blue = new Character(x, y, blueGhostImage);
                        ghosts.add(blue);
                        break;
                    case 'o':
                        Character orange = new Character(x, y, orangeGhostImage);
                        ghosts.add(orange);
                        break;
                    case 'p':
                        Character pink = new Character(x, y, pinkGhostImage);
                        ghosts.add(pink);
                        break;
                    case 'r':
                        Character red = new Character(x, y, redGhostImage);
                        ghosts.add(red);
                        break;
                    case 'P':
                        player = new Player(x, y, pacmanRightImage);
                        break;
                    case ' ':
                        int foodPositionX = x + 14;
                        int foodPositionY = y + 14;
                        int foodWidth = 4;
                        int foodHeight = 4;
                        Tile food = new Tile(foodPositionX, foodPositionY, foodWidth, foodHeight, null);
                        foods.add(food);
                        break;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(player.getImage(), player.x, player.y, player.width, player.height, null);

        for (Character ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Tile wall : walls) {
            g.drawImage(wall.getImage(), wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.WHITE);
        for (Tile food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
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
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), halfTileSize, halfTileSize);
        }
    }

    private void resetPositions() {
        player.reset();
        player.setVelocityX(0);
        player.setVelocityY(0);

        for (Character ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.setDirection(newDirection);
        }
    }

    private void checkForCollisions() {
        for (Character ghost : ghosts) {
            if (GameUtils.checkCollision(player, ghost)) {
                lives -= 1;
                if (lives == 0) {
                    setGameLost(true);
                    return;
                }

                resetPositions();
            }
        }

        for (Tile food : foods) {
            if (GameUtils.checkCollision(player, food)) {
                foods.remove(food);
                score += 10;
                break;
            }
        }

        if (foods.isEmpty()) {
            setGameLost(true);
        }
    }

    private void moveGhosts() {
        for (Character ghost : ghosts) {
            for (Tile wall : walls) {
                // TODO: Fix move ghost logic, now it stops at a wall.
                if (GameUtils.checkCollision(ghost, wall)) {
                    char newDirection = directions[random.nextInt(4)];
                    System.out.println("Changing direction: " +  newDirection );
                    GameUtils.changeToViableDirection(ghost, newDirection, walls);
                }
                GameUtils.moveCharacter(ghost, walls);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gamePaused) {
            gameLoop.stop();
        }

        GameUtils.moveCharacter(player, walls);
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
            loadMap();
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

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            GameUtils.changeToViableDirection(player, 'U', walls);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            GameUtils.changeToViableDirection(player, 'D', walls);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            GameUtils.changeToViableDirection(player, 'L', walls);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            GameUtils.changeToViableDirection(player, 'R', walls);
        } else {
            gamePaused = true;
        }

        if (player.getDirection() == 'U') {
            player.setImage(pacmanUpImage);
        } else if (player.getDirection() == 'D') {
            player.setImage(pacmanDownImage);
        } else if (player.getDirection() == 'L') {
            player.setImage(pacmanLeftImage);
        } else if (player.getDirection() == 'R') {
            player.setImage(pacmanRightImage);
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
