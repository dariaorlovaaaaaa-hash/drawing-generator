package com.drawing.gui;

import com.drawing.generator.DrawingGenerator;
import com.drawing.generator.ParameterValidator;
import com.drawing.model.Point;
import com.drawing.model.Shape;
import com.drawing.util.MathUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Графический интерфейс приложения генерации рисунков.
 */
public class DrawingGUI extends Application {

    private static final Logger logger = LogManager.getLogger(DrawingGUI.class);

    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private Canvas drawingCanvas;
    private GraphicsContext gc;
    private List<Shape> currentShapes = new ArrayList<>();
    private DrawingGenerator drawingGenerator;

    // Текущие границы отображения
    private double currentMinX = -100;
    private double currentMaxX = 100;
    private double currentMinY = -100;
    private double currentMaxY = 100;

    // Элементы управления
    private TextField shapeCountField;
    private TextField minXField;
    private TextField maxXField;
    private TextField minYField;
    private TextField maxYField;
    private Slider densitySlider;
    private TextField gridSizeField;
    private Label statusLabel;

    // Чекбоксы для выбора фигур
    private CheckBox lineCheckBox;
    private CheckBox circleCheckBox;
    private CheckBox rectangleCheckBox;
    private CheckBox triangleCheckBox;
    private CheckBox parabolaCheckBox;
    private CheckBox trapezoidCheckBox;

    /**
     * Точка входа для запуска графического интерфейса.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting graphical interface application");

        try {
            drawingGenerator = new DrawingGenerator();

            // Инициализируем элементы управления ПЕРЕД их использованием
            initializeControls();
            initializeUI(primaryStage);
            primaryStage.show();
            logger.info("Graphical interface successfully initialized");
        } catch (Exception e) {
            logger.error("Error starting graphical interface: {}", e.getMessage(), e);
            showErrorDialog("Ошибка запуска", "Не удалось запустить приложение", e.getMessage());
        }
    }

    /**
     * Инициализирует элементы управления.
     */
    private void initializeControls() {
        // Инициализируем все элементы управления здесь
        shapeCountField = new TextField("20");
        minXField = new TextField("-100");
        maxXField = new TextField("100");
        minYField = new TextField("-100");
        maxYField = new TextField("100");
        densitySlider = createDensitySlider();
        gridSizeField = new TextField("10");
        statusLabel = new Label("Готов к работе");

        // Инициализируем чекбоксы для выбора фигур
        lineCheckBox = new CheckBox("Линия");
        circleCheckBox = new CheckBox("Окружность");
        rectangleCheckBox = new CheckBox("Прямоугольник");
        triangleCheckBox = new CheckBox("Треугольник");
        parabolaCheckBox = new CheckBox("Парабола");
        trapezoidCheckBox = new CheckBox("Трапеция");

        // Устанавливаем все фигуры выбранными по умолчанию
        lineCheckBox.setSelected(true);
        circleCheckBox.setSelected(true);
        rectangleCheckBox.setSelected(true);
        triangleCheckBox.setSelected(true);
        parabolaCheckBox.setSelected(true);
        trapezoidCheckBox.setSelected(true);

        // Настраиваем размеры
        shapeCountField.setPrefWidth(150);
        minXField.setPrefWidth(150);
        maxXField.setPrefWidth(150);
        minYField.setPrefWidth(150);
        maxYField.setPrefWidth(150);
        gridSizeField.setPrefWidth(150);
    }

    /**
     * Инициализирует пользовательский интерфейс.
     */
    private void initializeUI(Stage stage) {
        stage.setTitle("Генератор случайных рисунков");

        // Основной контейнер
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Панель управления
        VBox controlPanel = createControlPanel();
        root.setLeft(controlPanel);

        // Область отображения
        VBox displayArea = createDisplayArea();
        root.setCenter(displayArea);

        // Панель статуса
        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);

        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        stage.setMinWidth(DEFAULT_WIDTH);
        stage.setMinHeight(DEFAULT_HEIGHT);

