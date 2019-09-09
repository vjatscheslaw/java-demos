package com.example.demo.patterns.singleton;

//порождающий шаблон проектирования, гарантирующий, что в однопроцессном приложении
// будет единственный экземпляр некоторого класса, и предоставляющий глобальную точку
// доступа к этому экземпляру.
public class SingletonPattern {

    public static void main(String[] args) {


        Thread[] threads = new Thread[10000];

        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(new R());
            threads[i].start();
        }

        try {
            for (int i = 0; i < 1000; i++) {
                threads[i].join();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        Singleton s = Singleton.getInstance();
        System.out.println(s.getCount());
    }
}

class R implements Runnable {

    @Override
    public void run() {
        Singleton.getInstance();
    }
}

//Потоконебезопасный
class Singleton {
    private static int counter = 0;
    private static Singleton instance;
    private Singleton() {
        counter++;
    }

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    public int getCount() {
        return counter;
    }
}

//потокобезопасный, но с EAGER инициализацией
class Singleton2 {
    private static int counter = 0;
    private static Singleton2 instance = new Singleton2();
    private Singleton2() {
        counter++;
    }

    public static Singleton2 getInstance() {
        return instance;
    }

    public int getCount() {
        return counter;
    }
}

//потокобезопасный, с LAZY инициализацией, но медленно работает, так как нам нужен synchronized
//только один раз, а тут он каждый раз вызывается
class Singleton3 {
    private static int counter = 0;
    private static Singleton3 instance = null;
    private Singleton3() {
        counter++;
    }

    public static synchronized Singleton3 getInstance() {
        if (instance == null)
            instance = new Singleton3();
        return instance;
    }

    public int getCount() {
        return counter;
    }
}

//потокобезопасный, с LAZY инициализацией, но медленно работает, так как нам нужен synchronized
//только один раз, а тут он каждый раз вызывается
class Singleton4 {
    private static int counter = 0;
    private static volatile Singleton4 instance = null;
    private Singleton4() {
        counter++;
    }

    public static Singleton4 getInstance() {
        if (instance == null)
            synchronized (Singleton4.class) {
            if (instance == null)
                instance = new Singleton4();
            }
        return instance;
    }

    public int getCount() {
        return counter;
    }
}
