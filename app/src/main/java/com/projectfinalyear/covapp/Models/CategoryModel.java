package com.projectfinalyear.covapp.Models;

public class CategoryModel {
    String categoryId;
    String name;
    String url;

    public CategoryModel() {
    }

    public CategoryModel(String categoryId, String name, String url) {
        this.categoryId = categoryId;
        this.name = name;
        this.url = url;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
