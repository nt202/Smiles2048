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
    private static int[][] appearance; // appeared smile

    GameField() {
        smiles = new ArrayList<>(MAX_ROWS * MAX_COLUMNS);
        current = new int[MAX_ROWS][MAX_COLUMNS];
        shifts = new int[MAX_ROWS][MAX_COLUMNS];
        destinations = new int[MAX_ROWS][MAX_COLUMNS];
        appearance = new int[MAX_ROWS][MAX_COLUMNS];
        addNewNumber(); // initialization
    }

    ArrayList<Smile> move(Direction direction) {
        GameField.direction = direction;
        putDestinationsToCurrent();
        clearArrays();
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
//        if (isMoved) addNewNumber();
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
        appearance = new int[MAX_ROWS][MAX_COLUMNS];
    }

    private void putDestinationsToCurrent() {
        for (int i = 0; i < MAX_ROWS; i++) {
            System.arraycopy(destinations[i], 0, current[i], 0, MAX_COLUMNS);
        }
//        for (int i = 0; i < MAX_ROWS; i++) {
//            for (int j = 0; j < MAX_COLUMNS; j++) {
//                current[i][j] = destinations[i][j];
//                if (appearance[i][j] != 0) current[i][j] = appearance[i][j];
//            }
//        }
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
                    appearance[i][j] = 2;
                } else {
                    destinations[i][j] = 4;
                    appearance[i][j] = 4;
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
                        .setIsMovable(isMovable(i, j))
                        .setCurrentName(current[i][j])
                        .setCurrentRow(i)
                        .setCurrentColumn(j)
                        .setDestinationName(calculateDestinationName(i, j))
                        .setDestinationRow(calculateDestinationRow(i, j))
                        .setDestinationColumn(calculateDestinationColumn(i, j))
                        .setIsFade(isFade(i, j))
                        .setIsAppear(isAppear(i, j))
                        .createSmile();
                smiles.add(counter, smile);
                counter++;
            }
        }
    }

    private boolean isMovable(int i, int j) {
        return shifts[i][j] != 0;
    }

    private int calculateDestinationName(int i, int j) {
//        if (appearance[i][j] != 0) return appearance[i][j];
        int i1 = calculateDestinationRow(i, j);
        int j1 = calculateDestinationColumn(i, j);
        return destinations[i1][j1];
    }

    private int calculateDestinationRow(int i, int j) {
        int value = shifts[i][j];
        switch (direction) {
            case UP:
                return i - value;
            case DOWN:
                return i + value;
        }
        return i;
    }

    private int calculateDestinationColumn(int i, int j) {
        int value = shifts[i][j];
        switch (direction) {
            case LEFT:
                return j - value;
            case RIGHT:
                return j + value;
        }
        return j;
    }

    private boolean isFade(int i, int j) {
        if (current[i][j] != 0) {
            int i1 = calculateDestinationRow(i, j);
            int j1 = calculateDestinationColumn(i, j);
            if (destinations[i1][j1] > current[i][j]) {
                // Далее очень сложная логика, даже не пытайтесь понять.
                switch (direction) {
                    case UP:
                        if (i == 0) return true;
                        if (i < MAX_ROWS - 1) {
                            boolean isOdd = false;
                            for (int row = 0; row < i; row++) {
                                if (current[row][j] == 0) continue;
                                if (current[row][j] == current[i][j]) {
                                    int i2 = calculateDestinationRow(row, j);
                                    if (destinations[i2][j] == destinations[i1][j]) {
                                        isOdd = !isOdd;
                                    }
                                } else {
                                    isOdd = false;
                                }
                            }
                            if (isOdd) {
                                return false;
                            } else {
                                for (int row = i + 1; row <= MAX_ROWS - 1; row++) {
                                    if (current[row][j] == 0) continue;
                                    if (current[row][j] == current[i][j]) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    case DOWN:
                        if (i == MAX_ROWS - 1) return true;
                        if (i > 0) {
                            boolean isOdd = false;
                            for (int row = MAX_ROWS - 1; row > i; row--) {
                                if (current[row][j] == 0) continue;
                                if (current[row][j] == current[i][j]) {
                                    int i2 = calculateDestinationRow(row, j);
                                    if (destinations[i2][j] == destinations[i1][j]) {
                                        isOdd = !isOdd;
                                    }
                                } else {
                                    isOdd = false;
                                }
                            }
                            if (isOdd) {
                                return false;
                            } else {
                                for (int row = i - 1; row >= 0; row--) {
                                    if (current[row][j] == 0) continue;
                                    if (current[row][j] == current[i][j]) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    case LEFT:
                        if (j == 0) return true;
                        if (j < MAX_COLUMNS - 1) {
                            boolean isOdd = false;
                            for (int column = 0; column < j; column++) {
                                if (current[i][column] == 0) continue;
                                if (current[i][column] == current[i][j]) {
                                    int j2 = calculateDestinationColumn(i, column);
                                    if (destinations[i][j2] == destinations[i][j1]) {
                                        isOdd = !isOdd;
                                    }
                                } else {
                                    isOdd = false;
                                }
                            }
                            if (isOdd) {
                                return false;
                            } else {
                                for (int column = j + 1; column <= MAX_ROWS - 1; column++) {
                                    if (current[i][column] == 0) continue;
                                    if (current[i][column] == current[i][j]) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    case RIGHT:
                        if (j == MAX_COLUMNS - 1) return true;
                        if (j > 0) {
                            boolean isOdd = false;
                            for (int column = MAX_COLUMNS - 1; column > j; column--) {
                                if (current[i][column] == 0) continue;
                                if (current[i][column] == current[i][j]) {
                                    int j2 = calculateDestinationColumn(i, column);
                                    if (destinations[i][j2] == destinations[i][j1]) {
                                        isOdd = !isOdd;
                                    }
                                } else {
                                    isOdd = false;
                                }
                            }
                            if (isOdd) {
                                return false;
                            } else {
                                for (int column = j - 1; column >= 0; column--) {
                                    if (current[i][column] == 0) continue;
                                    if (current[i][column] == current[i][j]) {
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                }
            }
        }
        return false;
    }

    private boolean isAppear(int i, int j) {
        return appearance[i][j] != 0;
    }
}