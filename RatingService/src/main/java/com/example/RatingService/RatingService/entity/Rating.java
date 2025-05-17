package com.example.RatingService.RatingService.entity;

import org.springframework.data.annotation.Id;

public class Rating {

    @Id
    private String ratingId;

    private String userId;
    private String hotelId;
    private String feedback;
    private int rating;

    public Rating() {
    }

    public Rating(String ratingId, String userId, String hotelId, String feedback, int rating) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId='" + ratingId + '\'' +
                ", userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                '}';
    }
}
