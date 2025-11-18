package org.example;

import org.example.Entities.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    private GameObject gameObject;

    @BeforeEach
    void setUp() {
        gameObject = new GameObject(0, 1, 10, 15);
    }

    @Test
    void constructor_ShouldInitializeFields() {
        assertEquals(0, gameObject.getX());
        assertEquals(1, gameObject.getY());
        assertEquals(10, gameObject.getWidth());
        assertEquals(15, gameObject.getHeight());
    }

    @Test
    void setX() {
        gameObject.setX(10);
        assertEquals(10, gameObject.getX());
    }

    @Test
    void setY() {
        gameObject.setY(10);
        assertEquals(10, gameObject.getY());
    }

    @Test
    void setWidth() {
        gameObject.setWidth(15);
        assertEquals(15, gameObject.getWidth());
    }

    @Test
    void setHeight() {
        gameObject.setHeight(15);
        assertEquals(15, gameObject.getHeight());
    }
}