import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Создаю потоки...");
        Callable<Integer> myCallable1 = new MyCallable("поток 1");
        Callable<Integer> myCallable2 = new MyCallable("поток 2");
        Callable<Integer> myCallable3 = new MyCallable("поток 3");
        Callable<Integer> myCallable4 = new MyCallable("поток 4");

        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final Future<Integer> task1 = threadPool.submit(myCallable1);
        final Future<Integer> task2 = threadPool.submit(myCallable2);
        final Future<Integer> task3 = threadPool.submit(myCallable3);
        final Future<Integer> task4 = threadPool.submit(myCallable4);

        try {
            final Integer result1 = task1.get();
            final Integer result2 = task2.get();
            final Integer result3 = task3.get();
            final Integer result4 = task4.get();
            System.out.println(myCallable1 + " отправил сообщения: " + result1 + " раз");
            System.out.println(myCallable2 + " отправил сообщения: " + result2 + " раз");
            System.out.println(myCallable3 + " отправил сообщения: " + result3 + " раз");
            System.out.println(myCallable4 + " отправил сообщения: " + result4 + " раз");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            List<Callable<Integer>> list = Arrays.asList(myCallable1, myCallable2, myCallable3, myCallable4);
            final Integer resultQuick = threadPool.invokeAny(list);
            System.out.println("Результат самой быстрой задачи: " + resultQuick);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершаю все потоки...");
        threadPool.shutdown();
    }
}
