public class MyInheritableThreadLocal {

    private static final InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {

        threadLocal.set(1);

        Thread thread = new Thread(() -> {
            int value = threadLocal.get();
            System.out.println("继承的ThreadLocal 在子线程读取值为: " + value);
        });

        thread.start();

        int value = threadLocal.get();
        System.out.println("继承的ThreadLocal 在main线程读取值为: " + value);
    }
}