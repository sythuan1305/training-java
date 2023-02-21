package day2.Collection;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashSetPerformance implements ICollectionPerformance{

    int numberOfElements = 1000000;

    Set<Integer> Setup() {
        Set<Integer> list = new HashSet<Integer>();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public void TestAdd() {
        Set<Integer> list = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements) + numberOfElements;

        long startTime = System.nanoTime() / 1000;
        list.add(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashSet Add: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestRemove() {
        Set<Integer> list = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.remove(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashSet Remove: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestFind() {
        Set<Integer> list = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.contains(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashSet Find: " + (endTime - startTime) + " microseconds");
    }
    
}
