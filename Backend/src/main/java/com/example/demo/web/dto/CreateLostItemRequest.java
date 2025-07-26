package com.example.demo.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateLostItemRequest(
    String description,
    @JsonProperty("image_url") String imageUrl,
    String location,
    String title
) {}