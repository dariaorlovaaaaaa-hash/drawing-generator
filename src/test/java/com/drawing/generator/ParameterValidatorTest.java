package com.drawing.generator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для валидатора параметров.
 */
class ParameterValidatorTest {

    @Test
    void testValidateValidParameters() {
        DrawingGenerator.GenerationParameters parameters =
                new DrawingGenerator.GenerationParameters(10, -100, 100, -100, 100, 0.5, 10);

        // Не должно быть исключения
        assertDoesNotThrow(() -> ParameterValidator.validate(parameters));
    }

    @Test
    void testValidateNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(null);
        });
    }

    @Test
    void testInvalidShapeCount() {
        // Отрицательное количество фигур
        DrawingGenerator.GenerationParameters parameters1 =
                new DrawingGenerator.GenerationParameters(-5, -100, 100, -100, 100, 0.5, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters1);
        });

        // Слишком большое количество фигур
        DrawingGenerator.GenerationParameters parameters2 =
                new DrawingGenerator.GenerationParameters(2000, -100, 100, -100, 100, 0.5, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters2);
        });
    }

    @Test
    void testInvalidCoordinates() {
        // minX >= maxX
        DrawingGenerator.GenerationParameters parameters1 =
                new DrawingGenerator.GenerationParameters(10, 100, 50, -100, 100, 0.5, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters1);
        });

        // minY >= maxY
        DrawingGenerator.GenerationParameters parameters2 =
                new DrawingGenerator.GenerationParameters(10, -100, 100, 100, 50, 0.5, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters2);
        });

        // Слишком маленький диапазон
        DrawingGenerator.GenerationParameters parameters3 =
                new DrawingGenerator.GenerationParameters(10, 0, 0.5, 0, 0.5, 0.5, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters3);
        });
    }

    @Test
    void testInvalidDensity() {
        // Плотность < 0
        DrawingGenerator.GenerationParameters parameters1 =
                new DrawingGenerator.GenerationParameters(10, -100, 100, -100, 100, -0.1, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters1);
        });

        // Плотность > 1
        DrawingGenerator.GenerationParameters parameters2 =
                new DrawingGenerator.GenerationParameters(10, -100, 100, -100, 100, 1.1, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters2);
        });
    }

    @Test
    void testInvalidGridSize() {
        // Размер сетки <= 0
        DrawingGenerator.GenerationParameters parameters1 =
                new DrawingGenerator.GenerationParameters(10, -100, 100, -100, 100, 0.5, -5);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters1);
        });

        // Слишком большой размер сетки
        DrawingGenerator.GenerationParameters parameters2 =
                new DrawingGenerator.GenerationParameters(10, -100, 100, -100, 100, 0.5, 200);

        assertThrows(IllegalArgumentException.class, () -> {
            ParameterValidator.validate(parameters2);
        });
    }
}