import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        List<MyCallable> callableList = new ArrayList<>();
        System.out.println("Создаю потоки...");
        for (int i = 1; i <= 4; i++) {
            callableList.add(new MyCallable("поток " + i));
        }

        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            List<Future<Integer>> task = threadPool.invokeAll(callableList);
            for (int i = 0; i < task.size(); i++) {
                System.out.println(callableList.get(i) + " отправил сообщения: " + task.get(i).get() + " раз");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            final Integer resultQuick = threadPool.invokeAny(callableList);
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
