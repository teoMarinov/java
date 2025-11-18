package org.example.Shared;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class ImageLoader {
    private ImageLoader() {
    }

    public static Image load(String path) {
        return new ImageIcon(Objects.requireNonNull(ImageLoader.class.getResource(path))).getImage();
    }
}
