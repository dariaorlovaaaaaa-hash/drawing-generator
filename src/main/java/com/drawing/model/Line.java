package com.drawing.model;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, представляющий линию.
 * Линия определяется двумя точками.
 */
public class Line extends Shape {

    private Point start;
    private Point end;

    /**
     * Конструктор для создания линии.
     *
     * @param start начальная точка линии
     * @param end конечная точка линии
     * @param color цвет линии
     * @param lineWidth толщина линии
     */
    public Line(Point start, Point end, String color, double lineWidth) {
        super(color, lineWidth);
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Point> getPoints() {
        return Arrays.asList(start, end);
    }

    @Override
    public String getType() {
        return "Line";
    }

    @Override
    public double getArea() {
        return 0; // Линия не имеет площади
    }

    @Override
    public boolean containsPoint(Point point) {
        // Проверяем, лежит ли точка на линии с учетом погрешности
        double distance = distanceToLine(point);
        return distance <= 0.1; // Погрешность 0.1
    }

    /**
     * Вычисляет расстояние от точки до линии.
     *
     * @param point точка
     * @return расстояние от точки до линии
     */
    private double distanceToLine(Point point) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double x0 = point.getX();
        double y0 = point.getY();

        double numerator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return denominator == 0 ? 0 : numerator / denominator;
    }

    /**
     * Возвращает длину линии.
     *
     * @return длина линии
     */
    public double getLength() {
        return start.distanceTo(end);
    }

    /**
     * Возвращает начальную точку линии.
     *
     * @return начальная точка
     */
    public Point getStart() {
        return start;
    }

    /**
     * Возвращает конечную точку линии.
     *
     * @return конечная точка
     */
    public Point getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return String.format("Line{start=%s, end=%s, color='%s', length=%.2f}",
                start, end, color, getLength());
    }
}