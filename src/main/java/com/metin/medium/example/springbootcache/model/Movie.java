package com.metin.medium.example.springbootcache.model;

public class Movie {

    private Long id;
    private String name;
    private double point;

    public Movie() {
    }

    public Movie(Long id, String name, double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
