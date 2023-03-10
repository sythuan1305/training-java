public class DiamondOperatorWithInnerClass {
    // biến appending là một đối tượng được extend từ lớp StringAppender
    // được sử dụng để triển khai phương thức append() của lớp StringAppender bằng lớp ẩn danh và trả về một đối tượng được extend từ lớp StringAppender
    // Diamond Operator (<>) sẽ tự động suy ra kiểu dữ liệu của đối tượng được extend từ lớp StringAppender là StringAppender<String>
    // class appending extends StringAppender<String>{
    //     public String append(String a, String b) {
    //         return new StringBuilder(a).append("-").append(b).toString();
    //     }
    // }
    static StringAppender<String> appending = new StringAppender<>() {
        @Override
        public String append(String a, String b) {
            return new StringBuilder(a).append("-").append(b).toString();
        }
    };
    
    // lớp StringAppender là một lớp trừu tượng có phương thức append() trả về một đối tượng
    public abstract static class StringAppender<T> {
        public abstract T append(String a, String b);
    }

    public static void main(String[] args) {
        System.out.println(appending.append("Hello", "World"));
    }
}
