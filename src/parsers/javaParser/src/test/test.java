public class Test {

    public int x(int y) {
        return 2 * y;
    }

    public static void main(String[] args) throws Exception {
        int z = 5;
        x(z);

        do {
            int a = 8;
            int b = 9;
        } while (z > 0);
        return 2 + x(z);
    }

}

class Test2 {

}
