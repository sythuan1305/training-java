package day2;

import day2.Collection.ArrayListPerformance;
import day2.Collection.HashMapPerformance;
import day2.Collection.HashSetPerformance;
import day2.Collection.ICollectionPerformance;
import day2.Collection.LinkedListPerformance;
import day2.Collection.TreeMapPerformance;
import day2.Collection.TreeSetPerformance;

public class CollectionPerformance {

    static void TestPerformance(ICollectionPerformance collection) {

    }

    public static void main(String[] args) {
        ICollectionPerformance collection;
        collection = new ArrayListPerformance();
        System.out.println("ArrayListPerformance");
        TestCollection(collection);

        collection = new LinkedListPerformance();
        System.out.println("LinkedListPerformance");
        TestCollection(collection);
        
        collection = new HashSetPerformance();
        System.out.println("HashSetPerformance");
        TestCollection(collection);

        collection = new HashMapPerformance();
        System.out.println("HashMapPerformance");
        TestCollection(collection);

        collection = new TreeSetPerformance();
        System.out.println("TreeSetPerformance");
        TestCollection(collection);

        collection = new TreeMapPerformance();
        System.out.println("TreeMapPerformance");
        TestCollection(collection);

    }

    static void TestCollection(ICollectionPerformance collection) {
        TestFuncPerformance(collection::TestAdd);
        TestFuncPerformance(collection::TestRemove);
        TestFuncPerformance(collection::TestFind);
    }

    static void TestFuncPerformance(Runnable func) {
        for (int i = 0; i < 3; i++) {
            func.run();
        }
    }

}
