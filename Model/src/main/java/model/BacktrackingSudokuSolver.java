/**
 *     Copyright (C) 2021  Weronika Tutkaj, Jakub Rosinski
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.
 */

package model;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    Random generator = new Random();

    public boolean check(int row, int col, int num, SudokuBoard board) {
        for (int c = 0; c < 9; c++) {
            if (board.get(row, c) == num && col != c) {
                return false;
            }
        }
        for (int r = 0; r < 9; r++) {
            if (board.get(r, col) == num && row != r) {
                return false;
            }
        }

        int squarerowbeg = row - row % 3;
        int squarecolbeg = col - col % 3;
        for (int r = squarerowbeg; r < squarerowbeg + 3; r++) {
            for (int c = squarecolbeg; c < squarecolbeg + 3; c++) {
                if (board.get(r, c) == num && row != r && col != c) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solvesudoku(int n, SudokuBoard board) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board.get(i, j) == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= n; num++) {
            if (check(row, col, num, board)) {
                board.set(row, col, num);
                if (solvesudoku(n, board)) {
                    return true;
                } else {
                    board.set(row, col, 0);
                }
            }
        }
        return false;
    }

    public boolean generatorsudoku(SudokuBoard board) {
        int number;
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    number = generator.nextInt(9) + 1;
                    flag = check(i, j, number, board);
                } while (!flag);
                board.set(i, j, number);
                flag = false;
            }
        }
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                do {
                    number = generator.nextInt(9) + 1;
                    flag = check(i, j, number, board);
                } while (!flag);
                board.set(i, j, number);
                flag = false;
            }
        }
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                do {
                    number = generator.nextInt(9) + 1;
                    flag = check(i, j, number, board);
                } while (!flag);
                board.set(i, j, number);
                flag = false;
            }
        }

        return true;
    }

    @Override
    public boolean solve(SudokuBoard board) {
        int n = 9;
        generatorsudoku(board);
        if (solvesudoku(n, board)) {
            return true;
        } else {
            return false;
        }
    }
}
