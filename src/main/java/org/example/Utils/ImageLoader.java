package org.example.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class ImageLoader {
    private static final ImageLoader INSTANCE = new ImageLoader();

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        return INSTANCE;
    }

    public Image load(String path) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
    }
}
