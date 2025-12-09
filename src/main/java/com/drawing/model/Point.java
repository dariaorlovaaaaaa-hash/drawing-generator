package com.drawing.model;

import java.io.Serializable;

/**
 * Класс, представляющий точку в двумерном пространстве.
 */
public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;

    /**
     * Конструктор для создания точки с заданными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату X точки.
     *
     * @return координата X
     */
    public double getX() {
        return x;
    }

    /**
     * Устанавливает координату X точки.
     *
     * @param x новая координата X
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Возвращает координату Y точки.
     *
     * @return координата Y
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает координату Y точки.
     *
     * @param y новая координата Y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Вычисляет расстояние до другой точки.
     *
     * @param other другая точка
     * @return расстояние между точками
     */
    public double distanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) * 31 + Double.hashCode(y);
    }
}