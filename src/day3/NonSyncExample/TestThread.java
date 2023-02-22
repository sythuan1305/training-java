package day3.NonSyncExample;

public class TestThread {
    public static void main(String[] args) {
        UnSafeSequence seq = new UnSafeSequence();

        for (int i = 0; i < 30; i++) {
            MyThread thread = new MyThread(seq);
            new Thread(thread).start();
        }

        System.out.println(seq.getValue());
    }
}
