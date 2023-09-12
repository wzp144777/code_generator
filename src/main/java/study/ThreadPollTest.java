package study;

import cn.hutool.core.collection.CollectionUtil;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
 *   线程池的几大类型
 *
 * */
public class ThreadPollTest {

//        一、方法说明
//          1、shutdown()：停止接收新任务，原来的任务继续执行
//          2、shutdownNow()：停止接收新任务，原来的任务停止执行
//          3、awaitTermination(long timeOut, TimeUnit unit)：当前线程阻塞
//              当前线程阻塞，直到：
//              等所有已提交的任务（包括正在跑的和队列中等待的）执行完；
//              或者 等超时时间到了（timeout 和 TimeUnit设定的时间）；
//              或者 线程被中断，抛出InterruptedException
//              返回true（shutdown请求后所有任务执行完毕）或false（已超时）

    //    FixedThreadPool（有限线程数的线程池）
    //    FixedThreadPool是一种有限线程数的线程池，它的核心线程数和最大线程数是一致的，线程数是固定的。一旦线程池的大小达到了最大值，再有新的任务提交时，就会放入无界阻塞队列中，等到有线程空闲时再从队列中取出任务继续执行。
    public static void pool1() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.err.println("线程1-1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        executorService.submit(new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.err.println("线程1-2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        executorService.submit(new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("线程1-3");
        }));
        executorService.shutdown();

    }

    //    CachedThreadPool（无限线程数的线程池）
    //    CachedThreadPool是一种无限线程数的线程池，线程数会根据需要自动增加或减少。如果有任务需要执行，且当前没有空闲线程可用，就会创建一个新的线程执行任务。
    //    当线程空闲时间超过60秒时，就会被回收。因此，CachedThreadPool适用于执行大量短期异步任务的场景。
    public static void pool2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Thread(() -> {
            System.err.println("线程2");
        }));
        executorService.shutdown();
    }

    //    ScheduledThreadPool（定时线程池）
    //    ScheduledThreadPool是一种定时线程池，它可以在指定的时间执行任务，也可以定期执行任务。ScheduledThreadPool具有定时执行、周期执行、延迟执行等特点，适用于定时任务和周期任务。
    public static void pool3() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.schedule(new Thread(() -> {
            System.err.println("定时线程");
        }), 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Thread(() -> {
            System.err.println("定时线程-1");
        }), 8, 3, TimeUnit.SECONDS);
    }

    //  SingleThreadExecutor（单一线程池）
    //  SingleThreadExecutor 是只有一个线程的线程池，它的特点是保证所有任务都在同一线程中按顺序执行，可以避免多线程情况下的竞争和死锁问题。适用于需要按顺序执行多个任务的场景，例如日志记录、任务提交等。
    public static void pool4() {
        // 实例化单一线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 创建任务并提交到单一线程池
        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                System.err.println("单线程1");
            }
        });
        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(2000);
                System.err.println("单线程2");
            }
        });

        // 手动关闭线程池
        executorService.shutdown();
    }

    //    SingleThreadScheduledExecutor（单一定时线程池）
    //    SingleThreadScheduledExecutor 是只有一个线程的定时线程池，它的特点是在给定延迟后按指定的时间周期性地执行任务。适用于需要延迟执行任务、周期性执行任务的场景，例如定时任务、定时检测等。
    public static void pool5() {
        // 实例化单一定时线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // 延迟一段时间后执行任务
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                // 任务执行的代码
            }
        }, 10, TimeUnit.SECONDS);

        // 延迟一段时间后，按指定时间间隔周期性地执行任务
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 任务执行的代码
            }
        }, 10, 5, TimeUnit.SECONDS);

        // 手动关闭线程池
        scheduledExecutorService.shutdown();
    }

    //    ForkJoinPool（分治线程池）
    //    forkJoinPool 是一种特殊的线程池，它的特点是将大任务分解成多个小任务，然后将这些小任务分配给不同的线程执行，最后将小任务的执行结果合并成大任务的结果。适用于需要分解大任务并发执行的场景，例如并行排序、MapReduce 等。
    public static void pool6() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建父类任务
        RecursiveTask<Integer> task = new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                // 创建子任务集合
                List<RecursiveTask<Integer>> subTasks = new ArrayList<>();
                List<Integer> aaa = CollectionUtil.list(false, 1,2,3,4,5,6,7,8,9,10);
                for (int i = 0; i < 10; i++) {
                    // 子任务返回值
                    // 子任务无法调用变量
                    int finalI = i;
                    RecursiveTask<Integer> subTask = new RecursiveTask<Integer>() {
                        @Override
                        protected Integer compute() {
                            System.err.println(aaa.get(finalI));
                            // 错误实例
                            // System.err.println(i);
                            return aaa.get(finalI);
                        }
                    };
                    subTasks.add(subTask);
                }
                // 执行所有子任务
                invokeAll(subTasks);

                // 将所有子任务的执行结果合并成父任务的结果
                int result = 0;
                for (RecursiveTask<Integer> subTask : subTasks) {
                    result += subTask.join();
                }
                return result;
            }
        };

        // 提交大任务到分治线程池执行
        Integer result = forkJoinPool.invoke(task);
        System.err.println("父任务结果++++" +result);
        // 关闭线程池
        forkJoinPool.shutdown();
    }

    public static void main(String[] args) {
//        pool1();
//        pool2();
//        pool3();
//        pool4();
//        pool5();
        pool6();
    }
}
