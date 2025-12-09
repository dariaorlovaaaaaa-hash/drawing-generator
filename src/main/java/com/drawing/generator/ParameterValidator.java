package com.drawing.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Валидатор параметров генерации.
 * Проверяет корректность входных параметров для генерации рисунков.
 */
public class ParameterValidator {

    private static final Logger logger = LogManager.getLogger(ParameterValidator.class);

    /**
     * Приватный конструктор для предотвращения создания экземпляров.
     */
    private ParameterValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Проверяет корректность параметров генерации.
     *
     * @param parameters параметры для проверки
     * @throws IllegalArgumentException если параметры некорректны
     */
    public static void validate(DrawingGenerator.GenerationParameters parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        validateShapeCount(parameters.getShapeCount());
        validateCoordinates(parameters.getMinX(), parameters.getMaxX(), "X");
        validateCoordinates(parameters.getMinY(), parameters.getMaxY(), "Y");
        validateDensity(parameters.getDensity());
        validateGridSize(parameters.getGridSize());

        logger.debug("Parameters successfully validated: {}", parameters);
    }

    /**
     * Проверяет корректность количества фигур.
     */
    private static void validateShapeCount(int shapeCount) {
        if (shapeCount <= 0) {
            throw new IllegalArgumentException("Number of shapes must be positive");
        }
        if (shapeCount > 1000) {
            throw new IllegalArgumentException("Number of shapes cannot exceed 1000");
        }
    }

    /**
     * Проверяет корректность координатного диапазона.
     */
    private static void validateCoordinates(double min, double max, String axis) {
        if (min >= max) {
            throw new IllegalArgumentException(
                    String.format("Minimum %s value (%.2f) must be less than maximum (%.2f)",
                            axis, min, max)
            );
        }
        if (Math.abs(max - min) < 1.0) {
            throw new IllegalArgumentException(
                    String.format("%s axis range is too small (minimum range: 1.0)", axis)
            );
        }
    }

    /**
     * Проверяет корректность значения кучности.
     */
    private static void validateDensity(double density) {
        if (density < 0.0 || density > 1.0) {
            throw new IllegalArgumentException("Density must be in range from 0.0 to 1.0");
        }
    }

    /**
     * Проверяет корректность размера сетки.
     */
    private static void validateGridSize(int gridSize) {
        if (gridSize <= 0) {
            throw new IllegalArgumentException("Grid size must be positive");
        }
        if (gridSize > 100) {
            throw new IllegalArgumentException("Grid size cannot exceed 100");
        }
    }
}