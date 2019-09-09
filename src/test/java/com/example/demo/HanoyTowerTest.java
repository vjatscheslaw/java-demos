package com.example.demo;

import org.junit.Before;
import org.junit.Test;

public class HanoyTowerTest {

    final char[] rods = {'A', 'B', 'C'};
    int[] circles = new int[3];

    @Before
    public void populateCircles() {
        for (int i = 1; i <= circles.length; i++) {
            circles[i-1] = i;
        }
    }

    @Test
    public void doTowers() {
        doTheThing(circles.length, rods[0], rods[1], rods[2]);
    }

    private void doTheThing(int onTop, char A, char B, char C) {
        if (onTop == 1) System.out.println("Disk " + onTop + " from " + A + " to " + C);
        else {
            doTheThing(onTop-1, A, C, B);
            System.out.println("Disk " + onTop + " from " + A + " to " + C);
            doTheThing(onTop-1, B, A, C);
        }
    }

}
