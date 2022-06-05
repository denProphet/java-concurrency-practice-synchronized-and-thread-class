package point;

public class Point {
    private int x;
    private int y;

    //Locks для методов с synchronized blocks
    private final Object lock = new Object();
    private static final Object lockStatic = new Object();


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Synchronized методы move(),
     * все варианты мониторов
     */

    public synchronized void moveSync(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public static synchronized void moveSync(int dx, int dy, Point point) {
        point.x += dx;
        point.y += dy;
    }

    public void moveWithSyncBlock(int dx, int dy) {
        synchronized (lock) {
            x += dx;
            y += dy;
        }
    }

    public static void moveWithSyncBlock(int dx, int dy, Point point) {
        synchronized (lockStatic) {
            point.x += dx;
            point.y += dy;
        }
    }

    /**
     * Печать на консоль текущих координат
     */

    public void printCoordinates() {
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
    }
}
