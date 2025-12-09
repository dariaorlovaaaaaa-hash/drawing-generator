package com.drawing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий параболу.
 * Парабола определяется уравнением y = a*x^2 + b*x + c.
 */
public class Parabola extends Shape {

    private double a;
    private double b;
    private double c;
    private double xMin;
    private double xMax;

    /**
     * Конструктор для создания параболы.
     *
     * @param a коэффициент при x^2
     * @param b коэффициент при x
     * @param c свободный коэффициент
     * @param xMin минимальное значение x для отрисовки
     * @param xMax максимальное значение x для отрисовки
     * @param color цвет параболы
     * @param lineWidth толщина линии
     */
    public Parabola(double a, double b, double c, double xMin, double xMax,
                    String color, double lineWidth) {
        super(color, lineWidth);
        this.a = a;
        this.b = b;
        this.c = c;
        this.xMin = xMin;
        this.xMax = xMax;
    }

    @Override
    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        int segments = 100; // Количество точек для аппроксимации

        double step = (xMax - xMin) / segments;

        for (int i = 0; i <= segments; i++) {
            double x = xMin + i * step;
            double y = a * x * x + b * x + c;
            points.add(new Point(x, y));
        }

        return points;
    }

    @Override
    public String getType() {
        return "Parabola";
    }

    @Override
    public double getArea() {
        // Приближенная площадь под параболой на интервале [xMin, xMax]
        int segments = 1000;
        double step = (xMax - xMin) / segments;
        double area = 0;

        for (int i = 0; i < segments; i++) {
            double x1 = xMin + i * step;
            double x2 = x1 + step;
            double y1 = a * x1 * x1 + b * x1 + c;
            double y2 = a * x2 * x2 + b * x2 + c;

            // Метод трапеций
            area += (y1 + y2) * step / 2;
        }

        return Math.abs(area);
    }

    @Override
    public boolean containsPoint(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Проверяем, лежит ли точка на параболе с погрешностью
        double parabolaY = a * x * x + b * x + c;
        return Math.abs(y - parabolaY) <= 0.5; // Погрешность 0.5
    }

    /**
     * Возвращает коэффициент a параболы.
     *
     * @return коэффициент a
     */
    public double getA() {
        return a;
    }

    /**
     * Возвращает коэффициент b параболы.
     *
     * @return коэффициент b
     */
    public double getB() {
        return b;
    }

    /**
     * Возвращает коэффициент c параболы.
     *
     * @return коэффициент c
     */
    public double getC() {
        return c;
    }

    /**
     * Возвращает вершину параболы.
     *
     * @return точка вершины параболы
     */
    public Point getVertex() {
        double xVertex = -b / (2 * a);
        double yVertex = a * xVertex * xVertex + b * xVertex + c;
        return new Point(xVertex, yVertex);
    }

    @Override
    public String toString() {
        return String.format("Parabola{y=%.2fx² + %.2fx + %.2f, x∈[%.1f,%.1f], color='%s'}",
                a, b, c, xMin, xMax, color);
    }
}