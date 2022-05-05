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

import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class SudokuAbstract {
    protected SudokuField[] fields;

    public SudokuAbstract(final SudokuField[] fields) {
        this.fields = fields;
    }


    public SudokuField[] getFields() {
        SudokuField[] tmpField = Arrays.copyOf(this.fields,9);
        return tmpField;
    }

    public void setFields(SudokuField[] fields) {
        for (int i = 0; i < 9; i++) {
            this.fields[i].setFieldValue(fields[i].getFieldValue());
        }
    }


    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (fields[i].getFieldValue() == fields[j].getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    SudokuField getField(int i) {
        return fields[i];
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("fields", fields).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SudokuAbstract tmp = (SudokuAbstract) obj;
        return new EqualsBuilder()
                .append(fields, tmp.fields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields)
                .toHashCode();
    }
}
