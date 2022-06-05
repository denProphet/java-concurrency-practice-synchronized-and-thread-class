import fibonacci.FibonacciRunnable;
import fibonacci.FibonacciThread;
import point.MovePointTo1InAllWaysRunnableTask;
import point.Point;

import java.util.ArrayList;
import java.util.Collection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    /**
     * Задание 2
     * Создать и запустить поток для вычисления N-го числа Фибоначчи,
     * наследуя класс Thread.
     *
     * Метка thrN означает каким потоком выполненно действие,
     * в данном случае поток лишь 1
     */
    static class FibonacciThreadTest {
        public static void main(String[] args) {
            FibonacciThread fibonacciThread = new FibonacciThread(6);
            fibonacciThread.start();
            try {
                fibonacciThread.join(); // ждем пока поток полностью отработает, блок
                System.out.println("Last number is " + fibonacciThread.getLast());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Задание 3
     * Создать и запустить поток для вычисления N-го числа Фибоначчи,
     * реализуя Runnable.
     *
     * Метка thrN означает каким потоком выполненно действие,
     * в данном случае поток лишь 1
     */
    static class FibonacciRunnableTest {
        public static void main(String[] args) {
            FibonacciRunnable fibonacciRunnable = new FibonacciRunnable(6);
            Thread thread = new Thread(fibonacciRunnable);
            thread.start();
            try {
                thread.join(); // ждем пока поток полностью отработает, блок
                System.out.println("Last number is " + fibonacciRunnable.getLast());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Задание 4
     * Реализовать прерывание потоков из заданий выше через 3 секунды.
     * Вывести результаты после завершения всех потоков с отметкой прерванных вычислений.
     *
     * Я запустил 2 потока одновременно, вывод на консоль последовательности естественно смешивается
     * (числа с последовательности того или иного потока помечены thr1/thr2 соответственно)
     *
     * За 3 сек (условие задачи) прерываение не успевает произойти (в моем случае) и в итоге для
     * двоих потоков мы имеем
     * ожидаемо нормальный конечный результат (значение поля последнего числа последовательности)
     */
    static class FibonacciThreadsInterruptTest {
        public static void main(String[] args) {

            FibonacciThread fibonacciThread1 = new FibonacciThread(5);

            FibonacciRunnable fibonacciRunnable = new FibonacciRunnable(5);
            Thread fibonacciThread2 = new Thread(fibonacciRunnable);
            fibonacciThread1.start();
            fibonacciThread2.start();
            try {
                fibonacciThread1.join(3000);
                fibonacciThread1.interrupt();
                System.out.println("(thread 1)Last number is " + fibonacciThread1.getLast());

                fibonacciThread2.join(3000);
                fibonacciThread2.interrupt();
                System.out.println("(thread 2)Last number is " + fibonacciRunnable.getLast());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Задание 4(1)
     * Реализовать прерывание потоков из заданий выше через 3 секунды.
     * Вывести результаты после завершения всех потоков с отметкой прерванных вычислений.
     *
     * Так как за 3 секунды задачи успевают закончится, я сделаю прерывание быстрее.
     * Также разделю запуск потоков (последовательный запуск, так как при одновременном запуске вывод
     * на консоле смешивается)
     */
    static class FibonacciThreadsInterruptTest2 {
        public static void main(String[] args) throws InterruptedException {

            //поток 1
            FibonacciThread fibonacciThread1 = new FibonacciThread(5);
            fibonacciThread1.start();
            try {
                fibonacciThread1.join(5);
                fibonacciThread1.interrupt();
                System.out.println("(thread 1)Last number is " + fibonacciThread1.getLast());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.sleep(1000);

            //поток 2
            FibonacciRunnable fibonacciRunnable = new FibonacciRunnable(5);
            Thread fibonacciThread2 = new Thread(fibonacciRunnable);
            fibonacciThread2.start();
            try{
                fibonacciThread2.join(10);
                fibonacciThread2.interrupt();
                System.out.println("(thread 2)Last number is " + fibonacciRunnable.getLast());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Задание 1
     * Создать 2000 одновременных задач в Executor,
     * которые двигают единственную двухмерную точку (point.Point) в одном направлении.
     * Подтвердить проблему атомарности и
     * решить ее с помощью ключевого слова synchronized (все варианты мониторов).
     *
     * в task (MovePointTo1InAllWaysRunnableTask) в методе run используется один из 4
     * вариаций методов с synchronized (остальные в task.run() закоментированы)
     */
    static class MovePointTest {
        public static void main(String[] args) {
            ExecutorService executor = Executors.newCachedThreadPool();
            Point point = new Point(0,0);

            MovePointTo1InAllWaysRunnableTask task = new MovePointTo1InAllWaysRunnableTask(point);
            Collection<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < 2000; i++) {
              Future<?> future = executor.submit(task);
              futures.add(future);
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
           point.printCoordinates();
        }
    }

}
