package org.example;

import org.example.Constants.GameDimensions;
import org.example.Entities.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    private org.example.Entities.Character character;
    private Image mockImage;

    @BeforeEach
    void setUp() {
        mockImage = new Canvas().createImage(20, 20);
        character = new Character(0, 0, mockImage);
    }

    @Test
    void constructor_ShouldInitializeFields() {
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
        assertEquals(GameDimensions.TILE_SIZE, character.getWidth());
        assertEquals(GameDimensions.TILE_SIZE, character.getHeight());
        assertEquals(0, character.getStartX());
        assertEquals(0, character.getStartY());
        assertSame(mockImage, character.getImage());
    }

    @Test
    void getStartX() {
        assertEquals(0, character.getStartX());
    }

    @Test
    void getStartY() {
        assertEquals(0, character.getStartY());
    }

    @Test
    void getDirection() {
        assertEquals('U', character.getDirection());
    }

    @Test
    void getVelocityX() {
        assertEquals(0, character.getVelocityX());
    }

    @Test
    void getVelocityY() {
        assertEquals(0, character.getVelocityY());
    }

    @Test
    void characterHasCorrectStartDirection() {
        assertEquals('U', character.getDirection());
    }

    @Test
    void updateDirectionWorks() {
        character.updateDirection('R');
        assertEquals('R', character.getDirection());
        character.updateDirection('D');
        assertEquals('D', character.getDirection());
        character.updateDirection('L');
        assertEquals('L', character.getDirection());
    }

    @Test
    void updateDirectionDoesntChangeForWrongDirection() {
        character.updateDirection('D');
        character.updateDirection('N');
        assertEquals('D', character.getDirection());
    }

    @Test
    void updateDirectionDoesntMovePosition() {
        character.updateDirection('R');
        assertEquals(0, character.getStartX());
        assertEquals(0, character.getStartY());
    }

    @Test
    void velocityStartsAt0() {
        assertEquals(0, character.getVelocityX());
        assertEquals(0, character.getVelocityY());
    }

    @Test
    void updateVelocityWorks() {
        character.updateDirection('U');
        assertEquals(0, character.getVelocityX());
        assertEquals(-8, character.getVelocityY());

        character.updateDirection('D');
        assertEquals(0, character.getVelocityX());
        assertEquals(8, character.getVelocityY());

        character.updateDirection('L');
        assertEquals(-8, character.getVelocityX());
        assertEquals(0, character.getVelocityY());

        character.updateDirection('R');
        assertEquals(8, character.getVelocityX());
        assertEquals(0, character.getVelocityY());

        character.updateDirection('Z');
        assertEquals(8, character.getVelocityX());
        assertEquals(0, character.getVelocityY());
    }

    @Test
    void moveWorks() {
        character.updateDirection('D');
        character.move();
        assertEquals(0, character.getX());
        assertEquals(8, character.getY());

        character.updateDirection('Z');
        character.move();
        assertEquals(0, character.getX());
        assertEquals(16, character.getY());
    }

    @Test
    void resetWorks() {
        character.updateDirection('D');
        character.move();
        character.reset();
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
        assertEquals('R', character.getDirection());
        assertEquals(0, character.getVelocityX());
        assertEquals(0, character.getVelocityY());
    }

    @Test
    void charactersMovesBetweenBorders() {
        character.updateDirection('R');
        character.setX(GameDimensions.BOARD_WIDTH);
        character.move();
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());

        character.reset();
        character.updateDirection('L');
        character.move();
        assertEquals(GameDimensions.BOARD_WIDTH, character.getX());

        character.reset();
        character.setY(GameDimensions.BOARD_HEIGHT);
        character.updateDirection('D');
        character.move();
        assertEquals(0, character.getY());

        character.reset();
        character.updateDirection('U');
        character.move();
        assertEquals(GameDimensions.BOARD_HEIGHT, character.getY());

    }
}