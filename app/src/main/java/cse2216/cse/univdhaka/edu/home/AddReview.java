package cse2216.cse.univdhaka.edu.home;

import com.google.firebase.database.DatabaseReference;

public class AddReview {
    String id;
    String title;
    Float star;
    String details;

    public AddReview(String id, String title, Float star, String details) {
        this.id = id;
        this.title = title;
        this.star = star;
        this.details = details;
    }

    public AddReview() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getStar() {
        return star;
    }

    public String getDetails() {
        return details;
    }
}
