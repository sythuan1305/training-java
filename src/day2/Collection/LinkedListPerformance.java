package day2.Collection;

import java.util.LinkedList;

public class LinkedListPerformance implements ICollectionPerformance {

    int numberOfElements = 1000000;

    LinkedList<Integer> Setup() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public void TestAdd() {
        LinkedList<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.add(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("LinkedList Add: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestRemove() {
        LinkedList<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.remove(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("LinkedList Remove: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestFind() {
        LinkedList<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.contains(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("LinkedList Find: " + (endTime - startTime) + " microseconds");
    }

}
