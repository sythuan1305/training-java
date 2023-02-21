package day2.Collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HashMapPerformance implements ICollectionPerformance{

    int numberOfElements = 1000000;

    Map<Integer, Integer> Setup() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numberOfElements; i++) {
            map.put(i, i);
        }
        return map;
    }

    @Override
    public void TestAdd() {
        Map<Integer, Integer> map = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements) + numberOfElements;

        long startTime = System.nanoTime() / 1000;
        map.put(randomNum, randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashMap Add: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestRemove() {
        Map<Integer, Integer> map = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements);

        long startTime = System.nanoTime() / 1000;
        map.remove(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashMap Remove: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestFind() {
        Map<Integer, Integer> map = Setup();
        Random rand = new Random();
        int randomNum = rand.nextInt(numberOfElements);

        long startTime = System.nanoTime() / 1000;
        map.containsKey(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("HashMap Find: " + (endTime - startTime) + " microseconds");
    }
}
