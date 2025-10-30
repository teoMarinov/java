package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class GameDimensionsTest {

    @Test
    void privateConstructor_shouldThrowException() throws Exception {
        Constructor<GameDimensions> constructor = GameDimensions.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        GameDimensions instance = constructor.newInstance();
        assertNotNull(instance);
    }

}