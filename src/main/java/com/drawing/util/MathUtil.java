package com.drawing.util;

/**
 * Утилитарный класс для математических операций.
 */
public class MathUtil {

    /**
     * Приватный конструктор для предотвращения создания экземпляров.
     */
    private MathUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ограничивает значение заданными пределами.
     *
     * @param value значение для ограничения
     * @param min минимальное допустимое значение
     * @param max максимальное допустимое значение
     * @return ограниченное значение
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Линейно интерполирует между двумя значениями.
     *
     * @param a начальное значение
     * @param b конечное значение
     * @param t коэффициент интерполяции (0.0-1.0)
     * @return интерполированное значение
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    /**
     * Преобразует координату из системы области в систему холста.
     *
     * @param value координата в системе области
     * @param minValue минимальное значение в системе области
     * @param maxValue максимальное значение в системе области
     * @param canvasSize размер холста
     * @return координата в системе холста
     */
    public static double mapToCanvas(double value, double minValue, double maxValue, double canvasSize) {
        double normalized = (value - minValue) / (maxValue - minValue);
        return normalized * canvasSize;
    }

    /**
     * Преобразует координату из системы холста в систему области.
     *
     * @param value координата в системе холста
     * @param minValue минимальное значение в системе области
     * @param maxValue максимальное значение в системе области
     * @param canvasSize размер холста
     * @return координата в системе области
     */
    public static double mapFromCanvas(double value, double minValue, double maxValue, double canvasSize) {
        double normalized = value / canvasSize;
        return minValue + normalized * (maxValue - minValue);
    }

    /**
     * Вычисляет расстояние между двумя точками.
     *
     * @param x1 координата X первой точки
     * @param y1 координата Y первой точки
     * @param x2 координата X второй точки
     * @param y2 координата Y второй точки
     * @return расстояние между точками
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}