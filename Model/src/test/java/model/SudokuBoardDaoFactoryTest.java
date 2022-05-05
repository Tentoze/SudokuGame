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
import model.Dao;
import model.SudokuBoard;
import model.SudokuBoardDaoFactory;
import model.exceptions.LoadFromFileException;
import model.exceptions.WriteToFileException;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private Dao<SudokuBoard> fileSudokuBoardDao;
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles2.exceptions");

    @Test
    public void WritingTest() throws Exception {
        BacktrackingSudokuSolver test11 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test11);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao;
        fileSudokuBoardDao = factory.getFileDao("sudoku.txt");
        test.solveGame();
        assertTrue(fileSudokuBoardDao.write(test));

    }

    @Test
    public void ReadingTest() throws Exception {
        BacktrackingSudokuSolver test11 = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(test11);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileSudokuBoardDao;
        fileSudokuBoardDao = factory.getFileDao("sudoku.txt");
        test.solveGame();
        fileSudokuBoardDao.write(test);
        assertEquals(fileSudokuBoardDao.read(), test);
    }

    @Test
    public void readExceptionTest() {
        fileSudokuBoardDao = factory.getFileDao("name23213");
        assertThrows(LoadFromFileException.class, () -> {fileSudokuBoardDao.read();});
    }

    /*@Test
    public void writeExceptionTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        fileSudokuBoardDao = factory.getFileDao("dniadiasdaw.xtxtdgsadbkw");
        assertThrows(WriteToFileException.class, () -> {fileSudokuBoardDao.write(board1);});
    }*/
}
