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
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.JdbcSudokuBoardDao;
import model.SudokuBoardDaoFactory;
import org.apache.log4j.Logger;

public class MenuController {
    private final Stage thisStage;
    private int levelFlag;
    private String loadFilePath;
    Locale locale = new Locale("pl");
    public ResourceBundle bundle = ResourceBundle.getBundle("bundles.text", locale);
    public ResourceBundle bundle2 = ResourceBundle.getBundle("model.Authors", locale);
    private final Logger logger = Logger.getLogger(MenuController.class);
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao base;
    int chosenIndex;

    @FXML
    private Button easybutton;
    @FXML
    private Button mediumbutton;
    @FXML
    private Button hardbutton;
    @FXML
    private Button  loadid;
    @FXML
    private Button langen;
    @FXML
    private Button langpl;
    @FXML
    private Label authorslabel;
    @FXML
    private Label author1label;
    @FXML
    private Label author2label;
    @FXML
    private Button deletebutton;

    @FXML
    private void initialize() {
        loadid.setOnAction(actionEvent -> {
            try {
                loadFile();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        easybutton.setOnAction(actionEvent -> openBoard(1));
        mediumbutton.setOnAction(actionEvent -> openBoard(2));
        hardbutton.setOnAction(actionEvent -> openBoard(3));
        deletebutton.setOnAction(actionEvent -> {
            try {
                delFile();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        authorslabel.setText(bundle2.getString("Title"));
        author1label.setText(bundle2.getString("author1"));
        author2label.setText(bundle2.getString("author2"));
        langen.setOnAction(actionEvent -> {
            try {
                changeLanguage("en");
                logger.info(bundle.getString("CHANGE_LANGUAGE"));
            } catch (IOException e) {
                logger.info(bundle.getString("FAILED_CHANGE_LANGUAGE"));
            }
        });
        langpl.setOnAction(actionEvent -> {
            try {
                changeLanguage("pl");
                logger.info(bundle.getString("CHANGE_LANGUAGE"));
            } catch (IOException e) {
                logger.info(bundle.getString("FAILED_CHANGE_LANGUAGE"));
            }
        });
    }

    public MenuController() throws SQLException {
        thisStage = new Stage();
        base = factory.getDataBaseDao("DataBase");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));


            // Set this class as the controller
            loader.setController(this);
            loader.setResources(bundle);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle(bundle.getString("applicationTitle"));

        } catch (IOException e) {
            logger.error("Error while setting scene", e);
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    public int getLevelFlag() {
        return levelFlag;
    }

    public String getLoadFilePath() {
        return loadFilePath;
    }

    public void setLevelFlag(int levelFlag) {
        this.levelFlag = levelFlag;
    }

    public void openBoard(int levelFlag) {
        setLevelFlag(levelFlag);
        SudokuController sudokuController = new SudokuController(this);
        thisStage.close();
        sudokuController.showStage();
    }

    public void loadFile() throws SQLException {
        List<String> names = base.getBoardNames();
        ChoiceDialog<String> chd = new ChoiceDialog<String>(names.get(0), names);
        chd.setHeaderText(bundle.getString("_loading"));
        Optional<String> result = chd.showAndWait();
        if (result.isPresent()) {
            List<Integer> indexes = base.getBoardIndexes();
            chosenIndex = indexes.get(names.indexOf(result.get()));
            openBoard(0);
            logger.info(bundle.getString("LOAD"));
        } else {
            logger.warn("Failed during loading file - file is null");
        }
    }

    public void delFile() throws SQLException {
        List<Integer> indexes = base.getBoardIndexes();
        List<String> names = base.getBoardNames();
        ChoiceDialog<String> chd = new ChoiceDialog<String>(names.get(0), names);
        chd.setHeaderText(bundle.getString("_delete"));
        Optional<String> result = chd.showAndWait();
        if (result.isPresent()) {
            base.delete(indexes.get(names.indexOf(result.get())));
        } else {
            logger.warn("Failed during deleting file - file is null");
        }
    }

    public void changeLanguage(String language) throws IOException {
        this.locale = new Locale(language);
        bundle2 = ResourceBundle.getBundle("model.Authors", locale);
        bundle = ResourceBundle.getBundle("bundles.text", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"),bundle);
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
    }

    public int getChosenIndex() {
        return chosenIndex;
    }

    public Locale getLocale() {
        return locale;
    }
}
