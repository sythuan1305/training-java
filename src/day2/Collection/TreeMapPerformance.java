package day2.Collection;

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapPerformance implements ICollectionPerformance{
    
        int numberOfElements = 1000000;
    
        SortedMap<Integer, Integer> Setup() {
            SortedMap<Integer, Integer> list = new TreeMap<Integer, Integer>();
            for (int i = 0; i < numberOfElements; i++) {
                list.put(i, i);
            }
            return list;
        }
    
        @Override
        public void TestAdd() {
            SortedMap<Integer, Integer> list = Setup();
            int randomNum = (int) (Math.random() * numberOfElements);
    
            long startTime = System.nanoTime() / 1000;
            list.put(randomNum, randomNum);
            long endTime = System.nanoTime() / 1000;
    
            System.out.println("TreeMap Add: " + (endTime - startTime) + " microseconds");
        }
    
        @Override
        public void TestRemove() {
            SortedMap<Integer, Integer> list = Setup();
            int randomNum = (int) (Math.random() * numberOfElements);
    
            long startTime = System.nanoTime() / 1000;
            list.remove(randomNum);
            long endTime = System.nanoTime() / 1000;
    
            System.out.println("TreeMap Remove: " + (endTime - startTime) + " microseconds");
        }
    
        @Override
        public void TestFind() {
            SortedMap<Integer, Integer> list = Setup();
            int randomNum = (int) (Math.random() * numberOfElements);
    
            long startTime = System.nanoTime() / 1000;
            list.containsKey(randomNum);
            long endTime = System.nanoTime() / 1000;
    
            System.out.println("TreeMap Find: " + (endTime - startTime) + " microseconds");
        }
    
    
}
