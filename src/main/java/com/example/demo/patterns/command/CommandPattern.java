package com.example.demo.patterns.command;

// поведенческий шаблон проектирования, используемый при объектно-ориентированном программировании,
// представляющий действие. Объект команды заключает в себе само действие и его параметры.
public class CommandPattern {

    public static void main(String[] args) {
        Comp c = new Comp();
        Enjoyker e = new Enjoyker(new StartCommand(c), new StopCommand(c), new ResetCommand(c));
        e.startComputer();
        e.resetComputer();
        e.stopComputer();
    }

}

class Comp {
    void start() {
        System.out.println("start");
    };

    void stop() {
        System.out.println("stop");
    };

    void reset() {
        System.out.println("reset");
    };
}

interface Command {
    void execute();
}


class StartCommand implements Command {
    Comp comp;
    public StartCommand(Comp comp) {
        this.comp = comp;
    }
    @Override
    public void execute() {
        comp.start();
    }
}

class StopCommand implements Command {
    Comp comp;
    public StopCommand(Comp comp) {
        this.comp = comp;
    }
    @Override
    public void execute() {
        comp.stop();
    }
}

class ResetCommand implements Command {
    Comp comp;
    public ResetCommand(Comp comp) {
        this.comp = comp;
    }
    @Override
    public void execute() {
        comp.reset();
    }
}


class Enjoyker {
    Command start, stop, reset; //Отличается от Фасада тем, что тут один и тот же класс Command

    public Enjoyker(Command start, Command stop, Command reset) {
        this.start = start;
        this.stop = stop;
        this.reset = reset;
    }

    void startComputer() {
        start.execute();
    }

    void stopComputer() {
        stop.execute();
    }

    void resetComputer() {
        reset.execute();
    }

}