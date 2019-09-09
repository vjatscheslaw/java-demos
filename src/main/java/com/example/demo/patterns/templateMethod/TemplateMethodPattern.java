package com.example.demo.patterns.templateMethod;

// поведенческий шаблон проектирования, определяющий
// основу алгоритма и позволяющий наследникам переопределять
// некоторые шаги алгоритма, не изменяя его структуру в целом.
public class TemplateMethodPattern {

    public static void main(String args[]) {
        C a = new A();
        a.templteMethod();
        System.out.println(
        );
        C b = new B();
        b.templteMethod();
    }
}

abstract class C {
    void templteMethod() {
        System.out.println(1);
        differ();
        System.out.println(3);
    }
    abstract void differ();
}

class A extends C {
    void differ() {
        System.out.println(100);
        System.out.println(200);
        System.out.println(300);

    }
}

class B extends C {
    void differ() {
        System.out.println("Echt");
        System.out.println("Brezl");
        System.out.println("Dirndl");

    }
}
