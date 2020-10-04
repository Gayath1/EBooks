package com.example.ebooks.models;

public class Book {
    private String id;
    private String categoryId;
    private String name;
    private String author;
    private String ABNNumber;
    private double price;
    private String imageUrl;
    private String description;

    public Book() {
    }

    public Book(String categoryId, String name, String author, String ABNNumber, double price, String imageUrl, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.author = author;
        this.ABNNumber = ABNNumber;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getABNNumber() {
        return ABNNumber;
    }

    public void setABNNumber(String ABNNumber) {
        this.ABNNumber = ABNNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
