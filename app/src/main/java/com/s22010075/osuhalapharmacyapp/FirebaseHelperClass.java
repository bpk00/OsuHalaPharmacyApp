package com.s22010075.osuhalapharmacyapp;

public class FirebaseHelperClass {

    String name, username, email, password, confirmPassword, imageUrl;

    // constructor
    public FirebaseHelperClass() {

    }

    // constructor with attributes
    public FirebaseHelperClass(String name, String username, String email, String password, String confirmPassword, String imageUrl) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.imageUrl = imageUrl;
    }

    public FirebaseHelperClass(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // getter & setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
