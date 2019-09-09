package com.example.demo.patterns.chainOfresponsibility;

//Chain of Responsibility -  поведенческий шаблон проектирования,
// предназначенный для организации в системе уровней ответственности.
public class ChORTest {
    public static void main(String[] args) {
        Logger sms = new SMSLogger(Level.ERROR);
        Logger file = new FileLogger(Level.INFO);

        sms.setNext(file);

        sms.log("Vasso", Level.INFO);
        sms.log("Vasso2", Level.WARN);
        sms.log("Vasso3", Level.ERROR);
        sms.log("Vasso0", Level.DEBUG);

    }

}

class Level {
    public static final int ERROR = 1;
    public static final int WARN = 2;
    public static final int INFO = 3;
    public static final int DEBUG = 4;
}

abstract class Logger {
    int priority;
    Logger next;
    public Logger(int priority) { this.priority = priority; }
    public void log(String message, int level) {
        if (level <= priority) writte(message); //Хохо, шаблонный метод!
        if (next != null) next.log(message, level);
    }

    public void setNext(Logger logger) {
        this.next = logger;
    }

    public abstract void writte(String message); //Хохо, шаблонный метод!
}

class SMSLogger extends Logger {

    public SMSLogger(int priority) {
        super(priority);
    }

    @Override
    public void writte(String message) {
        System.out.println("SMS: " + message);
    }
}

class FileLogger extends Logger {

    public FileLogger(int priority) {
        super(priority);
    }

    @Override
    public void writte(String message) {
        System.out.println("FILE: " + message);
    }
}