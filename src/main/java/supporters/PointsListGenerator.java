package supporters;

import models.Point;

import java.util.ArrayList;
import java.util.Random;

public class PointsListGenerator {
    private static Random random = new Random();

    public static ArrayList<Point> getArrayList(int size) {
        ArrayList<Point> pointList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            Point nextPoint = new Point(x, y, i + 1);
            pointList.add(nextPoint);
        }
        return pointList;
    }
}
