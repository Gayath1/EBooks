package com.example.ebooks.models;

public class Cart {
    private String id;
    private String userId;
    private String bookId;
    private String name;
    private String author;
    private double perPrice;
    private long quantity;

    public Cart() {
    }

    public Cart(String userId, String bookId, String name, String author, double perPrice, long quantity) {
        this.userId = userId;
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.perPrice = perPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public double getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(double perPrice) {
        this.perPrice = perPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
