public class BankAccount {

    // biến balance dùng để lưu trữ số tiền trong tài khoản
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    // phương thức deposit() dùng để nạp tiền vào tài khoản
    public synchronized void deposit(int amount) {
        balance += amount;
    }

    // phương thức withdraw() dùng để rút tiền ra khỏi tài khoản
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }

    public static void main(String[] args) {
        // khởi tạo 1 tài khoản với số tiền là 100
        BankAccount account = new BankAccount(100);

        // khởi tạo 10 thread
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                // mỗi thread sẽ nạp 20 đồng và rút 10 đồng
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

        // in ra số tiền trong tài khoản
        System.out.println(account.balance);
        // kết quả sẽ là 200 vì 10 thread sử dụng synchronized method để đồng bộ 10 thread
    }
}
