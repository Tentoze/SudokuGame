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

package pl.comp.viewmodel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.BacktrackingSudokuSolver;
import model.Easy;
import model.Hard;
import model.JdbcSudokuBoardDao;
import model.Level;
import model.Medium;
import model.Repository;
import model.SudokuBoard;
import model.SudokuBoardDaoFactory;
import model.exceptions.DaoException;
import model.exceptions.DbLoadException;
import org.apache.log4j.Logger;


public class SudokuController {
    private final BacktrackingSudokuSolver test2 = new BacktrackingSudokuSolver();
    private final SudokuBoard test = new SudokuBoard(test2);
    private final Repository repository = new Repository(test);
    private SudokuBoard sudokuBoard = repository.createInstanceSudokuBoard();
    private final MenuController menuController;
    private final Stage stage;
    private Level level;
    private Button clickedbutton;
    private ResourceBundle bundle;
    private final Logger logger = Logger.getLogger(SudokuController.class);
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao base;


    @FXML
    private Button exitbutton;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button checkbutton;
    @FXML
    private Button saveid;
    @FXML
    private GridPane tableBoard;

    @FXML
    private void initialize() throws Exception {
        this.sudokuBoard.solveGame();

        button1.setOnAction(actionEvent -> setnumber(button1));
        button2.setOnAction(actionEvent -> setnumber(button2));
        button3.setOnAction(actionEvent -> setnumber(button3));
        button4.setOnAction(actionEvent -> setnumber(button4));
        button5.setOnAction(actionEvent -> setnumber(button5));
        button6.setOnAction(actionEvent -> setnumber(button6));
        button7.setOnAction(actionEvent -> setnumber(button7));
        button8.setOnAction(actionEvent -> setnumber(button8));
        button9.setOnAction(actionEvent -> setnumber(button9));
        checkbutton.setOnAction(actionEvent -> checkBoard());
        saveid.setOnAction(actionEvent -> {
            try {
                saveBoard();
                logger.info(bundle.getString("SUCCESS_SAVE"));
            } catch (Exception e) {
                logger.warn(bundle.getString("FAILED_SAVE"));
            }
        });

        if (menuController.getLevelFlag() == 0) {
            try {
                sudokuBoard = this.loadGame(menuController.getChosenIndex());
            } catch (IOException | DbLoadException e) {
                e.printStackTrace();
            }
            } else {

            if (menuController.getLevelFlag() == 1) {
                level = new Easy();
                logger.info(bundle.getString("CHOOSE_EASY"));
            }
            if (menuController.getLevelFlag() == 2) {
                level = new Medium();
                logger.info(bundle.getString("CHOOSE_MEDIUM"));
            }
            if (menuController.getLevelFlag() == 3) {
                logger.info(bundle.getString("CHOOSE_HARD"));
                level = new Hard();
            }
            level.removeFields(sudokuBoard);
        }
        Button button;
        StringConverter<Number> converter = new NumberStringConverter();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.sudokuBoard.get(i,j) != 0) {
                    button = createButton(String.valueOf(this.sudokuBoard.get(i,j)));
                    button.setDisable(true);
                    button.setId(String.valueOf(i + j * 9));
                } else {
                    button = createButton("");
                    Button finalButton = button;
                    button.setOnAction(actionEvent -> actualbutton(finalButton));
                    button.setFont(Font.font(0));
                    button.setId(String.valueOf(i + j * 9));

                }
                tableBoard.add(button,i,j);
                Bindings.bindBidirectional(button.textProperty(),
                        sudokuBoard.getFieldProperty(i + j * 9), converter);
            }
            }
        exitbutton.setOnAction(actionEvent -> exitaction());
        }



    public SudokuController(MenuController menuController) {
        this.menuController = menuController;
        try {
            base = factory.getDataBaseDao("DataBase");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            bundle = ResourceBundle.getBundle("bundles.text", menuController.getLocale());
            loader.setResources(bundle);
            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            stage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            stage.setTitle(bundle.getString("gameTitle"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button createButton(String label) {
        Button button = new Button(label);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    private void exitaction() {
        menuController.showStage();
        this.stage.close();
    }

    private void actualbutton(Button button) {
        if (clickedbutton != null) {
            clickedbutton.setStyle("");
        }
        clickedbutton = button;
        clickedbutton.setStyle("-fx-background-color: Red");


    }

    private void setnumber(Button button) {
        if (clickedbutton != null) {
            clickedbutton.setText(button.getText());
            clickedbutton.setStyle("");
            clickedbutton.setFont(Font.getDefault());
            int c = Integer.parseInt(clickedbutton.getId()) / 9;
            int r = Integer.parseInt(clickedbutton.getId()) % 9;
            sudokuBoard.set(r, c, Integer.parseInt(button.getText()));
        }
    }

    private void checkBoard() {
        boolean tmp = sudokuBoard.checkBoard();
        if (tmp) {
            checkbutton.setStyle("-fx-background-color: Green");
        } else {
            checkbutton.setStyle("-fx-background-color: Red");
        }
    }

    public SudokuBoard loadBoard(String name) throws Exception {
        SudokuBoardDaoFactory dao = new SudokuBoardDaoFactory();
        return dao.getFileDao(name).read();
    }

    public void saveBoard() throws CloneNotSupportedException, DaoException, SQLException {
        TextInputDialog td = new TextInputDialog(bundle.getString("_name"));
        td.setHeaderText(bundle.getString("_choseName"));
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            base.write(sudokuBoard, result.get());
        } else {
            logger.warn("Failed during saving file - file is null");
        }
    }

    private void saveBoardToFile(String name) throws Exception {
        SudokuBoardDaoFactory dao = new SudokuBoardDaoFactory();
        dao.getFileDao(name).write(sudokuBoard);
    }

    public void showStage() {
        stage.show();
    }

    public SudokuBoard loadGame(int name) throws IOException, DbLoadException {
        return base.read(name);
    }


}