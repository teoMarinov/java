package org.example;

import org.example.Constants.GameDimensions;
import org.example.Entities.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void constructor_ShouldInitializeFields() {
        Image mockImage = new Canvas().createImage(20, 20);
        Player character = new Player(0, 0, mockImage);
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
        assertEquals(GameDimensions.TILE_SIZE, character.getWidth());
        assertEquals(GameDimensions.TILE_SIZE, character.getHeight());
        assertEquals(0, character.getStartX());
        assertEquals(0, character.getStartY());
        assertSame(mockImage, character.getImage());
    }
}