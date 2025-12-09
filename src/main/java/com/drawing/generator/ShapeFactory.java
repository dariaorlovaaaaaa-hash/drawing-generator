package com.drawing.generator;

import com.drawing.model.*;
import java.util.Random;

/**
 * Фабрика для создания геометрических фигур.
 * Создает случайные фигуры в заданных пределах.
 */
public class ShapeFactory {

    private final Random random;

    /**
     * Конструктор фабрики фигур.
     */
    public ShapeFactory() {
        this.random = new Random();
    }

    /**
     * Создает случайную фигуру заданного типа в пределах указанных координат.
     *
     * @param type тип фигуры
     * @param minX минимальная координата X
     * @param maxX максимальная координата X
     * @param minY минимальная координата Y
     * @param maxY максимальная координата Y
     * @param color цвет фигуры
     * @param lineWidth толщина линии
     * @return созданная фигура
     */
    public Shape createShape(ShapeType type, double minX, double maxX,
                             double minY, double maxY, String color, double lineWidth) {

        switch (type) {
            case LINE:
                return createLine(minX, maxX, minY, maxY, color, lineWidth);
            case CIRCLE:
                return createCircle(minX, maxX, minY, maxY, color, lineWidth);
            case RECTANGLE:
                return createRectangle(minX, maxX, minY, maxY, color, lineWidth);
            case TRIANGLE:
                return createTriangle(minX, maxX, minY, maxY, color, lineWidth);
            case PARABOLA:
                return createParabola(minX, maxX, minY, maxY, color, lineWidth);
            case TRAPEZOID:
                return createTrapezoid(minX, maxX, minY, maxY, color, lineWidth);
            default:
                // В случае неизвестного типа создаем линию
                return createLine(minX, maxX, minY, maxY, color, lineWidth);
        }
    }

    /**
     * Создает случайную линию.
     */
    private Line createLine(double minX, double maxX, double minY, double maxY,
                            String color, double lineWidth) {
        Point start = createRandomPoint(minX, maxX, minY, maxY);
        Point end = createRandomPoint(minX, maxX, minY, maxY);
        return new Line(start, end, color, lineWidth);
    }

    /**
     * Создает случайную окружность.
     */
    private Circle createCircle(double minX, double maxX, double minY, double maxY,
                                String color, double lineWidth) {
        Point center = createRandomPoint(minX, maxX, minY, maxY);
        // Ограничиваем максимальный радиус
        double maxRadiusX = Math.min(maxX - center.getX(), center.getX() - minX);
        double maxRadiusY = Math.min(maxY - center.getY(), center.getY() - minY);
        double maxRadius = Math.min(maxRadiusX, maxRadiusY);

        // Радиус от 2 до половины доступного пространства
        double radius = 2.0 + random.nextDouble() * (maxRadius / 2 - 2.0);
        radius = Math.max(radius, 2.0); // Минимальный радиус 2.0

        return new Circle(center, radius, color, lineWidth);
    }

    /**
     * Создает случайный прямоугольник.
     */
    private Rectangle createRectangle(double minX, double maxX, double minY, double maxY,
                                      String color, double lineWidth) {
        Point topLeft = createRandomPoint(minX, maxX, minY, maxY);
        double maxWidth = maxX - topLeft.getX();
        double maxHeight = maxY - topLeft.getY();

        // Минимальные размеры 5x5
        double minSize = 5.0;
        if (maxWidth < minSize || maxHeight < minSize) {
            // Если не хватает места, создаем точку заново
            topLeft = createRandomPoint(minX, maxX - minSize, minY, maxY - minSize);
            maxWidth = maxX - topLeft.getX();
            maxHeight = maxY - topLeft.getY();
        }

        double width = minSize + random.nextDouble() * (maxWidth - minSize);
        double height = minSize + random.nextDouble() * (maxHeight - minSize);

        return new Rectangle(topLeft, width, height, color, lineWidth);
    }

    /**
     * Создает случайный треугольник.
     */
    private Triangle createTriangle(double minX, double maxX, double minY, double maxY,
                                    String color, double lineWidth) {
        Point p1 = createRandomPoint(minX, maxX, minY, maxY);
        Point p2 = createRandomPoint(minX, maxX, minY, maxY);
        Point p3 = createRandomPoint(minX, maxX, minY, maxY);

        return new Triangle(p1, p2, p3, color, lineWidth);
    }

    /**
     * Создает случайную параболу.
     */
    private Parabola createParabola(double minX, double maxX, double minY, double maxY,
                                    String color, double lineWidth) {
        double a = (random.nextDouble() - 0.5) * 2; // От -1 до 1
        double b = (random.nextDouble() - 0.5) * 4; // От -2 до 2
        double c = minY + random.nextDouble() * (maxY - minY);

        // Ограничиваем диапазон x для отрисовки
        double xRange = maxX - minX;
        double parabolaMinX = minX + xRange * 0.1;
        double parabolaMaxX = maxX - xRange * 0.1;

        return new Parabola(a, b, c, parabolaMinX, parabolaMaxX, color, lineWidth);
    }

    /**
     * Создает случайную трапецию.
     */
    private Trapezoid createTrapezoid(double minX, double maxX, double minY, double maxY,
                                      String color, double lineWidth) {
        double width = maxX - minX;
        double height = maxY - minY;

        // Верхнее основание
        double topY = minY + height * 0.2 + random.nextDouble() * height * 0.3;
        double topX1 = minX + width * 0.1 + random.nextDouble() * width * 0.3;
        double topX2 = topX1 + width * 0.2 + random.nextDouble() * width * 0.3;

        // Нижнее основание
        double bottomY = topY + height * 0.3 + random.nextDouble() * height * 0.3;
        double bottomX1 = minX + width * 0.2 + random.nextDouble() * width * 0.2;
        double bottomX2 = bottomX1 + width * 0.3 + random.nextDouble() * width * 0.2;

        Point topLeft = new Point(topX1, topY);
        Point topRight = new Point(topX2, topY);
        Point bottomRight = new Point(bottomX2, bottomY);
        Point bottomLeft = new Point(bottomX1, bottomY);

        return new Trapezoid(topLeft, topRight, bottomRight, bottomLeft, color, lineWidth);
    }

    /**
     * Создает случайную точку в заданных пределах.
     */
    private Point createRandomPoint(double minX, double maxX, double minY, double maxY) {
        double x = minX + random.nextDouble() * (maxX - minX);
        double y = minY + random.nextDouble() * (maxY - minY);
        return new Point(x, y);
    }

    /**
     * Перечисление типов фигур.
     */
    public enum ShapeType {
        LINE, CIRCLE, RECTANGLE, TRIANGLE, PARABOLA, TRAPEZOID
    }
}