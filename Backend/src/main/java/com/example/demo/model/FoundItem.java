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
    private String imageUrl;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "location", nullable = true)
    private String location;
    @Column(name = "title", nullable = true)
    private String title;

    public FoundItem() {}

    public FoundItem(String imageUrl, String description, String location, String title) {
        this.imageUrl   = imageUrl;
        this.description = description;
        this.location    = location;
        this.title       = title;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
