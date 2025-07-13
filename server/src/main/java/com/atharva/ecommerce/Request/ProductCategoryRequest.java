package com.atharva.ecommerce.Request;

public class ProductCategoryRequest {
    String categoryTitle;
    String categoryName;

    public ProductCategoryRequest() {

    }

    public ProductCategoryRequest(String categoryTitle, String categoryName) {
        this.categoryTitle = categoryTitle;
        this.categoryName = categoryName;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
