package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class SudokuField implements Cloneable,Comparable<SudokuField> {
    private int value;
    private transient IntegerProperty valueProperty = new SimpleIntegerProperty(value);
    private boolean isEditable = false;

    public SudokuField() {

    }

    public SudokuField(int value) {
        this.value = value;
        this.valueProperty.setValue(value);
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        this.valueProperty.setValue(value);
        this.value = value;
    }

    public IntegerProperty getProperty() {
        return valueProperty;
    }

    public final void setPropertyValue(int value) {
        this.valueProperty = new SimpleIntegerProperty(value);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).toString();
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
        SudokuField tmp = (SudokuField) obj;
        return new EqualsBuilder()
                .append(value, tmp.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public int compareTo(final SudokuField field) {

        int compTmp;

        if (value > field.value) {
            compTmp = 1;
        } else if (value < field.value) {
            compTmp = -1;
        } else {
            compTmp = 0;
        }

        return compTmp;

    }

    @Override
    public SudokuField clone() {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }
}
