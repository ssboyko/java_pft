package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Point;

public class PointTests {

    @Test
    public void testDistanceFirstPointMoreThanSecond() {
        Point p1 = new Point(11.0, 10.0);
        Point p2 = new Point(3.0, 6.0);
        Assert.assertEquals(p1.distance(p1,p2),6.928203230275509);
    }

    @Test
    public void testDistanceBothPointsAtSamePosition() {
        Point p1 = new Point(11.0, 10.0);
        Point p2 = new Point(11.0, 10.0);
        Assert.assertEquals(p1.distance(p1,p2),0);
    }

    @Test
    public void testDistanceSecondPointMoreThanFirst() {
        Point p1 = new Point(10.0, 9.0);
        Point p2 = new Point(11.0, 10.0);
        Assert.assertEquals(p1.distance(p1,p2),0.0);
    }

    @Test
    public void testDistanceMinusCoordinates() {
        Point p1 = new Point(-10.0, -9.0);
        Point p2 = new Point(-11.0, -10.0);
        Assert.assertEquals(p1.distance(p1,p2),0.0);
    }


}
