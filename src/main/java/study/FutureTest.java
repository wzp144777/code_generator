package study;

import java.util.concurrent.*;

public class FutureTest {

    public static void testFeature(){
        // 创建线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String testA = "callable输出回调";
        Future<String> future = executorService.submit(new Callable<String>(){

            @Override
            public String call() throws Exception {
                return callA(testA);
            }
        });

        try {
            // 线程超时时间
            String result = future.get(1L , TimeUnit.SECONDS);
            System.err.println(result);
//            Future就是主线程和子线程的通信桥梁，借助LockSupport辅助线程的切换。通过共享FutureTask对象的数据实现数据间的传输。
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.err.println("超时");
        }finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }

    public static String callA(String content) throws InterruptedException {
        Thread.sleep(2000);
        return content;
    }

    public static void main(String[] args) {
        testFeature();
        System.err.println("123321");
    }

}
