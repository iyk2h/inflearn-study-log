package me.study;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class App {

    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        // Class<T>에 접근하는 방법 3가지
        // 모든 클래스를 로딩 한 다음 Class<T>의 인스턴스가 생긴다.

        // 1. "타입.class"로 접근할 수 있다.
        Class<Book> bookClass = Book.class;
        // 2. "인스턴스.getClass()"로 접근할 수 있다.
        Book book = new Book();
        Class<? extends Book> bookInstenceClass = book.getClass();
        // 3. 문자열(FQCN:Fully Qualified Class Name)로 접근할 수 있다.
        Class<?> FQCNGetClass = Class.forName("me.study.Book");

        // 필드 접근
        Arrays.stream(FQCNGetClass.getFields()).forEach(System.out::println);
        /*
          출력 : public java.lang.String me.study.Book.D
          D 만 출력된 이유는 public 이기 때문
         */

        System.out.println();

        Arrays.stream(FQCNGetClass.getDeclaredFields()).forEach(System.out::println);
        /*
          getDeclaredFields() 을 사용한 모든 필드 가져오기
          public java.lang.String me.study.Book.D
          private java.lang.String me.study.Book.A
          private static java.lang.String me.study.Book.B
          private static final java.lang.String me.study.Book.C
          public java.lang.String me.study.Book.D
          protected java.lang.String me.study.Book.E
         */

        System.out.println();

        /*
          필드의 값을 가져오고 싶다.
          값을 가져오려면 인스턴스가 필요하다.
         */

        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                // f.setAccessible(false); IllegalAccessException
                System.out.printf("%s %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        /*
          setAccessible 을 true 로 하지 않으면 접근이 불가능하다.
          setAccessible 의 default 값은 false 이다.
          private java.lang.String me.study.Book.A A
          private static java.lang.String me.study.Book.B B
          private static final java.lang.String me.study.Book.C C
          public java.lang.String me.study.Book.D D
          protected java.lang.String me.study.Book.E E
         */

        System.out.println();

        // 생성자 가져오기 getConstructors()
        Arrays.stream(bookClass.getConstructors()).forEach(System.out::println);
        /*
          public me.study.Book()
          public me.study.Book(java.lang.String,java.lang.String,java.lang.String)
         */

        System.out.println();

        // 부모 클래스 가져오기
        System.out.println(bookClass.getSuperclass());
        System.out.println(MyBook.class.getSuperclass());

        /*
          class java.lang.Object
          class me.study.Book
         */

        System.out.println();
        // 메서드 찾기
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        // 파라미터 없는 경우
        Method method1 = bookClass.getDeclaredMethod("g");
        /*
          혹은 null 입력
          Method method1 = bookClass.getDeclaredMethod("g", null);
          public void me.study.Book.g()
         */

        // 파라미터 있는경우
        Method method2 = bookClass.getDeclaredMethod("h", int.class);
        //public void me.study.Book.g()

        // 파라미터가 두 개 이상인 경우
        Method method3 = bookClass.getDeclaredMethod("sum", int.class, int.class);
        //public int me.study.Book.sum(int,int)

        Class[] parameterGroups = new Class[2];
        parameterGroups[0] = int.class;
        parameterGroups[1] = int.class;
        Method method4 = bookClass.getDeclaredMethod("sum", parameterGroups);
        //public int me.study.Book.sum(int,int)

        // Static 메서드 호출 또는 필드 변경
        Method method5 = bookClass.getDeclaredMethod("sum", int.class, int.class);
        System.out.println(method5.invoke(null, 10, 10));
        // 20

        // Static 필드
        Field field = bookClass.getDeclaredField("B");
        field.setAccessible(true);
        System.out.println(field.get(null));
        field.set(null, "It is new B");
        System.out.println(field.get(null));
        /*
          B
          It is new B
          */

        // static final 필드 접근
        Field field2 = bookClass.getDeclaredField("C");
        field2.setAccessible(true);
        System.out.println(field2.get(null));
        //IllegalAccessException 발생 Can not set static final
        try {
            field2.set(null, "It is new C");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n\n 어노테이션 실습");
        /*
         어노테이션은 주석과 비슷하지만 더 하는일이 많다.
         소스 코드와, 클래스 파일(바이트 코드)에는 남지만 클래스 파일(바이트 코드)을 로딩했을 때 메모리에는 남지 않는다.
         만약, 메모리에 남기고 싶다면 @Retention(RetentionPolicy.RUNTIME) 을 사용하면된다.
         @Retention(RetentionPolicy.CLASS) default

         Book 클래스 파일에 어노테이션 정보까지 보려면
         `javap -c -v ~~/Book.class`


         */

        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);
        // 출력 : @me.study.MyAnnotation()

        /*
        어노테이션 위치 지정 가능
        타입, 필드만 사용 가능 : @Target({ElementType.TYPE, ElementType.FIELD})


         */

        /*
        상속받은 클래스의 경우 어노테이션이 안보인다. 보이게 하려면 @Inherited 을 사용하면 된다.
        `**Retention**` : 해당 애노테이션을 언제까지 유지할 것인가? 소스, 클래스, 런타임
        `**Inherit**` : 해당 애노테이션을 하위 클래스까지 전달할 것인가?
        `**Target**` : 어디에 사용할 수 있는가?

        - **`getAnnotations()`** : 상속받은 (@Inherit) 애노테이션까지 조회
        - **`getDeclareAnnotations()`** : 자기 자신에만 붙어있는 애노테이션 조회
         */
        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);


    }
}
