package com.atharva.ecommerce.Request;

public class ReviewRequest {
    private Long ProductId;
    private String review;

    public ReviewRequest() {}

    public ReviewRequest(Long productId, String review) {
        ProductId = productId;
        this.review = review;
    }

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
