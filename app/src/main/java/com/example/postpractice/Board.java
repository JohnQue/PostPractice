package com.example.postpractice;

public class Board {
    String title;
    String content;
    String period;
    String gender;
    String price;
    String location;
    Long date;

    public Board(String title, String content, Long date, String period, String price, String location, String gender) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.period = period;
        this.price = price;
        this.location = location;
        this.gender = gender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
