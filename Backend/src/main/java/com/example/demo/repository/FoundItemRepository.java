package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FoundItem;

public interface FoundItemRepository extends JpaRepository<FoundItem, Integer> {}
// This interface extends CrudRepository to provide CRUD operations for FoundItem entities.