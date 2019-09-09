package com.example.demo.patterns.surrogate;

// структурный шаблон проектирования, предоставляющий объект, который контролирует
// доступ к другому объекту, перехватывая все вызовы (выполняет функцию контейнера).
public class SurrogateOrProxyPattern {

    public static void main(String[] args) {
        Image image = new ProxyImage("D://file.mov");
        image.display();

    }
}

interface Image {
    void display();
}

class RealImage implements Image {

    String file;

    public RealImage(String file) {
        this.file = file;
        load();
    }

    @Override
    public void display() {
        System.out.println("displaying " + file);
    }

    public void load() {
        System.out.println("loading " + file);
    }
}

class ProxyImage implements Image {

    String file;
    RealImage image;

    public ProxyImage(String file) {
        this.file = file;
    }

    @Override
    public void display() {
        if (image == null)
            image = new RealImage(file);
        image.display();
    }
}


