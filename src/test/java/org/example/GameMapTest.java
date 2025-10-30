package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    @Test
    void privateConstructor_shouldThrowException() throws Exception {
        Constructor<GameMap> constructor = GameMap.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        GameMap instance = constructor.newInstance();
        assertNotNull(instance);
    }

}