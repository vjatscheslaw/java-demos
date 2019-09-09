package com.example.demo;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class AnotherTest {

    @Test
    public void ttest2() {
        List<String> set = new LinkedList();
        int i = 100;
        while (i > 0) {
            set.add(String.valueOf(Math.round(Math.random()*100)));
            i--;
        }
        for (int y = 0; y < set.size(); y++) {
            System.out.println(set.get(y));
        }

    }

    @Test
    public void test3() {

        StringBuffer result = new StringBuffer();
        byte i;
        while (result.length() < 24) {
            i = (byte) (Math.floor(Math.random() * 3) + 1);
            switch (i) {
                case 1:
                    result.append(Character.toChars((byte) (Math.floor(Math.random() * 9) + 48)));
                    break;
                case 2:
                    result.append(Character.toChars((byte) (Math.floor(Math.random() * 25) + 65)));
                    break;
                case 3:
                    result.append(Character.toChars((byte) (Math.round(Math.random() * 25) + 97)));
                    break;
            }
        }

        System.out.println(result.toString());
    }

    @Test
    public void tttest() {
        String dflt = "2000-03-01";
        Timestamp timestamp1 = Timestamp.valueOf(dflt.concat(" 09:30:21.034"));
        Timestamp timestamp2 = Timestamp.valueOf(dflt.concat(" 09:30:24.034"));
        System.out.println(timestamp1.before(timestamp2));
    }

}
