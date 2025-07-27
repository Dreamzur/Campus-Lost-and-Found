package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "image_url", nullable = false)
    private String image_url;

    @Column(nullable = false)
    private String description;


    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @Column(name = "claimed", nullable = false)
    private boolean claimed = false;



    public FoundItem(){}


    public FoundItem(String imageUrl, String description, String location, String title) {
        this.imageUrl   = imageUrl;
        this.description = description;
        this.location    = location;
        this.title       = title;
    }

    // getters & setters

    public Long getId() {
        return id;
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isClaimed(){
        return claimed;
    }

    public void setClaimed(boolean claimed){
        this.claimed = claimed;
    }


}
