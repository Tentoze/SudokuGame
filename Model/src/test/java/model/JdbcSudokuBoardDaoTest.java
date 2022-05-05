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

import model.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {

    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    public void writeReadTest() throws Exception, DbLoadException, DbWriteException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        board1.solveGame();
        SudokuBoard board2;
        try (
                JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.getDataBaseDao("Test1");
        ) {
            dao.write(board1, "Dodanie");
            SudokuBoard board3 = dao.read(1);
            assertNotSame(board1 , board3);
            assertEquals(board1,board3);
            dao.delete(1);
        }
    }

    @Test
    public void exceptionsTest() throws Exception {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        board1.solveGame();
        SudokuBoard board2;
        try (
                JdbcSudokuBoardDao dao = factory.getDataBaseDao("test2");
        ) {
            dao.close();
            assertThrows(DbLoadException.class,
                    () -> {
                        dao.read(7);
                    });
            assertThrows(DbWriteException.class,
                    () -> {
                        dao.write(board1,"Nieweszlo");
                    });
        }
    }
}


