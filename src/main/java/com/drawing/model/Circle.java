package com.drawing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий окружность.
 * Окружность определяется центром и радиусом.
 */
public class Circle extends Shape {

    private Point center;
    private double radius;

    /**
     * Конструктор для создания окружности.
     *
     * @param center центр окружности
     * @param radius радиус окружности
     * @param color цвет окружности
     * @param lineWidth толщина линии
     */
    public Circle(Point center, double radius, String color, double lineWidth) {
        super(color, lineWidth);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public List<Point> getPoints() {
        // Для отрисовки используем 36 точек по окружности
        List<Point> points = new ArrayList<>();
        int segments = 36;

        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);
            points.add(new Point(x, y));
        }

        return points;
    }

    @Override
    public String getType() {
        return "Circle";
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public boolean containsPoint(Point point) {
        double distance = center.distanceTo(point);
        return distance <= radius;
    }

    /**
     * Возвращает центр окружности.
     *
     * @return центр окружности
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Возвращает радиус окружности.
     *
     * @return радиус окружности
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Возвращает длину окружности.
     *
     * @return длина окружности
     */
    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle{center=%s, radius=%.2f, color='%s', area=%.2f}",
                center, radius, color, getArea());
    }
}