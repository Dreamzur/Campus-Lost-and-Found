package com.example.demo.repository;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.LostItem;

public interface LostItemRepository extends CrudRepository<LostItem, Integer> {}
// This interface extends CrudRepository to provide CRUD operations for LostItem entities.