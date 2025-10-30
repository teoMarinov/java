package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile tile;
    private Image mockImage;

    @BeforeEach
    void setUp() {
        mockImage = new Canvas().createImage(10, 10);
        tile = new Tile(5, 10, 20, 30, mockImage);
    }

    @Test
    void constructor_ShouldInitializeFields() {
        assertEquals(5, tile.getX());
        assertEquals(10, tile.getY());
        assertEquals(20, tile.getWidth());
        assertEquals(30, tile.getHeight());
        assertEquals(mockImage, tile.getImage());
    }

    @Test
    void getImage() {
        assertSame(mockImage, tile.getImage());
    }
}