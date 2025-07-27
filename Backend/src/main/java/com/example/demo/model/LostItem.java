package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lost_items")
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "image_url", nullable = false)
  private String imageUrl;

    @Column(nullable = false)
    private String description;


    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @Column(name = "claimed", nullable = false)
    private boolean claimed = false;


    public LostItem(){}

    public LostItem(String title, String description, String imageUrl, String location) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isClaimed(){
        return claimed;
    }

    public void setClaimed(boolean claimed){
        this.claimed = claimed;
    }
}