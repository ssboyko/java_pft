package ru.stqa.pft.sandbox;

import ru.stqa.pft.sandbox.Point;

public class MyFirstProgram {
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);

        //System.out.println("Расстояние между двумя точками равно " + p1.distance(p1, p2));
        System.out.println("Расстояние между двумя точками равно " + p1.distance(p2));

    }

//    public static double distance(Point p1, Point p2) {
//        double x = (p2.x - p1.x);
//        double y = (p2.y - p1.y);
//        return Math.sqrt(Math.pow(x, 2) - Math.pow(y, 2));
//    }
}
