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
import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuColumn;
import model.SudokuField;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    @Test
    public void correctNumsGenerateTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);

        test.solveGame();
        boolean pom = test.checkBoard();
        assertTrue(pom);
        BacktrackingSudokuSolver test22 = new BacktrackingSudokuSolver();
        SudokuBoard test2 = new SudokuBoard(test22);
        test2.set(0,5,1);
        test2.set(0,6,1);
        assertFalse(test2.solveGame());
    }

    @Test
    public void differentNumsGenerateTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        test.solveGame();
        BacktrackingSudokuSolver test22 = new BacktrackingSudokuSolver();
        SudokuBoard test2 = new SudokuBoard(test22);
        test2.solveGame();
        int[][] board = test.copyBoard();
        int[][] board2 = test2.copyBoard();
        assertFalse(Arrays.deepEquals(board, board2));
    }

    @Test
    void get() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        test.set(5,6,9);
        int testnumber = 9;
        assertEquals(testnumber,test.get(5,6));

    }

    @Test
    void checkboard() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        test.solveGame();

        assertTrue(test.checkBoard());
        test.set(1,1,1);
        test.set(1,2,1);
        assertFalse(test.checkBoard());
        BacktrackingSudokuSolver test22 = new BacktrackingSudokuSolver();
        SudokuBoard test2 = new SudokuBoard(test22);
        test2.solveGame();
        test2.set(0,0,1);
        test2.set(0,1,2);
        test2.set(0,2,3);
        test2.set(0,3,4);
        test2.set(0,4,5);
        test2.set(0,5,6);
        test2.set(0,6,7);
        test2.set(0,7,8);
        test2.set(0,8,9);
        test2.set(1,0,1);
        test2.set(1,1,1);
        assertFalse(test2.checkBoard());
        BacktrackingSudokuSolver test33 = new BacktrackingSudokuSolver();
        SudokuBoard test3 = new SudokuBoard(test33);
        test3.set(0,0,1);
        test3.set(0,1,2);
        test3.set(0,2,3);
        test3.set(0,3,4);
        test3.set(0,4,5);
        test3.set(0,5,6);
        test3.set(0,6,7);
        test3.set(0,7,8);
        test3.set(0,8,9);
        test3.set(1,0,3);
        test3.set(1,1,1);
        assertFalse(test3.checkBoard());
    }

    @Test
    void getColumnTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        SudokuField[] fields;
        test.solveGame();
        assertNotNull(test.getColumn(3));
        fields = test.getColumn(7).getFields();
        assertEquals(test.get(0,7),fields[0].getFieldValue());


    }

    @Test
    void ColumnTest(){
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        test.solveGame();
        test.set(1,1,1);
        test.set(2,1,1);
        assertFalse(test.getColumn(1).verify());
    }

    @Test
    void getRowTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        assertNotNull(test.getRow(8));
    }

    @Test
    void getBoxTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        assertNotNull(test.getBox(3, 4));
    }
    @Test
    void hashCodeTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        BacktrackingSudokuSolver test22 = new BacktrackingSudokuSolver();
        SudokuBoard test2 = new SudokuBoard(test22);
        for(int j=0; j<9;j++)
            for(int i=0; i<9 ; i++)
            {
                test.set(i,j,1);
                test2.set(i,j,1);
            }
        assertEquals(test.hashCode(),test2.hashCode());
    }
    @Test
    void toStringTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        test.solveGame();
        assertEquals(test.toString(),test.toString());
        SudokuField [] board = new SudokuField [9];
        SudokuField [] board2 = new SudokuField [9];
        for(int i=0; i<9; i++) {
            board[i] = new SudokuField();
            board2[i] = new SudokuField(1);
        }
        SudokuColumn col1 = new SudokuColumn(board);
        col1.setFields(board2);
        assertEquals(col1.toString(),col1.toString());
    }
    @Test
    void equalsTest() {
        BacktrackingSudokuSolver test1 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test1);
        BacktrackingSudokuSolver test22 = new BacktrackingSudokuSolver();
        SudokuBoard test2 = new SudokuBoard(test22);
        SudokuBoard test3 = null;
        for(int j=0; j<9;j++) {
            for (int i = 0; i < 9; i++) {
                test.set(i, j, 1);
                test2.set(i, j, 0);
            }
        }
        assertFalse(test.equals(test2));
        for(int j=0; j<9;j++) {
            for (int i = 0; i < 9; i++) {
                test2.set(i, j, 1);
            }
        }
        assertTrue(test.equals(test2));
        assertEquals(test, test);
        assertFalse(test.equals(test3));

        SudokuField test4 = new SudokuField();
        SudokuField test5 = new SudokuField();
        SudokuField test6 = null;
        test4.setFieldValue(3);
        assertFalse(test.equals(test4));
        test5.setFieldValue(3);
        assertTrue(test4.equals(test5));
        assertTrue(test4.equals(test4));
        assertFalse(test4.equals(test6));
        assertFalse(test4.equals(test2));
        test5.setFieldValue(4);
        assertFalse(test4.equals(test5));
        SudokuField [] board = new SudokuField [9];
        SudokuField [] board2 = new SudokuField [9];
        for(int i=0; i<9; i++){
            board[i] = new SudokuField(1);
            board2[i] = new SudokuField(2);
        }
        SudokuColumn col1 = new SudokuColumn(board);
        SudokuColumn col2 = new SudokuColumn(board2);
        SudokuColumn col3 = null;
        assertFalse(col1.equals(col2));
        for(int i=0; i<9; i++){
            board2[i].setFieldValue(1);
        }
        assertTrue(col1.equals(col2));
        assertTrue(col1.equals(col1));
        assertFalse(col1.equals(col3));
        assertFalse(col1.equals(test1));

    }
}