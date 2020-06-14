package com.mattjohnson.teai7_2.service;

public class CountryCategoryPair {

    private String countryField;

    private String categoryField;

    public CountryCategoryPair() {
    }

    public CountryCategoryPair(String countryField, String categoryField) {
        this.countryField = countryField;
        this.categoryField = categoryField;
    }

    public String getCountryField() {
        return countryField;
    }

    public void setCountryField(String countryField) {
        this.countryField = countryField;
    }

    public String getCategoryField() {
        return categoryField;
    }

    public void setCategoryField(String categoryField) {
        this.categoryField = categoryField;
    }
}
