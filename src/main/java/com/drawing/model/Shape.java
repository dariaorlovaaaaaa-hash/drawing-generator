package com.drawing.model;

import java.util.List;

/**
 * Абстрактный класс, представляющий геометрическую фигуру.
 * Содержит общие свойства и методы для всех фигур.
 */
public abstract class Shape {

    protected String color;
    protected double lineWidth;

    /**
     * Конструктор для создания фигуры.
     *
     * @param color цвет фигуры в формате HEX
     * @param lineWidth толщина линии
     */
    public Shape(String color, double lineWidth) {
        this.color = color;
        this.lineWidth = lineWidth;
    }

    /**
     * Возвращает список точек, определяющих фигуру.
     *
     * @return список точек фигуры
     */
    public abstract List<Point> getPoints();

    /**
     * Возвращает тип фигуры.
     *
     * @return строковое представление типа фигуры
     */
    public abstract String getType();

    /**
     * Возвращает площадь фигуры.
     *
     * @return площадь фигуры
     */
    public abstract double getArea();

    /**
     * Проверяет, содержит ли фигура указанную точку.
     *
     * @param point точка для проверки
     * @return true если точка находится внутри фигуры, иначе false
     */
    public abstract boolean containsPoint(Point point);

    /**
     * Возвращает цвет фигуры.
     *
     * @return цвет в формате HEX
     */
    public String getColor() {
        return color;
    }

    /**
     * Устанавливает цвет фигуры.
     *
     * @param color новый цвет в формате HEX
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Возвращает толщину линии фигуры.
     *
     * @return толщина линии
     */
    public double getLineWidth() {
        return lineWidth;
    }

    /**
     * Устанавливает толщину линии фигуры.
     *
     * @param lineWidth новая толщина линии
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public String toString() {
        return String.format("%s{color='%s', lineWidth=%.1f}", getType(), color, lineWidth);
    }
}