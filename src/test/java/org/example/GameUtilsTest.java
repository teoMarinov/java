package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    private Character character;
    private Tile rightSideWall;
    private HashSet<Tile> walls;

    @BeforeEach
    void setUp() {
        character = new Character(0, 0, null);
        rightSideWall = new Tile(0, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, null);
        walls = new HashSet<>();
        walls.add(rightSideWall);
    }

    @Test
    void checkCollisionReturnsTrueForOverlap() {
        Tile collisionTile = new Tile(GameDimensions.TILE_SIZE - 1, GameDimensions.TILE_SIZE - 1, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, null);
        assertTrue(GameUtils.checkCollision(character, collisionTile));
    }

    @Test
    void checkCollisionReturnsFalseForNoOverlap() {
        assertFalse(GameUtils.checkCollision(character, rightSideWall));
    }

    @Test
    void changeToViableDirectionWorksOnNoCollision() {
        GameUtils.changeToViableDirection(character, 'R', walls);
        assertEquals('R', character.getDirection());
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void changeToViableDirectionRevertsOnCollision() {
        GameUtils.changeToViableDirection(character, 'D', walls);
        assertEquals('U', character.getDirection());
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void moveCharacterWorksOnNoCollision() {
        GameUtils.changeToViableDirection(character, 'R', walls);
        GameUtils.moveCharacter(character);
        assertEquals(8, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void moveCharacterRevertsOnCollision() {
        character.updateDirection('D');
        GameUtils.moveCharacter(character, walls);
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }
}