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
import model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuCloneableTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        SudokuField test2 = new SudokuField();
        SudokuField [] board = new SudokuField [9];
        SudokuField [] board2 = new SudokuField [9];
        for(int i=0; i<9; i++){
            board[i] = new SudokuField(1);
            board2[i] = new SudokuField(2);
        }
        SudokuRow test3 = new SudokuRow(board);
        SudokuColumn test4 = new SudokuColumn(board);
        SudokuBox test5 = new SudokuBox(board);
        SudokuBoard testclone = test.clone();
        SudokuField test2clone = test2.clone();
        SudokuRow test3clone = test3.clone();
        SudokuColumn test4clone = test4.clone();
        SudokuBox test5clone = test5.clone();

        assertEquals(test,testclone);
        testclone.set(1,1,1);
        assertNotEquals(test,testclone);

        assertEquals(test2,test2clone);
        test2clone.setFieldValue(5);
        assertNotEquals(test2,test2clone);

        assertEquals(test3,test3clone);
        test3clone.setFields(board2);
        assertNotEquals(test3,test3clone);

        assertEquals(test4,test4clone);
        test4clone.setFields(board2);
        assertNotEquals(test,testclone);

        assertEquals(test5,test5clone);
        test5clone.setFields(board2);
        assertNotEquals(test5,test5clone);



    }
}