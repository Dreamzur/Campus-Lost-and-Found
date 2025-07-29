package com.example.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFoundItemRequest {
  private String description;
  @JsonProperty("image_url")
  private String imageUrl;
  private String location;
  private String title;

  public CreateFoundItemRequest(String description, @JsonProperty("image_url") String imageUrl, String location,
      String title) {
    this.description = description;
    this.imageUrl = imageUrl;
    this.location = location;
    this.title = title;
  }

}