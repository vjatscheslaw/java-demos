package com.example.demo;

import org.junit.Test;

public class Citeck3 {

    int num = 39712090;

    @Test
    public void convert() {

        if ((num & 1) == 0) {
            num ^= 1;
        }

        System.out.println(num);

    }


}
