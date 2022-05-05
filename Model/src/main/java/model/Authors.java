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

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            {"Title", "Autorzy"},
            {"author1", "Jakub Rosiński"},
            {"author2", "Weronika Tutkaj"}
    };
}

