package ru.nt202.smiles2048.model;

import java.util.ArrayList;
import java.util.Random;

class GameField {

    private static ArrayList<Smile> smiles;
    private static Direction direction;
    private static boolean isGameOver = false;
    private static boolean isMoved = false;
    private static final int MAX_ROWS = 4;
    private static final int MAX_COLUMNS = 4;
    private static int[][] current; // current's combinations
    private static int[][] shifts; // how many shifts current's smiles should do to destination
    private static int[][] destinations; // destinations smile's names
    private static boolean[][] appearance; // appeared smile

    GameField() {
        smiles = new ArrayList<>(MAX_ROWS * MAX_COLUMNS);
        current = new int[MAX_ROWS][MAX_COLUMNS];
        shifts = new int[MAX_ROWS][MAX_COLUMNS];
        destinations = new int[MAX_ROWS][MAX_COLUMNS];
        appearance = new boolean[MAX_ROWS][MAX_COLUMNS];
        addNewNumber(); // initialization
    }

    ArrayList<Smile> move(Direction direction) {
        GameField.direction = direction;
        clearArrays();
        putDestinationsToCurrent();
        switch (direction) {
            case UP:
                calculateMoveUp();
                break;
            case DOWN:
                calculateMoveDown();
                break;
            case LEFT:
                calculateMoveLeft();
                break;
            case RIGHT:
                calculateMoveRight();
                break;
            case NONE:
                return smiles;
        }
        calculateDifference(); // set isMoved
        if (isMoved) addNewNumber();
        addNewNumber();
//        calculateGameOver(); // set isGameOver
//        if (isGameOver) return null;
        createGameField(); // set smiles
        showField(current, shifts, destinations);
        return smiles;
    }

    private static void showField(int arr1[][], int arr2[][], int arr3[][]) {
        System.out.println("-------------------------------");
        System.out.println(arr1[0][0] + " - " + arr1[0][1] + " - " + arr1[0][2] + " - " + arr1[0][3] + "|   |" + arr2[0][0] + " - " + arr2[0][1] + " - " + arr2[0][2] + " - " + arr2[0][3] + "|   |" + arr3[0][0] + " - " + arr3[0][1] + " - " + arr3[0][2] + " - " + arr3[0][3]);
        System.out.println(arr1[1][0] + " - " + arr1[1][1] + " - " + arr1[1][2] + " - " + arr1[1][3] + "|   |" + arr2[1][0] + " - " + arr2[1][1] + " - " + arr2[1][2] + " - " + arr2[1][3] + "|   |" + arr3[1][0] + " - " + arr3[1][1] + " - " + arr3[1][2] + " - " + arr3[1][3]);
        System.out.println(arr1[2][0] + " - " + arr1[2][1] + " - " + arr1[2][2] + " - " + arr1[2][3] + "|   |" + arr2[2][0] + " - " + arr2[2][1] + " - " + arr2[2][2] + " - " + arr2[2][3] + "|   |" + arr3[2][0] + " - " + arr3[2][1] + " - " + arr3[2][2] + " - " + arr3[2][3]);
        System.out.println(arr1[3][0] + " - " + arr1[3][1] + " - " + arr1[3][2] + " - " + arr1[3][3] + "|   |" + arr2[3][0] + " - " + arr2[3][1] + " - " + arr2[3][2] + " - " + arr2[3][3] + "|   |" + arr3[3][0] + " - " + arr3[3][1] + " - " + arr3[3][2] + " - " + arr3[3][3]);
        System.out.println("-------------------------------");
    }

    private void clearArrays() {
        shifts = new int[MAX_ROWS][MAX_COLUMNS];
        appearance = new boolean[MAX_ROWS][MAX_COLUMNS];
    }

    private void putDestinationsToCurrent() {
        for (int i = 0; i < MAX_ROWS; i++) {
            System.arraycopy(destinations[i], 0, current[i], 0, MAX_COLUMNS);
        }
    }

