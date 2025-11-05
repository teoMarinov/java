package org.example.Entities;

import org.example.Constants.GameImagePaths;
import org.example.Shared.ImageLoader;

import java.awt.*;

public class Pacman extends Player {
    private final Image pacmanRightImage, pacmanLeftImage, pacmanDownImage, pacmanUpImage;

    public Pacman(int x, int y) {
        super(x, y, ImageLoader.load(GameImagePaths.PACMAN_RIGHT));

        pacmanRightImage = ImageLoader.load(GameImagePaths.PACMAN_RIGHT);
        pacmanLeftImage = ImageLoader.load(GameImagePaths.PACMAN_LEFT);
        pacmanDownImage = ImageLoader.load(GameImagePaths.PACMAN_DOWN);
        pacmanUpImage = ImageLoader.load(GameImagePaths.PACMAN_UP);
    }


    @Override
    public void updateDirection(char direction) {
        super.updateDirection(direction);

        if (this.getDirection() == direction) {
            if (direction == 'U') {
                this.setImage(pacmanUpImage);
            } else if (direction == 'D') {
                this.setImage(pacmanDownImage);
            } else if (direction == 'L') {
                this.setImage(pacmanLeftImage);
            } else if (direction == 'R') {
                this.setImage(pacmanRightImage);
            }
        }
    }
}
