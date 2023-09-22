package study.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SumThread {

    static class  AService {
         public static Integer get() {
             Random random = new Random();
            return random.nextInt(10);
        }
    }

    static class  BService {
        public static Integer get() {
            Random random = new Random();
            return random.nextInt(10);
        }
    }

    static class  CService {
        public static Integer get() {
            Random random = new Random();
            return random.nextInt(10);
        }
    }


    public static void main(String[] args) {

        List<Integer> numberList = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try {
            executorService.submit(
                // 获取A的数字
                new Thread(() -> {
                    System.out.println("获取A");
                    numberList.add(AService.get());
                    latch.countDown();
                })
            );

            executorService.submit(
                // 获取B的数字
                new Thread(() -> {
                    System.out.println("获取B");
                    numberList.add(BService.get());
                    latch.countDown();
                })
            );

            executorService.submit(
                // 获取C的数字
                new Thread(() -> {
                    System.out.println("获取C");
                    numberList.add(CService.get());
                    latch.countDown();
                })
            );

            latch.await();
            executorService.shutdown();
            System.out.println("计算总数" + numberList.stream().reduce(Integer::sum).orElse(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
