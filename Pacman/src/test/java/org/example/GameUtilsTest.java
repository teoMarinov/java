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

    @BeforeEach
    void setUp() {
        character = new Character(0, 0, null);
        rightSideWall = new Tile(0, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, GameDimensions.TILE_SIZE, null);
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
}