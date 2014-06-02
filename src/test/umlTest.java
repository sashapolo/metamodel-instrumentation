public class umlTest extends umlSuper {
    private final Test2 reference;
    private final Test3[] multipleReference;

    private int private1() {}
    private int private2() {}
    private int private3() {}

    public void public1() {}

    public static void main(final String[] args) {
        System.out.println("Hello, world!");
    }
}

class umlSuper {
    protected int x;
    public void public1() {}
    public void public2() {}
    public void public3() {}
}

class Test2 extends Super {
    private final int foo;
}

class Test3 extends Super {
    private final String bar;
}

class Super {
}
