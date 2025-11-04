package org.example.Entities;

import org.example.Constants.GameImagePaths;
import org.example.Utils.ImageLoader;

import java.awt.*;

public class Packman extends Player {
    private final Image pacmanRightImage, pacmanLeftImage, pacmanDownImage, pacmanUpImage;

    public Packman(int x, int y, ImageLoader imageLoader) {
        super(x, y, imageLoader.load(GameImagePaths.PACMAN_RIGHT));

        pacmanRightImage = imageLoader.load(GameImagePaths.PACMAN_RIGHT);
        pacmanLeftImage = imageLoader.load(GameImagePaths.PACMAN_LEFT);
        pacmanDownImage = imageLoader.load(GameImagePaths.PACMAN_DOWN);
        pacmanUpImage = imageLoader.load(GameImagePaths.PACMAN_UP);
    }


    @Override
    public void updateDirection(char direction) {
        if (direction == 'U') {
            this.setImage(pacmanUpImage);
        } else if (direction == 'D') {
            this.setImage(pacmanDownImage);
        } else if (direction == 'L') {
            this.setImage(pacmanLeftImage);
        } else if (direction == 'R') {
            this.setImage(pacmanRightImage);
        }
        super.updateDirection(direction);
    }
}
