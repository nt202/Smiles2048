package ru.nt202.smiles2048.model;

import java.util.ArrayList;
import java.util.Random;

import ru.nt202.smiles2048.view.Smile;

import static ru.nt202.smiles2048.utils.AppConfig.MAX_COLUMNS;
import static ru.nt202.smiles2048.utils.AppConfig.MAX_ROWS;

class GameField {

    private static ArrayList<Smile> smiles;
    private static Direction direction;
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
        // initialization 2 numbers:
        addNewNumber();
        addNewNumber();
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
                createGameField();
                return smiles;
        }
        if (isMoveHappened()) {
            addNewNumber();
        } else {
            return null;
        }
        if (isGameOver()) {
            System.out.println("GAME OVER");
            return smiles;
        } else {
            createGameField();
            showField(current, shifts, destinations);
            return smiles;
        }
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

    private boolean isMoveHappened() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] != current[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGameOver() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] == 0) return false;
            }
        }
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS - 1; j++) {
                if (destinations[i][j] == destinations[i][j + 1]) return false;
            }
        }
        for (int i = 0; i < MAX_ROWS - 1; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (destinations[i][j] == destinations[i + 1][j]) return false;
            }
        }
        return true;
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
                        break;
                    }
                }
            }
        }
    }

    // Further method relates with Smile setters:
    private void createGameField() {
        int counter = 0;
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                Smile smile;
                if (smiles.size() < MAX_ROWS * MAX_COLUMNS) {
                    smile = new Smile();
                } else {
                    smile = smiles.get(counter);
                }
                smile.setSquareNumber(counter);
                smile.setMovable(isMovable(i, j));
                smile.setBlast(isBlast(i, j));
                smile.setCurrentName(current[i][j]);
                smile.setCurrentRow(i);
                smile.setCurrentColumn(j);
                smile.setDestinationName(calculateDestinationName(i, j));
                smile.setDestinationRow(calculateDestinationRow(i, j));
                smile.setDestinationColumn(calculateDestinationColumn(i, j));
                smile.setFade(isFade(i, j));
                if (isAppear(i, j)) {
                    smile.setAppear(true);
                    smile.setAppearDestinationName(calculateAppearDestinationName(i, j));
                    smile.setAppearDestinationRow(i);
                    smile.setAppearDestinationColumn(j);
                } else {
                    smile.setAppear(false);
                }
                if (smiles.size() < MAX_ROWS * MAX_COLUMNS) {
                    smiles.add(smile);
                }
                counter++;
            }
        }
    }

    private boolean isMovable(int i, int j) {
        return shifts[i][j] != 0;
    }

    private boolean isBlast(int i, int j) {
        if (isMovable(i, j)) {
            if (calculateDestinationName(i, j) > current[i][j]) {
                return true;
            }
        }
        return false;
    }

    private int calculateDestinationName(int i, int j) {
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

    private int calculateAppearDestinationName(int i, int j) {
        return appearance[i][j];
    }
}