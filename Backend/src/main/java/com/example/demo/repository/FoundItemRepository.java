package com.example.demo.repository;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.FoundItem;

public interface FoundItemRepository extends CrudRepository<FoundItem, Integer> {}
// This interface extends CrudRepository to provide CRUD operations for FoundItem entities.