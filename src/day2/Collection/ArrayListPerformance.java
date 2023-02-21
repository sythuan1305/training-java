package day2.Collection;

import java.util.ArrayList;
import java.util.List;

public class ArrayListPerformance implements ICollectionPerformance{

    int numberOfElements = 1000000;

    List<Integer> Setup() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public void TestAdd() {
        List<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.add(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("ArrayList Add: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestRemove() {

        List<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.remove(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("ArrayList Remove: " + (endTime - startTime) + " microseconds");
    }

    @Override
    public void TestFind() {
        List<Integer> list = Setup();
        int randomNum = (int) (Math.random() * numberOfElements);

        long startTime = System.nanoTime() / 1000;
        list.contains(randomNum);
        long endTime = System.nanoTime() / 1000;

        System.out.println("ArrayList Find: " + (endTime - startTime) + " microseconds");
    }
    
}