    private void calculateMoveLeft() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS - 1; j++) {
                int temp = destinations[i][j];
                for (int n = j + 1; n < MAX_COLUMNS; n++) {
                    if (temp == 0) {
                        if (destinations[i][n] != 0) {
                            temp = destinations[i][n];
                            shifts[i][n] = shifts[i][n] + (n - j);
                            destinations[i][n] = 0;
                            destinations[i][j] = temp;
                            j--;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (destinations[i][n] == 0) {
                        continue;
                    }
                    if (destinations[i][n] != temp) {
                        break;
                    } else {
                        shifts[i][n] = shifts[i][n] + (n - j);
                        destinations[i][n] = 0;
                        destinations[i][j] = temp * 2;
                        break;
                    }
                }
            }
        }
    }

    private void calculateMoveRight() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = MAX_COLUMNS - 1; j > 0; j--) {
                int temp = destinations[i][j];
                for (int n = j - 1; n >= 0; n--) {
                    if (temp == 0) {
                        if (destinations[i][n] != 0) {
                            temp = destinations[i][n];
                            shifts[i][n] = shifts[i][n] + (j - n);
                            destinations[i][n] = 0;
                            destinations[i][j] = temp;
                            j++;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (destinations[i][n] == 0) {
                        continue;
                    }
                    if (destinations[i][n] != temp) {
                        break;
                    } else {
                        shifts[i][n] = shifts[i][n] + (j - n);
                        destinations[i][n] = 0;
                        destinations[i][j] = temp * 2;
                        break;
                    }
                }
            }
        }
    }

    private void calculateMoveUp() {
        for (int j = 0; j < MAX_COLUMNS; j++) {
            for (int i = 0; i < MAX_ROWS - 1; i++) {
                int temp = destinations[i][j];
                for (int m = i + 1; m < MAX_ROWS; m++) {
                    if (temp == 0) {
                        if (destinations[m][j] != 0) {
                            temp = destinations[m][j];
                            shifts[m][j] = shifts[m][j] + (m - i);
                            destinations[m][j] = 0;
                            destinations[i][j] = temp;
                            i--;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (destinations[m][j] == 0) {
                        continue;
                    }
                    if (destinations[m][j] != temp) {
                        break;
                    } else {
                        shifts[m][j] = shifts[m][j] + (m - i);
                        destinations[m][j] = 0;
                        destinations[i][j] = temp * 2;
                        break;
                    }
                }
            }
        }
    }

    private void calculateMoveDown() {
        for (int j = 0; j < MAX_COLUMNS; j++) {
            for (int i = MAX_ROWS - 1; i > 0; i--) {

                int temp = destinations[i][j];

                for (int m = i - 1; m >= 0; m--) {
                    if (temp == 0) {
                        if (destinations[m][j] != 0) {
                            temp = destinations[m][j];
                            shifts[m][j] = shifts[m][j] + (i - m);
                            destinations[m][j] = 0;
                            destinations[i][j] = temp;
                            i++;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (destinations[m][j] == 0) {
                        continue;
                    }
                    if (destinations[m][j] != temp) {
                        break;
                    } else {
                        shifts[m][j] = shifts[m][j] + (i - m);
                        destinations[m][j] = 0;
                        destinations[i][j] = temp * 2;
                        break;
                    }
                }
            }
        }
    }

    private void calculateDifference() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] != current[i][j]) {
                    isMoved = true;
                }
            }
        }
        isMoved = false;
    }

    private void calculateGameOver() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] == 0) isGameOver = false;
            }
        }
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS - 1; j++) {
                if (destinations[i][j] == destinations[i][j + 1]) isGameOver = false;
            }
        }
        for (int i = 0; i < MAX_ROWS - 1; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] == destinations[i + 1][j]) isGameOver = false;
            }
        }
        isGameOver = true;
    }

    private void addNewNumber() {
        int triesCount = MAX_ROWS * MAX_COLUMNS;
        while (triesCount > 0) {
            int i = new Random().nextInt(MAX_ROWS);
            int j = new Random().nextInt(MAX_COLUMNS);
            if (destinations[i][j] == 0) {
                if (Math.random() <= 0.8) {
                    destinations[i][j] = 2;
                    appearance[i][j] = true;
                } else {
                    destinations[i][j] = 4;
                    appearance[i][j] = true;
                }
                break;
            }
            triesCount--;
        }
        if (triesCount == 0) {
            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < MAX_COLUMNS; j++) {
                    if (destinations[i][j] == 0) {
                        addNewNumber();
                    } else {
                        isGameOver = true;
                        break;
                    }
                }
            }
        }
    }

    private void createGameField() {
        int counter = 0;
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                Smile smile = new SmileBuilder()
                        .setCurrentName(current[i][j])
                        .setCurrentRow(i)
                        .setCurrentColumn(j)
                        .setDestinationName(destinations[i][j])
                        .setDestinationRow(calculateDestinationRow(i, shifts[i][j]))
                        .setDestinationColumn(calculateDestinationColumn(j, shifts[i][j]))
                        .setFade(doFade(i, j))
                        .setAppear(doAppearance(i, j))
                        .createSmile();
                smiles.add(counter, smile);
                counter++;
            }
        }
    }

    private int calculateDestinationRow(int i, int value) {
        switch (direction) {
            case UP:
                return i + value;
            case DOWN:
                return i - value;
        }
        return i;
    }

    private int calculateDestinationColumn(int j, int value) {
        switch (direction) {
            case LEFT:
                return j - value;
            case RIGHT:
                return j + value;
        }
        return j;
    }

    private boolean doFade(int i, int j) {
        if (destinations[i][j] == 0 && current[i][j] != 0) return true;
        return false;
    }

    private boolean doAppearance(int i, int j) {
        return appearance[i][j];
    }
}