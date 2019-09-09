package com.example.demo.patterns.iterator;

// поведенческий шаблон проектирования. Представляет собой объект, позволяющий получить
// последовательный доступ к элементам объекта-агрегата без использования описаний каждого
// из агрегированных объектов.
public class ItaratorPattern {

    public static void main(String[] args) {

        ConcreteAggregate aggregate = new ConcreteAggregate();
        Iterator i = aggregate.getIterator();

        while (i.hasNext()) {
            System.out.println((String) i.next());
        }

    }
}

interface Iterator {
    boolean hasNext();
    Object next();
}

interface Aggregate {
    Iterator getIterator();
}

class ConcreteAggregate  implements Aggregate {
    String[] tasks = {"построить дом", "родить сына", "посадить дерево"};

    @Override
    public Iterator getIterator() {
        return null;
    }

    private class TaskIterator implements Iterator {

        int index = 0;

        @Override
        public boolean hasNext() {
            if (index < tasks.length) {
                return true;
            } else return false;
        }

        @Override
        public Object next() {
            return tasks[index++];
        }
    }


}


