import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool implements ConnectionPool{
    private String url; // biến url dùng để lưu trữ đường dẫn đến database
    private String user; // biến user dùng để lưu trữ tên đăng nhập của database
    private String password; // biến password dùng để lưu trữ mật khẩu của database
    private List<ConnectionPool> connectionPool; // biến connectionPool dùng để lưu trữ các connection
    private List<ConnectionPool> usedConnections = new ArrayList<>(); // biến usedConnections dùng để lưu trữ các connection đang được sử dụng
    private static int INITIAL_POOL_SIZE = 10; // biến INITIAL_POOL_SIZE dùng để lưu trữ số lượng connection ban đầu

    // phương thức create() dùng để tạo ra 1 connection pool
    public static BasicConnectionPool create(
            String url, String user,
            String password) throws SQLException {
        
        // khởi tạo 1 list connection
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            // thêm các connection vào list
            pool.add(createConnection(url, user, password));
        }
        // trả về 1 connection pool
        return new BasicConnectionPool(url, user, password, pool);
    }

    // hàm khởi tạo với 4 tham số là url, user, password và 1 list connection
    private BasicConnectionPool(
            String url, String user, String password,
            List<Connection> pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = pool;
    }

    // standard constructors

    // phương thức getConnection() dùng để lấy 1 connection trong connection pool
    @Override
    public Connection getConnection() {
        // lấy 1 connection trong connection pool
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        // thêm connection vào usedConnections
        usedConnections.add(connection);
        return connection;
    }

    // phương thức releaseConnection() dùng để trả lại 1 connection vào connection pool
    @Override
    public boolean releaseConnection(Connection connection) {
        // thêm connection vào connection pool
        connectionPool.add(connection);
        // xóa connection khỏi usedConnections
        return usedConnections.remove(connection);
    }

    // phương thức createConnection() dùng để tạo ra 1 connection
    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
                // đăng ký driver
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                // trả về 1 connection
        return DriverManager.getConnection(url, user, password);
    }

    // phương thức getSize() dùng để lấy tổng số connection trong connection pool và usedConnections
    public int getSize() {
        // trả về tổng số connection trong connection pool và usedConnections
        return connectionPool.size() + usedConnections.size();
    }

    // phương thức getUrl() dùng để lấy đường dẫn đến database
    public String getUrl() {
        return url;
    }

    // phương thức getUser() dùng để lấy tên đăng nhập của database
    public String getUser() {
        return user;
    }

    // phương thức getPassword() dùng để lấy mật khẩu của database
    public String getPassword() {
        return password;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // tạo 1 connection pool
        BasicConnectionPool connectionPool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/world", "root", "123qwe!@#");


        // hiển thị tổng số lượng connection trong connection pool và usedConnections
        System.out.println("Pool size: " + connectionPool.getSize());

        // hiển thị số lượng connection trong connection pool và usedConnections
        connectionPool.logConnectionPool();


        // sử dụng 1 connection
        Connection connection = connectionPool.getConnection();

        // hiển thị số lượng connection trong connection pool và usedConnections
        connectionPool.logConnectionPool();


        // release connection
        connectionPool.releaseConnection(connection);

        // hiển thị số lượng connection trong connection pool và usedConnections
        connectionPool.logConnectionPool();
    }


    // phương thức logConnectionPool() dùng để hiển thị số lượng connection trong connection pool và usedConnections
    void logConnectionPool() {
        System.out.println("connectionPool: " + connectionPool.size());
        System.out.println("usedConnections: " + usedConnections.size());
    }
}
 
