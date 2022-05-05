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

public class Level {
    private int fieldsToRemove;

    public Level(int fieldsToRemove) {
        this.fieldsToRemove = fieldsToRemove;
    }

    public void removeFields(SudokuBoard sudokuBoard) {
        for (int i = 0; i < fieldsToRemove; i++) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
            if (sudokuBoard.get(x, y) != 0) {
                sudokuBoard.set(x, y, 0);
            } else {
                i--;
            }
        }
    }
}
