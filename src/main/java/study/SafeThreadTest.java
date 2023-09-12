package study;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SafeThreadTest {
    public static int demo(final List list, final int testCount) throws InterruptedException {
        ThreadGroup group = new ThreadGroup(list.getClass().getName() + "@" + list.hashCode());
        final Random rand = new Random();
        // 实现rannable
        Runnable listAppender = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(rand.nextInt(2));
                } catch (InterruptedException e) {
                    return;
                }
                list.add("0");
            }
        };

        for (int i = 0; i < testCount; i++) {
            new Thread(group, listAppender, "InsertList-" + i).start();
        }

        Thread thread = new Thread();

        while (group.activeCount() > 0) {
            Thread.sleep(10);
        }

        return list.size();
    }
    public static void main(String[] args) throws InterruptedException {
        // 线程不安全实例
        List unsafeList = new ArrayList();
        List safeList = Collections.synchronizedList(new ArrayList());
        final int N = 10000;
        for (int i = 0; i < 10; i++) {
            unsafeList.clear();
            safeList.clear();
            int unsafeSize = demo(unsafeList, N);
            int safeSize = demo(safeList, N);
            System.out.println("unsafe/safe: " + unsafeSize + "/" + safeSize);
        }


        //匿名内部类创建多线程
        new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"创建新线程1");
            }
        }.start();


        //使用Lambda表达式，实现多线程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"创建新线程2");
        }).start();

//        在多线程的代码中，哪些情况下不需要考虑线程安全问题?
//            ①几个线程之间互相没有任何数据共享的情况下，天生是线程安全的;
//            ②几个线程之间即使有共享数据，但都是做读操作,没有写操作时，也是天生线程安全的。

        // 线程安全的 List
        // 写和删除 添加lock  读没有lock操作 所以会出现脏读 数据实时不高的情况下可以使用
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();

        // 添加 synchronized关键字修饰所有方法 是对象锁 所以线程安全
        Vector<String> vector = new Vector<>();
        vector.add("123");
        vector.get(0);
        vector.remove("123");

        // 通过对象mutex 添加 synchronized 锁
        List<String> list = Collections.synchronizedList(new ArrayList<>());

    }

    // 继承thread
    static class Add extends Thread {
        int r = 0;
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                r ++;
            }
        }
    }
}