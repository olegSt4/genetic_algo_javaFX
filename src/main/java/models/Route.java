package models;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Route implements Comparable<Route> {
    public final ArrayList<Point> citySequence;
    public final double length;

    public Route(ArrayList<Point> citySequence, double widthScale, double heightScale) {
        this.citySequence = citySequence;

        double l = 0;
        for (int i = 0; i < citySequence.size() - 1; i++) {
            double x1 = citySequence.get(i).x * widthScale;
            double y1 = citySequence.get(i).y * heightScale;
            double x2 = citySequence.get(i + 1).x * widthScale;
            double y2 = citySequence.get(i + 1).y * heightScale;

            l += Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }

        this.length = l;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (Point point : citySequence) {
            result = result*prime + point.id;
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) return false;

        Route otherRoute = (Route) other;
        if (otherRoute.citySequence.size() != this.citySequence.size()) return false;

        for (int i = 0; i < this.citySequence.size(); i++) {
            if (this.citySequence.get(i).id != otherRoute.citySequence.get(i).id) return false;
        }

        return true;
    }

    @Override
    public int compareTo(Route other) {
        return Double.compare(this.length, other.length);
    }
}
