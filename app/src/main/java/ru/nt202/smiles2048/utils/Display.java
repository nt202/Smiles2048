package ru.nt202.smiles2048.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Display {
    public static int getWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }
}
