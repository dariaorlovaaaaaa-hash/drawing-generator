package com.drawing.generator;

import com.drawing.model.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор случайных рисунков.
 */
public class DrawingGenerator {

    private static final Logger logger = LogManager.getLogger(DrawingGenerator.class);

    private final Random random;
    private final ShapeFactory shapeFactory;

    /**
     * Конструктор генератора рисунков.
     */
    public DrawingGenerator() {
        this.random = new Random();
        this.shapeFactory = new ShapeFactory();
        logger.info("Drawing generator initialized");
    }

    /**
     * Генерирует набор случайных фигур по заданным параметрам.
     * Использует все типы фигур.
     */
    public List<Shape> generateShapes(GenerationParameters parameters) {
        return generateShapes(parameters, getAllShapeTypes());
    }

    /**
     * Генерирует набор случайных фигур по заданным параметрам,
     * используя только указанные типы фигур.
     *
     * @param parameters параметры генерации
     * @param shapeTypes список типов фигур для генерации
     * @return список сгенерированных фигур
     * @throws IllegalArgumentException если параметры некорректны
     */
    public List<Shape> generateShapes(GenerationParameters parameters, List<String> shapeTypes) {
        logger.info("Starting drawing generation with parameters: {}", parameters);
        logger.info("Selected shape types: {}", shapeTypes);

        // Валидация параметров
        ParameterValidator.validate(parameters);

        // Проверяем, что есть выбранные типы фигур
        if (shapeTypes == null || shapeTypes.isEmpty()) {
            throw new IllegalArgumentException("No shape types selected for generation");
        }

        List<Shape> shapes = new ArrayList<>();
        int shapeCount = parameters.getShapeCount();
        double minX = parameters.getMinX();
        double maxX = parameters.getMaxX();
        double minY = parameters.getMinY();
        double maxY = parameters.getMaxY();
        double density = parameters.getDensity();

        // Учет кучности - сужение области для более плотного расположения
        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;
        double width = (maxX - minX) * density;
        double height = (maxY - minY) * density;

        double effectiveMinX = centerX - width / 2;
        double effectiveMaxX = centerX + width / 2;
        double effectiveMinY = centerY - height / 2;
        double effectiveMaxY = centerY + height / 2;

        logger.info("Effective generation area: x=[{}, {}], y=[{}, {}]",
                effectiveMinX, effectiveMaxX, effectiveMinY, effectiveMaxY);

        // Генерация фигур только выбранных типов
        for (int i = 0; i < shapeCount; i++) {
            try {
                Shape shape = generateSingleShape(shapeTypes, effectiveMinX, effectiveMaxX,
                        effectiveMinY, effectiveMaxY);
                if (shape != null) {
                    shapes.add(shape);
                    if (i < 10) { // Логируем только первые 10 фигур для отладки
                        logger.debug("Generated shape #{}/{}: {}", i + 1, shapeCount, shape.getType());
                    }
                } else {
                    logger.warn("Failed to generate shape #{}/{}", i + 1, shapeCount);
                }
            } catch (Exception e) {
                logger.error("Error generating shape #{}/{}: {}", i + 1, shapeCount, e.getMessage());
                // Продолжаем генерацию остальных фигур
            }
        }

        logger.info("Generation completed. Successfully created {} shapes out of {} requested",
                shapes.size(), shapeCount);
        return shapes;
    }

    /**
     * Генерирует одну случайную фигуру из указанных типов.
     */
    private Shape generateSingleShape(List<String> shapeTypes,
                                      double minX, double maxX, double minY, double maxY) {

        // Выбор случайного типа фигуры из выбранных
        if (shapeTypes.isEmpty()) {
            logger.error("Shape types list is empty!");
            return null;
        }

        String randomType = shapeTypes.get(random.nextInt(shapeTypes.size()));
        ShapeFactory.ShapeType shapeType;

        try {
            shapeType = ShapeFactory.ShapeType.valueOf(randomType);
        } catch (IllegalArgumentException e) {
            logger.error("Unknown shape type: {}. Using LINE as default", randomType);
            shapeType = ShapeFactory.ShapeType.LINE; // Используем линию как тип по умолчанию
        }

        // Генерация случайного цвета
        String color = generateRandomColor();

        // Генерация случайной толщины линии
        double lineWidth = 1.0 + random.nextDouble() * 3.0; // От 1.0 до 4.0

        try {
            // Создание фигуры через фабрику
            return shapeFactory.createShape(shapeType, minX, maxX, minY, maxY, color, lineWidth);
        } catch (Exception e) {
            logger.error("Error creating shape type {}: {}", shapeType, e.getMessage());
            return null;
        }
    }

    /**
     * Генерирует случайный цвет в формате HEX.
     *
     * @return цвет в формате #RRGGBB
     */
    private String generateRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    /**
     * Возвращает список всех доступных типов фигур.
     *
     * @return список всех типов фигур
     */
    private List<String> getAllShapeTypes() {
        List<String> allTypes = new ArrayList<>();
        for (ShapeFactory.ShapeType type : ShapeFactory.ShapeType.values()) {
            allTypes.add(type.name());
        }
        return allTypes;
    }


    public static class GenerationParameters {
        private final int shapeCount;
        private final double minX;
        private final double maxX;
        private final double minY;
        private final double maxY;
        private final double density;
        private final int gridSize;

        /**
         * Конструктор параметров генерации.
         *
         * @param shapeCount количество фигур
         * @param minX минимальная координата X
         * @param maxX максимальная координата X
         * @param minY минимальная координата Y
         * @param maxY максимальная координата Y
         * @param density кучность фигур (0.0-1.0)
         * @param gridSize размер координатной сетки
         */
        public GenerationParameters(int shapeCount, double minX, double maxX,
                                    double minY, double maxY, double density, int gridSize) {
            this.shapeCount = shapeCount;
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.density = density;
            this.gridSize = gridSize;
        }

        public int getShapeCount() {
            return shapeCount;
        }

        public double getMinX() {
            return minX;
        }

        public double getMaxX() {
            return maxX;
        }

        public double getMinY() {
            return minY;
        }

        public double getMaxY() {
            return maxY;
        }

        public double getDensity() {
            return density;
        }

        public int getGridSize() {
            return gridSize;
        }

        @Override
        public String toString() {
            return String.format(
                    "GenerationParameters{shapeCount=%d, x=[%.1f, %.1f], y=[%.1f, %.1f], density=%.2f, gridSize=%d}",
                    shapeCount, minX, maxX, minY, maxY, density, gridSize
            );
        }
    }
}