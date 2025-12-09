package com.drawing;

import com.drawing.gui.DrawingGUI;
import java.util.Locale;

/**
 * Main application class
 */
public class Main {
    public static void main(String[] args) {
        // Устанавливаем локаль US для гарантированного использования точки как десятичного разделителя
        Locale.setDefault(Locale.US);

        // Устанавливаем системные свойства для кодировки
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.stdout.encoding", "UTF-8");
        System.setProperty("sun.stderr.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");

        // Запускаем приложение
        DrawingGUI.main(args);
    }
}