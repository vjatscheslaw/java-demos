package com.example.demo.patterns.mediator;

import java.util.ArrayList;
import java.util.List;

// поведенческий шаблон проектирования, обеспечивающий взаимодействие множества объектов,
// формируя при этом слабую связанность и избавляя объекты от необходимости явно ссылаться друг на друга.
public class MediatorPattern {

    public static void main(String[] args) {

        TextChat chat = new TextChat();

        User admin = new Admin(chat, "AdMiN");
        User user1 = new SimpleUser(chat, "Igor");
        User user2 = new SimpleUser(chat, "Alex");
        User user3 = new SimpleUser(chat, "Vasya");

        chat.setAdmin(admin);
        chat.addUser(user1);
        chat.addUser(user2);
        chat.addUser(user3);
        admin.sendMessage("ohoho");
        user1.sendMessage("Привет");
        user2.sendMessage("Zdorova");
        user3.sendMessage("Привет");
    }

}

//интерфейс клиента
abstract class User {
    Chat chat;
    String name;

    public User(Chat chat, String name) {
        this.chat = chat;
        this.name = name;
    }

    public void sendMessage(String message) {
        chat.sendMessage(message, this);
    }

    abstract void getMessage(String message);

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User [" +
                "name='" + name + '\'' +
                "']";
    }
}

//интерфейс посредника
interface Chat {
    void sendMessage(String message, User user);
}

//конкретный клиент
class Admin extends User {

    public Admin(Chat chat, String name) {
        super(chat, name);
    }

    @Override
    public void sendMessage(String message) {
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("Администратор " + getName() + " receives the message '" + message + "'");
    }
}

//конкретный клиент
class SimpleUser extends User {
    public SimpleUser(Chat chat, String name) {
        super(chat, name);
    }

    @Override
    public void sendMessage(String message) {
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("Пользователь " + getName() + " receives a message '" + message + "'");
    }
}

//concrete Mediator
class TextChat implements Chat {
    User admin;
    List<User> users = new ArrayList<>();

    public void setAdmin(User admin) {
        if (admin instanceof Admin) {
            this.admin = admin;
        } else {
            throw new RuntimeException("Not enough rights");
        }
    }

    public void addUser(User u) {
        if (admin == null) {
            throw new RuntimeException("No admin");
        } else if (u instanceof SimpleUser)
            users.add(u);
        else throw new RuntimeException("Admin cannot enter the chat");
    }

    public void sendMessage(String message, User user) {
        if (user instanceof Admin) {
            for (User u : users) {
                u.getMessage(user.getName() + ": " + message);
            }
        } else if (user instanceof SimpleUser) {
            for (User u : users) {
                if (u != user) {
                    u.getMessage(user.getName() + ": " + message);
                }

            }
            admin.getMessage(user.getName() + ": " + message);

        }

    }
}
