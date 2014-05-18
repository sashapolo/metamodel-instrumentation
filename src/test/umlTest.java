public class umlTest {
    private final Test2 reference;
    private final Test3[] multipleReference;

    public static void main(final String[] args) {
        System.out.println("Hello, world!");
    }
}

class Test2 extends Super {
    private final int foo;
}

class Test3 extends Super {
    private final String bar;
}

class Super {
}
