package ru.nt202.smiles2048.model;

public class SmileBuilder {
    private int currentName;
    private int currentRow;
    private int currentColumn;
    private int destinationName;
    private int destinationRow;
    private int destinationColumn;
    private boolean fade;
    private boolean appear;

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

    public SmileBuilder setFade(boolean fade) {
        this.fade = fade;
        return this;
    }

    public SmileBuilder setAppear(boolean appear) {
        this.appear = appear;
        return this;
    }

    public Smile createSmile() {
        return new Smile(currentName, currentRow, currentColumn, destinationName, destinationRow, destinationColumn, fade, appear);
    }
}