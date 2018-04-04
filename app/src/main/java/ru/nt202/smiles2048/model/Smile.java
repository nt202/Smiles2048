package ru.nt202.smiles2048.model;

public class Smile {

    private String currentName;
    private int currentRow, currentColumn;

    private String destinationName;
    private int destinationRow, destinationColumn;

    private boolean fade;

    private boolean appear;

    public Smile(int currentName,
                 int currentRow,
                 int currentColumn,
                 int destinationName,
                 int destinationRow,
                 int destinationColumn,
                 boolean fade,
                 boolean appear) {
//        this.currentName = "R.drawable.ic_" + currentName;
        this.currentName = "ic_" + currentName;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
        this.destinationName = "ic_" + destinationName;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.fade = fade;
        this.appear = appear;
    }

    public String getCurrentName() {
        return currentName;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    public int getDestinationColumn() {
        return destinationColumn;
    }

    public boolean isFade() {
        return fade;
    }

    public boolean isAppear() {
        return appear;
    }

    @Override
    public String toString() {
        return "Smile{" +
                "currentName='" + currentName + '\'' +
                ", currentRow=" + currentRow +
                ", currentColumn=" + currentColumn +
                ", destinationName='" + destinationName + '\'' +
                ", destinationRow=" + destinationRow +
                ", destinationColumn=" + destinationColumn +
                ", fade=" + fade +
                ", appear=" + appear +
                '}';
    }
}
