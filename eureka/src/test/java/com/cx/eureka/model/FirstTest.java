package com.cx.eureka.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstTest {

    private First first;

    @BeforeEach
    void before() {
        first = new First();
    }

    @Test
    void goWorks(){
        First.go();
        assertTrue(true);
    }



}