package com.drawing.model;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, представляющий прямоугольник.
 * Прямоугольник определяется верхней левой точкой, шириной и высотой.
 */
public class Rectangle extends Shape {

    private Point topLeft;
    private double width;
    private double height;

    /**
     * Конструктор для создания прямоугольника.
     *
     * @param topLeft верхняя левая точка прямоугольника
     * @param width ширина прямоугольника
     * @param height высота прямоугольника
     * @param color цвет прямоугольника
     * @param lineWidth толщина линии
     */
    public Rectangle(Point topLeft, double width, double height, String color, double lineWidth) {
        super(color, lineWidth);
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public List<Point> getPoints() {
        Point topRight = new Point(topLeft.getX() + width, topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + height);
        Point bottomRight = new Point(topLeft.getX() + width, topLeft.getY() + height);

        return Arrays.asList(topLeft, topRight, bottomRight, bottomLeft);
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public boolean containsPoint(Point point) {
        double x = point.getX();
        double y = point.getY();
        double left = topLeft.getX();
        double right = left + width;
        double top = topLeft.getY();
        double bottom = top + height;

        return x >= left && x <= right && y >= top && y <= bottom;
    }

    /**
     * Возвращает верхнюю левую точку прямоугольника.
     *
     * @return верхняя левая точка
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Возвращает ширину прямоугольника.
     *
     * @return ширина прямоугольника
     */
    public double getWidth() {
        return width;
    }

    /**
     * Возвращает высоту прямоугольника.
     *
     * @return высота прямоугольника
     */
    public double getHeight() {
        return height;
    }

    /**
     * Вычисляет периметр прямоугольника.
     *
     * @return периметр прямоугольника
     */
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("Rectangle{topLeft=%s, width=%.2f, height=%.2f, color='%s', area=%.2f}",
                topLeft, width, height, color, getArea());
    }
}