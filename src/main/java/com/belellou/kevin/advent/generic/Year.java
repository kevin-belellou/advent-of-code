package com.belellou.kevin.advent.generic;

public enum Year {

    YEAR_2015("2015"),
    YEAR_2016("2016"),
    YEAR_2017("2017"),
    YEAR_2018("2018"),
    YEAR_2019("2019"),
    YEAR_2020("2020"),
    YEAR_2021("2021"),
    YEAR_2022("2022"),
    YEAR_2023("2023"),
    YEAR_2024("2024");

    private final String year;

    Year(final String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year;
    }
}
