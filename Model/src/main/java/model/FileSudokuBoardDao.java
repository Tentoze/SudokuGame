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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.Scanner;
import model.exceptions.LoadFromFileException;
import model.exceptions.WriteToFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    String filename;
    File file;
    private Scanner reading;
    private PrintWriter writer;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles2.exceptions");

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public SudokuBoard read() throws LoadFromFileException {
        BacktrackingSudokuSolver test = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(test);
            try {
                reading = new Scanner(file);
                for (int i = 0;i < 9;i++) {
                    for (int j = 0; j < 9; j++) {
                        int value = Integer.parseInt(reading.nextLine());
                        sudokuBoard.set(i, j, value);
                    }
                }
                reading.close();
                } catch (IOException e) {
                LOGGER.warn("File " + filename + " not found", e);
                throw new LoadFromFileException(bundle.getString("LOAD_FROM_FILE"), e);
            }
    return sudokuBoard;
}

    public boolean write(SudokuBoard obj) throws WriteToFileException {
        try {
            writer = new PrintWriter(filename);
            for (int i = 0;i < 9;i++) {
                for (int j = 0; j < 9; j++) {
                    writer.println(obj.get(i,j));
                }
            }
            writer.close();
            return true;
            } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new WriteToFileException(bundle.getString("WRITE_TO_FILE"), e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            reading.close();
            writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

