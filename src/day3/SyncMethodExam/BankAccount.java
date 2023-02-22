package day3.SyncMethodExam;

public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                account.deposit(20);
                account.withdraw(10);
            });
            
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        }

        System.out.println(account.balance);
    }
}
