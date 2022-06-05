package fibonacci;

import java.math.BigInteger;

public class FibonacciRunnable implements Runnable {

    private final int n;
    private BigInteger prev = BigInteger.valueOf(1);
    private BigInteger last = BigInteger.valueOf(1);

    public FibonacciRunnable(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        BigInteger current;
        System.out.println("FibonacciRunnable class has started");
        System.out.print(prev + "(thr2) " + last + "(thr2) ");
        for (int i = 3; i <= n; i++) {
            current = prev.add(last);
            System.out.print(current + "(thr2) ");
            prev = last;
            last = current;
            if (Thread.currentThread().isInterrupted()) {
              System.out.println("\n(FibonacciRunnable class) Thread has been interrupted");
                break;
            }
        }
        System.out.println();
    }

    public BigInteger getLast() {
        return last;
    }
}

