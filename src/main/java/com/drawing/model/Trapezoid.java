package com.drawing.model;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, представляющий трапецию.
 * Трапеция определяется четырьмя точками.
 */
public class Trapezoid extends Shape {

    private Point topLeft;
    private Point topRight;
    private Point bottomRight;
    private Point bottomLeft;

    /**
     * Конструктор для создания трапеции.
     *
     * @param topLeft верхняя левая точка
     * @param topRight верхняя правая точка
     * @param bottomRight нижняя правая точка
     * @param bottomLeft нижняя левая точка
     * @param color цвет трапеции
     * @param lineWidth толщина линии
     */
    public Trapezoid(Point topLeft, Point topRight, Point bottomRight, Point bottomLeft,
                     String color, double lineWidth) {
        super(color, lineWidth);
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    @Override
    public List<Point> getPoints() {
        return Arrays.asList(topLeft, topRight, bottomRight, bottomLeft);
    }

    @Override
    public String getType() {
        return "Trapezoid";
    }

    @Override
    public double getArea() {
        // Формула площади трапеции через координаты вершин
        double area = 0;

        // Используем формулу площади многоугольника через координаты вершин
        List<Point> points = getPoints();
        int n = points.size();

        for (int i = 0; i < n; i++) {
            Point current = points.get(i);
            Point next = points.get((i + 1) % n);
            area += current.getX() * next.getY() - next.getX() * current.getY();
        }

        return Math.abs(area) / 2.0;
    }

    @Override
    public boolean containsPoint(Point point) {
        // Разбиваем трапецию на два треугольника и проверяем принадлежность
        Triangle triangle1 = new Triangle(topLeft, topRight, bottomRight, color, lineWidth);
        Triangle triangle2 = new Triangle(topLeft, bottomRight, bottomLeft, color, lineWidth);

        return triangle1.containsPoint(point) || triangle2.containsPoint(point);
    }

    /**
     * Возвращает верхнюю левую точку трапеции.
     *
     * @return верхняя левая точка
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Возвращает верхнюю правую точку трапеции.
     *
     * @return верхняя правая точка
     */
    public Point getTopRight() {
        return topRight;
    }

    /**
     * Возвращает нижнюю правую точку трапеции.
     *
     * @return нижняя правая точка
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Возвращает нижнюю левую точку трапеции.
     *
     * @return нижняя левая точка
     */
    public Point getBottomLeft() {
        return bottomLeft;
    }

    /**
     * Вычисляет периметр трапеции.
     *
     * @return периметр трапеции
     */
    public double getPerimeter() {
        double side1 = topLeft.distanceTo(topRight);
        double side2 = topRight.distanceTo(bottomRight);
        double side3 = bottomRight.distanceTo(bottomLeft);
        double side4 = bottomLeft.distanceTo(topLeft);

        return side1 + side2 + side3 + side4;
    }

    @Override
    public String toString() {
        return String.format("Trapezoid{points=[%s, %s, %s, %s], color='%s', area=%.2f}",
                topLeft, topRight, bottomRight, bottomLeft, color, getArea());
    }
}