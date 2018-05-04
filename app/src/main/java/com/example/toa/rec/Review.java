package com.example.toa.rec;

public class Review {
    String courseName;
    String instructorName;
    String reviewText;
    double starRating;

    public Review() {
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.reviewText = reviewText;
        this.starRating = starRating;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public double getStarRating() {
        return starRating;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }
}
