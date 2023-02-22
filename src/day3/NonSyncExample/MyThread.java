package day3.NonSyncExample;

class MyThread implements Runnable {
    private UnSafeSequence unsafe;

    public MyThread(UnSafeSequence unsafe) {
        this.unsafe = unsafe;
    }

    @Override
    public void run() {
        unsafe.NextValue();
    }

}