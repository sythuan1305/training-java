package day3.NonSyncExample;

public class UnSafeSequence {
    private int value;

    UnSafeSequence() {
        value = 0;
    }

    public  void  NextValue() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
