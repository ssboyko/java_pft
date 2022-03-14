package ru.stqa.pft.sandbox;

public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p2) {
        double x = (p2.x - this.x);
        double y = (p2.y - this.y);
        return Math.sqrt(Math.pow(x, 2) - Math.pow(y, 2));
    }
}
