package com.example.demo;

import org.junit.Test;
import java.util.Stack;

public class Citeck2 {

    @Test
    public void check() {
        String regExp = "[dsflkdsj(5)dfjkl]";
        System.out.println(checkRegExp(regExp));
    }

    private String checkRegExp(String s) {
        Stack brackets = new Stack<Character>();
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '(':
                case '[':
                    brackets.push(Character.valueOf(ch));
                    break;
                case ')':
                case ']':
                    if (!brackets.isEmpty()) {
                        char chx = (char) brackets.pop();
                        if ((Character.valueOf(ch).equals(Character.valueOf(')'))
                                && !Character.valueOf(chx).equals(Character.valueOf('('))
                        ) || (Character.valueOf(ch).equals(Character.valueOf(']'))
                                && !Character.valueOf(chx).equals(Character.valueOf('[')))
                        ) {
                            result = result.concat("Ошибка: " + ch + " на " + i + "\n");
                        }
                    } else
                        result = result.concat("Ошибка: " + ch + " на " + i + "\n");
                    break;
                default:
                    break;
            }
        }
        if (!brackets.isEmpty()) {
            result = result.concat("Отсутствует правый ограничитель");
        }
        if (!result.isEmpty()) return result;
        else return "Всё хорошо";
    }
}

