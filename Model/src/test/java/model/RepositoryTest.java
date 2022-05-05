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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    @Test
    void verifyTest() {
        BacktrackingSudokuSolver test11 = new BacktrackingSudokuSolver();
        SudokuBoard test1 = new SudokuBoard(test11);
        Repository testrepo = new Repository(test1);
        SudokuBoard test2 = testrepo.createInstanceSudokuBoard();
        test2.set(1,1,1);
        assertNotEquals(test1,test2);
    }


}