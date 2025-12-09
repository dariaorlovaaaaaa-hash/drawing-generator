package com.drawing.model;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, представляющий треугольник.
 * Треугольник определяется тремя точками.
 */
public class Triangle extends Shape {

    private Point point1;
    private Point point2;
    private Point point3;

    /**
     * Конструктор для создания треугольника.
     *
     * @param point1 первая точка треугольника
     * @param point2 вторая точка треугольника
     * @param point3 третья точка треугольника
     * @param color цвет треугольника
     * @param lineWidth толщина линии
     */
    public Triangle(Point point1, Point point2, Point point3, String color, double lineWidth) {
        super(color, lineWidth);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    @Override
    public List<Point> getPoints() {
        return Arrays.asList(point1, point2, point3);
    }

    @Override
    public String getType() {
        return "Triangle";
    }

    @Override
    public double getArea() {
        // Формула площади треугольника через координаты вершин
        double area = Math.abs(
                point1.getX() * (point2.getY() - point3.getY()) +
                        point2.getX() * (point3.getY() - point1.getY()) +
                        point3.getX() * (point1.getY() - point2.getY())
        ) / 2.0;

        return area;
    }

    @Override
    public boolean containsPoint(Point point) {
        // Метод барицентрических координат
        double x = point.getX();
        double y = point.getY();
        double x1 = point1.getX();
        double y1 = point1.getY();
        double x2 = point2.getX();
        double y2 = point2.getY();
        double x3 = point3.getX();
        double y3 = point3.getY();

        double denominator = ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        double a = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / denominator;
        double b = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / denominator;
        double c = 1 - a - b;

        return a >= 0 && a <= 1 && b >= 0 && b <= 1 && c >= 0 && c <= 1;
    }

    /**
     * Возвращает первую точку треугольника.
     *
     * @return первая точка
     */
    public Point getPoint1() {
        return point1;
    }

    /**
     * Возвращает вторую точку треугольника.
     *
     * @return вторая точка
     */
    public Point getPoint2() {
        return point2;
    }

    /**
     * Возвращает третью точку треугольника.
     *
     * @return третья точка
     */
    public Point getPoint3() {
        return point3;
    }

    /**
     * Вычисляет периметр треугольника.
     *
     * @return периметр треугольника
     */
    public double getPerimeter() {
        double side1 = point1.distanceTo(point2);
        double side2 = point2.distanceTo(point3);
        double side3 = point3.distanceTo(point1);

        return side1 + side2 + side3;
    }

    @Override
    public String toString() {
        return String.format("Triangle{points=[%s, %s, %s], color='%s', area=%.2f}",
                point1, point2, point3, color, getArea());
    }
}