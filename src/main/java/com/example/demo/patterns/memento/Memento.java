package com.example.demo.patterns.memento;

//Хранитель
// поведенческий шаблон проектирования, позволяющий, не нарушая инкапсуляцию,
// зафиксировать и сохранить внутреннее состояние объекта так,
// чтобы позднее восстановить его в это состояние.
public class Memento {

    public static void main(String[] args) {
        var game = new Game();
        game.set("LVL 1", 30000);
        System.out.println(game);

        File f = new File();
        f.setSave(game.save());

        System.out.println("playing");
        game.set("LVL 2", 500000);
        System.out.println(game);
        System.out.println("loading....");
        game.load(f.getSave());
        System.out.println(game);
    }


}
//Клиент
class Game {
    private String level;
    private int ms;
    public void set(String level, int ms) {
        this.level = level;
        this.ms = ms;
    };
    public void load(Save save) {
        level = save.getLevel();
        ms = save.getMs();
    }
    public Save save() {
        return new Save(level, ms);
    }

    @Override
    public String toString() {
        return "Game{" +
                "level='" + level + '\'' +
                ", ms=" + ms +
                '}';
    }
}

//Memento, шкатулочка
class Save {
    private String level;
    private int ms;

    public Save(String level, int ms) {
        this.level = level;
        this.ms = ms;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMs() {
        return ms;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }
}

//Опекун, умеет сохранять и возвращать
class File {
    private Save save;

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }
}
