package model; /**
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

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements Cloneable {

    private final SudokuSolver sudokuSolver;
    private final List<SudokuField> board = Arrays.asList(new SudokuField[81]);

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;

        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField());
        }
    }

    public IntegerProperty getFieldProperty(int index) {
        return board.get(index).getProperty();
    }

    public boolean solveGame() {
        return sudokuSolver.solve(this);
    }

    public int get(int row, int col) {
        return  board.get(row + col * 9).getFieldValue();
    }



    public void set(int row, int col, int num) {
        board.get(row + col * 9).setFieldValue(num);
    }

    public boolean checkBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int num = board.get(row + col * 9).getFieldValue();
                if (num == 0) {
                    return false;
                }
                for (int c = 0; c < 9; c++) {
                    if (board.get(row + c * 9).getFieldValue() == num && col != c) {
                            return false;
                    }
                }
                for (int r = 0; r < 9; r++) {
                    if (board.get(r + col * 9).getFieldValue() == num && row != r) {
                            return false;
                    }
                }

                int squarerowbeg = row - row % 3;
                int squarecolbeg = col - col % 3;
                for (int r = squarerowbeg; r < squarerowbeg + 3; r++) {
                    for (int c = squarecolbeg; c < squarecolbeg + 3; c++) {
                        if (board.get(r + c * 9).getFieldValue() == num && row != r && col != c) {
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public int[][] copyBoard() {
        int[][] copyBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copyBoard[i][j] = board.get(i + j * 9).getFieldValue();
            }
        }
        return copyBoard;
    }

    public SudokuRow getRow(int y) {
        SudokuField[] fields = new SudokuField[9];
        SudokuRow row = new SudokuRow(fields);
        for (int i = 0; i < 9; i++) {
            fields[i] = board.get(y + i * 9);
        }
        row.setFields(fields);
        return row;
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
        SudokuColumn column = new SudokuColumn(fields);
        for (int i = 0; i < 9; i++) {
            fields[i] = board.get(i + x * 9);
        }
        column.setFields(fields);
        return column;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] fields = new SudokuField[9];
        SudokuBox box = new SudokuBox(fields);
        int squarerowbeg = x - x % 3;
        int squarecolbeg = y - y % 3;
        int index = 0;
        for (int r = squarerowbeg; r < squarerowbeg + 3; r++) {
            for (int c = squarecolbeg; c < squarecolbeg + 3; c++) {
                fields[index++] = board.get(squarerowbeg + r + (squarecolbeg + c) * 9);
            }
        }
        box.setFields(fields);
        return box;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("board", board).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SudokuBoard tmp = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(board, tmp.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        BacktrackingSudokuSolver back = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard(back);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                sb.set(i, j, board.get(i + 9 * j).clone().getFieldValue());
            }
        }
        return sb;
    }

    public SudokuField getField(int x, int y) {
        return board.get(x + y * 9);
    }
}
