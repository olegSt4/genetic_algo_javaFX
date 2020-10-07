package models;

public class Point implements Cloneable{
    public final double x;
    public final double y;
    public final int id;

    public Point(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public Object clone() {
        Object cloned = null;
        try {
            cloned = super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return cloned;
    }
}
