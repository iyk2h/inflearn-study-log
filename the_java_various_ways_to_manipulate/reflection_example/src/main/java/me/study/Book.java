package me.study;

import java.lang.annotation.Target;

@MyAnnotation
public class Book {
    private String A = "A";
    private static String B = "B";
    private static final String C = "C";
    public String D = "D";
    @MyAnnotation
    protected String E = "E";

//    @MyAnnotation
    public Book() {

    }

    public Book(String a, String d, String e) {
        A = a;
        D = d;
        E = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("G");
    }

    public int h(int i) {
        return 100;
    }

    static public int sum(int a, int b) {
        return a + b;
    }
}
