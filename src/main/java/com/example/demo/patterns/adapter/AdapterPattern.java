package com.example.demo.patterns.adapter;

import java.io.FileNotFoundException;

// структурный шаблон проектирования, предназначенный для организации использования функций объекта,
// недоступного для модификации, через специально созданный интерфейс. Другими словами —
// это структурный паттерн проектирования, который позволяет объектам с несовместимыми интерфейсами работать вместе.
public class AdapterPattern {

    public static void main(String[] args) throws FileNotFoundException {
        VectorGraphicsInterface g1 = new VectorAdapterFromRaster();
        g1.drawLine();
        g1.drawSquare();
        VectorGraphicsInterface g2 = new VectorAdapterFromRaster2();
        g1.drawLine();
        g1.drawSquare();

    }

}

interface VectorGraphicsInterface {
    void drawLine();
    void drawSquare();
}

class RasterGraphics {
    void drawRasterLine() {
        System.out.println("Drawing a line");
    }
    void drawRasterSquare() {
        System.out.println("Drawing a square");
    }
}
//through Inheritance
class VectorAdapterFromRaster extends RasterGraphics implements VectorGraphicsInterface {

    @Override
    public void drawLine() {
        drawRasterLine();
    }

    @Override
    public void drawSquare() {
        drawRasterSquare();
    }
}

//thorugh Composition
class VectorAdapterFromRaster2 implements VectorGraphicsInterface {

    RasterGraphics rg = new RasterGraphics();
    @Override
    public void drawLine() {
        rg.drawRasterLine();
    }

    @Override
    public void drawSquare() {
        rg.drawRasterSquare();
    }
}
