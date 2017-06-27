package com.example.arron.gdgm.model;

import java.util.List;

/**
 * Created by J。 on 2017/4/22.
 */

public class FilterMenuModel {
    private List<String> year;
    private List<String> semester;

    public List<String> getYear() {
        return year;
    }

    public void setYear(List<String> year) {
        this.year = year;
    }

    public List<String> getSemester() {
        return semester;
    }

    public void setSemester(List<String> semester) {
        this.semester = semester;
    }
}
