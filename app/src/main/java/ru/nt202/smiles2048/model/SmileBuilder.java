package ru.nt202.smiles2048.model;

public class SmileBuilder {
    private boolean isMovable;
    private int currentName;
    private int currentRow;
    private int currentColumn;
    private int destinationName;
    private int destinationRow;
    private int destinationColumn;
    private boolean isFade;
    private boolean isAppear;

    public SmileBuilder setIsMovable(boolean isMovable) {
        this.isMovable = isMovable;
        return this;
    }

    public SmileBuilder setCurrentName(int currentName) {
        this.currentName = currentName;
        return this;
    }

    public SmileBuilder setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
        return this;
    }

    public SmileBuilder setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
        return this;
    }

    public SmileBuilder setDestinationName(int destinationName) {
        this.destinationName = destinationName;
        return this;
    }

    public SmileBuilder setDestinationRow(int destinationRow) {
        this.destinationRow = destinationRow;
        return this;
    }

    public SmileBuilder setDestinationColumn(int destinationColumn) {
        this.destinationColumn = destinationColumn;
        return this;
    }

    public SmileBuilder setIsFade(boolean isFade) {
        this.isFade = isFade;
        return this;
    }

    public SmileBuilder setIsAppear(boolean isAppear) {
        this.isAppear = isAppear;
        return this;
    }

    public Smile createSmile() {
        return new Smile(isMovable, currentName, currentRow, currentColumn, destinationName, destinationRow, destinationColumn, isFade, isAppear);
    }
}