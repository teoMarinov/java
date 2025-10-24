package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    public class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        char direction = 'U'; // U D L R
        int velocityX = 0;
        int velocityY = 0;

        public Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.startX = x;
            this.startY = y;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -8;
            } else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = 8;
            } else if (this.direction == 'L') {
                this.velocityX = -8;
                this.velocityY = 0;
            } else if (this.direction == 'R') {
                this.velocityX = 8;
                this.velocityY = 0;
            }
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public char getDirection() {
            return this.direction;
        }
    }

    private final int ROWS = 21;
    private final int COLUMNS = 19;
    private final int TILE_SIZE = 32;
    private final int BOARD_WIDTH = COLUMNS * TILE_SIZE;
    private final int BOARD_HEIGHT = ROWS * TILE_SIZE;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameLoop;
    char[] directions = {'U', 'D', 'L', 'R'}; //up down left right
    Random random = new Random();

    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    PacMan() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
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
        for (Block ghost: ghosts) {
            char newDirection = directions[random.nextInt(4)];
        }
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                char tileMapChar = tileMap[r].charAt(c);

                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                switch (tileMapChar) {
                    case 'X':
                        Block wall = new Block(wallImage, x, y, TILE_SIZE, TILE_SIZE);
                        walls.add(wall);
                        break;
                    case 'b':
                        Block ghost = new Block(blueGhostImage, x, y, TILE_SIZE, TILE_SIZE);
                        ghosts.add(ghost);
                        break;
                    case 'o':
                        Block orange = new Block(orangeGhostImage, x, y, TILE_SIZE, TILE_SIZE);
                        ghosts.add(orange);
                        break;
                    case 'p':
                        Block pink = new Block(pinkGhostImage, x, y, TILE_SIZE, TILE_SIZE);
                        ghosts.add(pink);
                        break;
                    case 'r':
                        Block red = new Block(redGhostImage, x, y, TILE_SIZE, TILE_SIZE);
                        ghosts.add(red);
                        break;
                    case 'P':
                        pacman = new Block(pacmanRightImage, x, y, TILE_SIZE, TILE_SIZE);
                        break;
                    case ' ':
                        int foodPositionX = x + 14;
                        int foodPositionY = y + 14;
                        int foodWidth = 4;
                        int foodHeight = 4;
                        Block food = new Block(null, foodPositionX, foodPositionY, foodWidth, foodHeight);
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
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }

    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }
    }

    public boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }

        if (pacman.getDirection() == 'U') {
            pacman.setImage(pacmanUpImage);
        } else if (pacman.getDirection() == 'D') {
            pacman.setImage(pacmanDownImage);
        } else if (pacman.getDirection() == 'L') {
            pacman.setImage(pacmanLeftImage);
        } else if (pacman.getDirection() == 'R') {
            pacman.setImage(pacmanRightImage);
        }
    }
}
