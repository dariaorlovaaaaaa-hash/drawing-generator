package com.drawing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для классов геометрических фигур.
 */
class ShapeTest {

    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;

    @BeforeEach
    void setUp() {
        point1 = new Point(0, 0);
        point2 = new Point(10, 0);
        point3 = new Point(10, 10);
        point4 = new Point(0, 10);
    }

    @Test
    void testLineCreation() {
        Line line = new Line(point1, point3, "#FF0000", 2.0);

        assertEquals("Line", line.getType());
        assertEquals(2, line.getPoints().size());
        assertEquals(0, line.getArea());
        assertTrue(line.getLength() > 0);
        assertEquals("#FF0000", line.getColor());
        assertEquals(2.0, line.getLineWidth());
    }

    @Test
    void testCircleCreation() {
        Point center = new Point(5, 5);
        Circle circle = new Circle(center, 5, "#00FF00", 1.5);

        assertEquals("Circle", circle.getType());
        assertTrue(circle.getPoints().size() > 0);
        assertTrue(circle.getArea() > 0);
        assertEquals(5, circle.getRadius());
        assertEquals(center, circle.getCenter());
    }

    @Test
    void testRectangleCreation() {
        Rectangle rectangle = new Rectangle(point1, 10, 10, "#0000FF", 1.0);

        assertEquals("Rectangle", rectangle.getType());
        assertEquals(4, rectangle.getPoints().size());
        assertEquals(100, rectangle.getArea());
        assertTrue(rectangle.containsPoint(new Point(5, 5)));
        assertFalse(rectangle.containsPoint(new Point(15, 15)));
    }

    @Test
    void testTriangleCreation() {
        Triangle triangle = new Triangle(point1, point2, point3, "#FFFF00", 2.0);

        assertEquals("Triangle", triangle.getType());
        assertEquals(3, triangle.getPoints().size());
        assertTrue(triangle.getArea() > 0);
        assertTrue(triangle.containsPoint(new Point(5, 2)));
    }

    @Test
    void testParabolaCreation() {
        Parabola parabola = new Parabola(1, 0, 0, -5, 5, "#FF00FF", 1.5);

        assertEquals("Parabola", parabola.getType());
        assertTrue(parabola.getPoints().size() > 0);
        assertTrue(parabola.getArea() > 0);
        assertNotNull(parabola.getVertex());
    }

    @Test
    void testTrapezoidCreation() {
        Trapezoid trapezoid = new Trapezoid(
                new Point(0, 0),
                new Point(8, 0),
                new Point(6, 6),
                new Point(2, 6),
                "#00FFFF",
                1.0
        );

        assertEquals("Trapezoid", trapezoid.getType());
        assertEquals(4, trapezoid.getPoints().size());
        assertTrue(trapezoid.getArea() > 0);
        assertTrue(trapezoid.getPerimeter() > 0);
    }

    @Test
    void testPointDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);

        assertEquals(5, p1.distanceTo(p2), 0.001);
        assertEquals(p1.distanceTo(p2), p2.distanceTo(p1), 0.001);
    }

    @Test
    void testShapeColorAndLineWidth() {
        Line line = new Line(point1, point2, "#123456", 3.0);

        assertEquals("#123456", line.getColor());
        assertEquals(3.0, line.getLineWidth());

        line.setColor("#654321");
        line.setLineWidth(2.0);

        assertEquals("#654321", line.getColor());
        assertEquals(2.0, line.getLineWidth());
    }
}