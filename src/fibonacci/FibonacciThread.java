package fibonacci;

import java.math.BigInteger;

public class FibonacciThread extends Thread {

    private final int n;
    private BigInteger prev = BigInteger.valueOf(1);
    private BigInteger last = BigInteger.valueOf(1);

    public FibonacciThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        BigInteger current;
        System.out.println("FibonacciThread class has started");
        System.out.print(prev + "(thr1) " + last + "(thr1) ");
        for (int i = 3; i <= n; i++) {
            current = prev.add(last);
            System.out.print(current + "(thr1) ");
            prev = last;
            last = current;
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("\n(FibonacciThread class) Thread has been interrupted");
                break;
            }
        }
        System.out.println();
    }

    public BigInteger getLast() {
        return last;
    }
}
