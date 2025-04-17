package com.s22010075.osuhalapharmacyapp;

// DataModelClassForStoreFull - For representing the data structure stored in Firebase database.
public class DataModelClassForStoreFull {

    // variables for attributes of the database
    String name, availability, description, keywords, imageUrl;
    Float price, rating;

    // constructor for firebase without attributes
    public DataModelClassForStoreFull() {

    }

    // constructor with attributes
    public DataModelClassForStoreFull(String name, String availability, String description,
                                  String keywords, String imageUrl, Float price, Float rating) {
        this.name = name;
        this.availability = availability;
        this.description = description;
        this.keywords = keywords;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
