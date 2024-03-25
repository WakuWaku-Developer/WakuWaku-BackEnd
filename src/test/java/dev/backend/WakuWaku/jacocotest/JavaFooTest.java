package dev.backend.WakuWaku.jacocotest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaFooTest {

    private JavaFoo javaFoo = new JavaFoo();

    @Test
    void hello() {
        String actual = javaFoo.hello("펭");

        assertEquals(actual, "하");
    }
}