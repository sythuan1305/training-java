package day2.Collection;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetPerformance implements ICollectionPerformance{

    int numberOfElements = 1000000;
    
    SortedSet<Integer> Setup() {
        SortedSet<Integer> list = new TreeSet<Integer>();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public void TestAdd() {
        SortedSet<Integer> list = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements) + numberOfElements;

        long startTime = System.nanoTime() / 1000;
        list.add(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("TreeSet Add: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestRemove() {
        SortedSet<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.remove(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("TreeSet Remove: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestFind() {
        SortedSet<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.contains(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("TreeSet Find: " + (endTime - startTime) + " microseconds");
    }
    
    
}