        stage.setOnCloseRequest(event -> {
            logger.info("Application shutdown");
            System.exit(0);
        });
    }

    /**
     * Создает панель управления.
     */
    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(350);
        controlPanel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;");

        // Заголовок
        Label titleLabel = new Label("Параметры генерации");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Панель выбора фигур
        VBox shapesPanel = createShapesSelectionPanel();

        // Кнопки управления
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button generateButton = new Button("Сгенерировать");
        generateButton.setPrefWidth(120);
        generateButton.setOnAction(e -> generateDrawing());

        Button clearButton = new Button("Очистить");
        clearButton.setPrefWidth(120);
        clearButton.setOnAction(e -> clearCanvas());

        buttonBox.getChildren().addAll(generateButton, clearButton);

        // Добавление элементов на панель
        controlPanel.getChildren().addAll(
                titleLabel,
                createLabeledField("Количество фигур:", shapeCountField),
                createLabeledField("Минимальный X:", minXField),
                createLabeledField("Максимальный X:", maxXField),
                createLabeledField("Минимальный Y:", minYField),
                createLabeledField("Максимальный Y:", maxYField),
                createLabeledField("Кучность (0.0-1.0):", densitySlider),
                createLabeledField("Размер сетки:", gridSizeField),
                new Separator(),
                new Label("Выберите типы фигур:"),
                shapesPanel,
                new Separator(),
                buttonBox
        );

        return controlPanel;
    }

    /**
     * Создает панель для выбора фигур.
     */
    private VBox createShapesSelectionPanel() {
        VBox shapesPanel = new VBox(5);
        shapesPanel.setPadding(new Insets(10));
        shapesPanel.setStyle("-fx-background-color: #e8f4f8; -fx-border-color: #b0d4e0; -fx-border-width: 1;");

        // Первая строка чекбоксов
        HBox row1 = new HBox(10);
        row1.getChildren().addAll(lineCheckBox, circleCheckBox, rectangleCheckBox);

        // Вторая строка чекбоксов
        HBox row2 = new HBox(10);
        row2.getChildren().addAll(triangleCheckBox, parabolaCheckBox, trapezoidCheckBox);

        // Кнопки для быстрого выбора
        HBox quickButtons = new HBox(10);
        quickButtons.setAlignment(Pos.CENTER);

        Button selectAllButton = new Button("Все");
        selectAllButton.setOnAction(e -> selectAllShapes(true));

        Button deselectAllButton = new Button("Ничего");
        deselectAllButton.setOnAction(e -> selectAllShapes(false));

        Button randomSelectButton = new Button("Случайно");
        randomSelectButton.setOnAction(e -> selectRandomShapes());

        quickButtons.getChildren().addAll(selectAllButton, deselectAllButton, randomSelectButton);

        shapesPanel.getChildren().addAll(row1, row2, new Separator(), quickButtons);

        return shapesPanel;
    }

    /**
     * Создает область отображения рисунка.
     */
    private VBox createDisplayArea() {
        VBox displayArea = new VBox(10);
        displayArea.setPadding(new Insets(10));
        displayArea.setAlignment(Pos.CENTER);

        // Холст для рисования
        drawingCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = drawingCanvas.getGraphicsContext2D();

        // Панель инструментов для холста
        HBox canvasToolbar = new HBox(10);
        canvasToolbar.setAlignment(Pos.CENTER);

        Button toggleGridButton = new Button("Сетка");
        toggleGridButton.setOnAction(e -> toggleGrid());

        Button zoomInButton = new Button("+");
        zoomInButton.setOnAction(e -> zoomCanvas(1.2));

        Button zoomOutButton = new Button("-");
        zoomOutButton.setOnAction(e -> zoomCanvas(0.8));

        Button resetViewButton = new Button("Сброс");
        resetViewButton.setOnAction(e -> resetCanvasView());

        canvasToolbar.getChildren().addAll(toggleGridButton, zoomInButton, zoomOutButton, resetViewButton);

        displayArea.getChildren().addAll(canvasToolbar, drawingCanvas);

        // Очистка холста при запуске
        clearCanvas();

        return displayArea;
    }

    /**
     * Создает панель статуса.
     */
    private HBox createStatusBar() {
        HBox statusBar = new HBox(10);
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #dddddd; -fx-border-width: 1 0 0 0;");

        // statusLabel уже инициализирован в initializeControls()
        statusLabel.setStyle("-fx-text-fill: #333333;");

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(16, 16);

        statusBar.getChildren().addAll(statusLabel, new Region(), progressIndicator);
        HBox.setHgrow(statusBar.getChildren().get(1), Priority.ALWAYS);

        return statusBar;
    }

    /**
     * Создает слайдер для регулировки кучности.
     */
    private Slider createDensitySlider() {
        Slider slider = new Slider(0.0, 1.0, 0.5);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.25);
        slider.setMinorTickCount(5);
        slider.setPrefWidth(150);
        return slider;
    }

    /**
     * Создает контейнер с меткой и элементом управления.
     */
    private HBox createLabeledField(String labelText, Control control) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText);
        label.setPrefWidth(150);

        hbox.getChildren().addAll(label, control);
        return hbox;
    }

    /**
     * Выбирает или снимает выбор со всех фигур.
     */
    private void selectAllShapes(boolean select) {
        lineCheckBox.setSelected(select);
        circleCheckBox.setSelected(select);
        rectangleCheckBox.setSelected(select);
        triangleCheckBox.setSelected(select);
        parabolaCheckBox.setSelected(select);
        trapezoidCheckBox.setSelected(select);

        updateStatus(select ? "Все фигуры выбраны" : "Все фигуры сняты", "#666666");
    }

    /**
     * Случайным образом выбирает фигуры.
     */
    private void selectRandomShapes() {
        Random random = new Random();
        lineCheckBox.setSelected(random.nextBoolean());
        circleCheckBox.setSelected(random.nextBoolean());
        rectangleCheckBox.setSelected(random.nextBoolean());
        triangleCheckBox.setSelected(random.nextBoolean());
        parabolaCheckBox.setSelected(random.nextBoolean());
        trapezoidCheckBox.setSelected(random.nextBoolean());

        updateStatus("Фигуры выбраны случайным образом", "#666666");
    }

    /**
     * Получает список выбранных типов фигур.
     */
    private List<String> getSelectedShapeTypes() {
        List<String> selectedTypes = new ArrayList<>();

        if (lineCheckBox.isSelected()) selectedTypes.add("LINE");
        if (circleCheckBox.isSelected()) selectedTypes.add("CIRCLE");
        if (rectangleCheckBox.isSelected()) selectedTypes.add("RECTANGLE");
        if (triangleCheckBox.isSelected()) selectedTypes.add("TRIANGLE");
        if (parabolaCheckBox.isSelected()) selectedTypes.add("PARABOLA");
        if (trapezoidCheckBox.isSelected()) selectedTypes.add("TRAPEZOID");

        return selectedTypes;
    }

    /**
     * Генерирует новый рисунок.
     */
    private void generateDrawing() {
        try {
            logger.info("=== STARTING DRAWING GENERATION ===");
            updateStatus("Генерация...", "#FFA500");

            // Проверяем, что выбрана хотя бы одна фигура
            List<String> selectedTypes = getSelectedShapeTypes();
            logger.info("Selected shape types count: {}", selectedTypes.size());

            if (selectedTypes.isEmpty()) {
                logger.error("No shapes selected!");
                updateStatus("Ошибка: не выбрано ни одной фигуры", "#FF0000");
                showErrorDialog("Ошибка выбора", "Не выбрано ни одной фигуры",
                        "Пожалуйста, выберите хотя бы один тип фигуры для генерации.");
                return;
            }

            // Получение параметров из полей ввода - исправляем проблему с запятой
            String shapeCountText = normalizeNumber(shapeCountField.getText());
            String minXText = normalizeNumber(minXField.getText());
            String maxXText = normalizeNumber(maxXField.getText());
            String minYText = normalizeNumber(minYField.getText());
            String maxYText = normalizeNumber(maxYField.getText());
            String gridSizeText = normalizeNumber(gridSizeField.getText());

            logger.debug("Normalized inputs - shapeCount: {}, minX: {}, maxX: {}, minY: {}, maxY: {}, gridSize: {}",
                    shapeCountText, minXText, maxXText, minYText, maxYText, gridSizeText);

            int shapeCount = Integer.parseInt(shapeCountText);
            currentMinX = Double.parseDouble(minXText);
            currentMaxX = Double.parseDouble(maxXText);
            currentMinY = Double.parseDouble(minYText);
            currentMaxY = Double.parseDouble(maxYText);
            double density = densitySlider.getValue();
            int gridSize = Integer.parseInt(gridSizeText);

            logger.info("Parameters: shapes={}, X=[{}, {}], Y=[{}, {}], density={}, grid={}",
                    shapeCount, currentMinX, currentMaxX, currentMinY, currentMaxY, density, gridSize);

            // Проверяем корректность границ
            if (currentMinX >= currentMaxX) {
                throw new IllegalArgumentException("Минимальный X должен быть меньше максимального X");
            }
            if (currentMinY >= currentMaxY) {
                throw new IllegalArgumentException("Минимальный Y должен быть меньше максимального Y");
            }

            // Создание параметров генерации
            DrawingGenerator.GenerationParameters parameters =
                    new DrawingGenerator.GenerationParameters(
                            shapeCount, currentMinX, currentMaxX,
                            currentMinY, currentMaxY, density, gridSize
                    );

            // Валидация параметров
            ParameterValidator.validate(parameters);

            // Генерация фигур
            logger.info("Starting generation of {} shapes...", shapeCount);
            currentShapes = drawingGenerator.generateShapes(parameters, selectedTypes);

            logger.info("Shapes generated. Received: {} shapes", currentShapes.size());

            if (currentShapes.isEmpty()) {
                logger.warn("Failed to generate any shapes!");
                updateStatus("Не удалось сгенерировать фигуры", "#FF0000");
                return;
            }

            // Отображаем информацию о первых фигурах для отладки
            for (int i = 0; i < Math.min(3, currentShapes.size()); i++) {
                Shape shape = currentShapes.get(i);
                List<Point> points = shape.getPoints();
                logger.debug("Shape {}: type={}, color={}, points={}, lineWidth={}",
                        i + 1, shape.getType(), shape.getColor(),
                        points.size(), shape.getLineWidth());
            }

            // Очищаем холст и рисуем
            clearCanvasForRedraw();
            drawShapes();
            drawGrid(gridSize);

            logger.info("=== GENERATION AND RENDERING COMPLETED ===");
            updateStatus(String.format("Сгенерировано %d фигур", currentShapes.size()), "#008000");

        } catch (NumberFormatException e) {
            logger.error("Number format error: {}. Please use dot (.) as decimal separator", e.getMessage());
            updateStatus("Ошибка ввода данных", "#FF0000");
            showErrorDialog("Ошибка ввода", "Некорректный формат числа",
                    "Пожалуйста, используйте точку (.) как десятичный разделитель.\nПример: 10.5 вместо 10,5");
        } catch (IllegalArgumentException e) {
            logger.error("Parameter validation error: {}", e.getMessage());
            updateStatus("Ошибка параметров", "#FF0000");
            showErrorDialog("Ошибка параметров", "Некорректные параметры", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during generation: {}", e.getMessage(), e);
            updateStatus("Ошибка генерации", "#FF0000");
            showErrorDialog("Ошибка", "Ошибка при генерации", e.getMessage());
        }
    }

    /**
     * Нормализует числовую строку - заменяет запятые на точки и удаляет лишние пробелы.
     */
    private String normalizeNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "0";
        }
        return input.trim().replace(',', '.');
    }

    /**
     * Отрисовывает все фигуры на холсте.
     */
    private void drawShapes() {
        logger.debug("Starting shapes rendering. Count: {}", currentShapes.size());

        if (currentShapes.isEmpty()) {
            logger.warn("No shapes to render!");
            return;
        }

        int renderedCount = 0;

        for (Shape shape : currentShapes) {
            try {
                drawShape(shape);
                renderedCount++;
            } catch (Exception e) {
                logger.error("Error rendering shape {}: {}", shape.getType(), e.getMessage());
            }
        }

        logger.debug("Rendered {} shapes out of {}", renderedCount, currentShapes.size());
    }

    /**
     * Отрисовывает одну фигуру на холсте.
     */
    private void drawShape(Shape shape) {
        gc.setStroke(Color.web(shape.getColor()));
        gc.setLineWidth(shape.getLineWidth());

        var points = shape.getPoints();
        if (points.isEmpty()) {
            return;
        }

        // Преобразование первой точки
        double firstX = mapToCanvasX(points.get(0).getX());
        double firstY = mapToCanvasY(points.get(0).getY());

        // Начало пути
        gc.beginPath();
        gc.moveTo(firstX, firstY);

        // Добавление остальных точек
        for (int i = 1; i < points.size(); i++) {
            double x = mapToCanvasX(points.get(i).getX());
            double y = mapToCanvasY(points.get(i).getY());
            gc.lineTo(x, y);
        }

        // Замыкание пути для замкнутых фигур
        if (!shape.getType().equals("Line") && !shape.getType().equals("Parabola")) {
            gc.lineTo(firstX, firstY);
        }

        // Отрисовка
        gc.stroke();
    }

    /**
     * Преобразует координату X из системы области в систему холста.
     */
    private double mapToCanvasX(double x) {
        return MathUtil.mapToCanvas(x, currentMinX, currentMaxX, CANVAS_WIDTH);
    }

    /**
     * Преобразует координату Y из системы области в систему холста.
     */
    private double mapToCanvasY(double y) {
        double normalized = (y - currentMinY) / (currentMaxY - currentMinY);
        return CANVAS_HEIGHT - (normalized * CANVAS_HEIGHT); // Инверсия для правильной ориентации
    }

    /**
     * Отрисовывает координатную сетку.
     */
    private void drawGrid(int gridSize) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        // Вертикальные линии
        double xStep = (currentMaxX - currentMinX) / gridSize;
        for (int i = 0; i <= gridSize; i++) {
            double x = currentMinX + i * xStep;
            double canvasX = mapToCanvasX(x);

            gc.beginPath();
            gc.moveTo(canvasX, 0);
            gc.lineTo(canvasX, CANVAS_HEIGHT);
            gc.stroke();

            // Подписи
            if (canvasX > 20 && canvasX < CANVAS_WIDTH - 40) {
                gc.setFill(Color.DARKGRAY);
                gc.fillText(String.format("%.1f", x), canvasX + 2, CANVAS_HEIGHT - 2);
            }
        }

        // Горизонтальные линии
        double yStep = (currentMaxY - currentMinY) / gridSize;
        for (int i = 0; i <= gridSize; i++) {
            double y = currentMinY + i * yStep;
            double canvasY = mapToCanvasY(y);

            gc.beginPath();
            gc.moveTo(0, canvasY);
            gc.lineTo(CANVAS_WIDTH, canvasY);
            gc.stroke();

            // Подписи
            if (canvasY > 20 && canvasY < CANVAS_HEIGHT - 20) {
                gc.setFill(Color.DARKGRAY);
                gc.fillText(String.format("%.1f", y), 2, canvasY - 2);
            }
        }

        // Оси координат
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        double zeroX = mapToCanvasX(0);
        double zeroY = mapToCanvasY(0);

        if (zeroX >= 0 && zeroX <= CANVAS_WIDTH) {
            gc.beginPath();
            gc.moveTo(zeroX, 0);
            gc.lineTo(zeroX, CANVAS_HEIGHT);
            gc.stroke();
        }

        if (zeroY >= 0 && zeroY <= CANVAS_HEIGHT) {
            gc.beginPath();
            gc.moveTo(0, zeroY);
            gc.lineTo(CANVAS_WIDTH, zeroY);
            gc.stroke();
        }
    }

    /**
     * Очищает холст.
     */
    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        currentShapes.clear();
        updateStatus("Холст очищен", "#666666");
    }

    /**
     * Очищает холст для перерисовки.
     */
    private void clearCanvasForRedraw() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    /**
     * Переключает отображение сетки.
     */
    private void toggleGrid() {
        // Для отображения сетки сгенерируйте новый рисунок
        updateStatus("Для отображения сетки сгенерируйте новый рисунок", "#666666");
    }

    /**
     * Изменяет масштаб холста.
     */
    private void zoomCanvas(double factor) {
        try {
            logger.debug("Zooming canvas with factor: {}", factor);

            // Сохраняем центр текущего вида
            double centerX = (currentMinX + currentMaxX) / 2;
            double centerY = (currentMinY + currentMaxY) / 2;

            // Вычисляем новые границы
            double width = (currentMaxX - currentMinX) / factor;
            double height = (currentMaxY - currentMinY) / factor;

            // Проверяем минимальные размеры
            if (width < 1.0 || height < 1.0) {
                logger.warn("Cannot zoom further - minimum size reached");
                updateStatus("Достигнут минимальный масштаб", "#FFA500");
                return;
            }

            currentMinX = centerX - width / 2;
            currentMaxX = centerX + width / 2;
            currentMinY = centerY - height / 2;
            currentMaxY = centerY + height / 2;

            // Обновляем поля ввода
            minXField.setText(String.format("%.1f", currentMinX));
            maxXField.setText(String.format("%.1f", currentMaxX));
            minYField.setText(String.format("%.1f", currentMinY));
            maxYField.setText(String.format("%.1f", currentMaxY));

            // Перерисовываем
            clearCanvasForRedraw();
            drawShapes();

            // Рисуем сетку с текущим размером
            String gridSizeText = normalizeNumber(gridSizeField.getText());
            try {
                int gridSize = Integer.parseInt(gridSizeText);
                drawGrid(gridSize);
            } catch (NumberFormatException e) {
                drawGrid(10); // Значение по умолчанию
            }

            updateStatus(String.format("Масштаб изменен: x∈[%.1f, %.1f], y∈[%.1f, %.1f]",
                    currentMinX, currentMaxX, currentMinY, currentMaxY), "#666666");

        } catch (Exception e) {
            logger.error("Error during zoom: {}", e.getMessage());
            updateStatus("Ошибка масштабирования", "#FF0000");
        }
    }

    /**
     * Сбрасывает вид холста.
     */
    private void resetCanvasView() {
        currentMinX = -100;
        currentMaxX = 100;
        currentMinY = -100;
        currentMaxY = 100;

        minXField.setText("-100");
        maxXField.setText("100");
        minYField.setText("-100");
        maxYField.setText("100");

        clearCanvas();
        updateStatus("Вид сброшен", "#666666");
    }

    /**
     * Обновляет статусную строку.
     */
    private void updateStatus(String message, String color) {
        if (statusLabel != null) {
            statusLabel.setText(message);
            statusLabel.setStyle(String.format("-fx-text-fill: %s;", color));
        }
    }

    /**
     * Показывает диалоговое окно с ошибкой.
     */
    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}