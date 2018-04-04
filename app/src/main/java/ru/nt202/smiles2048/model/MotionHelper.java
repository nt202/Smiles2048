package ru.nt202.smiles2048.model;

public class MotionHelper {

    public static float xDown, yDown, xUp, yUp;

    public static Direction getDirection() {
        float dX = Math.abs(xUp - xDown);
        float dY = Math.abs(yUp - yDown);

        if (xDown > xUp && dX > dY) return Direction.LEFT;
        else if (xDown < xUp && dX > dY) return Direction.RIGHT;
        else if (yDown > yUp && dY > dX) return Direction.UP;
        else if (yDown < yUp && dY > dX) return Direction.DOWN;
        else return Direction.NONE;
    }

}
