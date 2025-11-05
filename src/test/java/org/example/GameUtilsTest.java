package org.example;

import org.example.Constants.GameDimensions;
import org.example.Entities.Character;
import org.example.Entities.Tile;
import org.example.Shared.GameUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    private org.example.Entities.Character character;
    private Tile rightSideWall;
    private HashSet<Tile> walls;
    private GameUtils gameUtils;

    @BeforeEach
    void setUp() {
        character = new Character(0, 0, null);
        rightSideWall = new Tile(0, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, null);
        walls = new HashSet<>();
        walls.add(rightSideWall);
        gameUtils = GameUtils.getInstance();
    }

    @Test
    void checkCollisionReturnsTrueForOverlap() {
        Tile collisionTile = new Tile(GameDimensions.TILE_SIZE - 1, GameDimensions.TILE_SIZE - 1, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, null);
        assertTrue(gameUtils.checkCollision(character, collisionTile));
    }

    @Test
    void checkCollisionReturnsFalseForNoOverlap() {
        assertFalse(gameUtils.checkCollision(character, rightSideWall));
    }

    @Test
    void changeToViableDirectionWorksOnNoCollision() {
        gameUtils.changeToViableDirection(character, 'R', walls);
        assertEquals('R', character.getDirection());
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void changeToViableDirectionRevertsOnCollision() {
        gameUtils.changeToViableDirection(character, 'D', walls);
        assertEquals('U', character.getDirection());
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void moveCharacterWorksOnNoCollision() {
        gameUtils.changeToViableDirection(character, 'R', walls);
        gameUtils.moveCharacter(character);
        assertEquals(8, character.getX());
        assertEquals(0, character.getY());
    }

    @Test
    void moveCharacterRevertsOnCollision() {
        character.updateDirection('D');
        gameUtils.moveCharacter(character, walls);
        assertEquals(0, character.getX());
        assertEquals(0, character.getY());
    }
}