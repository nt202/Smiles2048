package ru.nt202.smiles2048.utils;

public class AppConfig {
    public static final int MAX_ROWS = 4;
    public static final int MAX_COLUMNS = 4;

    private static final double coefficient = 2;
    public static final long DURATION_MOVE = (long) (100 * coefficient); // milliseconds
    public static final long DURATION_FADE = (long) (70 * coefficient); // milliseconds
    public static final long DURATION_APPEAR = (long) (120 * coefficient); // milliseconds
}
