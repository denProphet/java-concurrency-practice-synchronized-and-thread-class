package point;

import point.Point;

public class MovePointTo1InAllWaysRunnableTask implements Runnable {
    private final Point point;

    public MovePointTo1InAllWaysRunnableTask(Point point) {
        this.point = point;
    }


    @Override
    public void run() {
        point.moveSync(1, 1);
        //Point.moveSync(1,1,point);
        //point.moveWithSyncBlock(1,1);
        //Point.moveWithSyncBlock(1,1,point);
    }
}
