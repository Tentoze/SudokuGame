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
module pl.comp.viewmodel {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.kordamp.ikonli.javafx;
    requires model;
    requires log4j;
    requires java.sql;

    opens pl.comp.viewmodel to javafx.fxml;
    exports pl.comp.viewmodel;
}