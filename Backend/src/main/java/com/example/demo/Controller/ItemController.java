package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.FoundItem;
import com.example.demo.model.LostItem;
import com.example.demo.repository.FoundItemRepository;
import com.example.demo.repository.LostItemRepository;
import com.example.demo.web.dto.CreateLostItemRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // frontend
public class ItemController {

    private final LostItemRepository lostRepo;
    private final FoundItemRepository foundRepo;

    public ItemController(LostItemRepository lostRepo, FoundItemRepository foundRepo) {
        this.lostRepo = lostRepo;
        this.foundRepo = foundRepo;
    }

    @GetMapping("/lost")
    public List<LostItem> getLostItems() {
        return toList(lostRepo.findAll());
    }

    @GetMapping("/found")
    public List<FoundItem> getFoundItems() {
        return toList(foundRepo.findAll());
    }

    @PostMapping("/lost")
    public LostItem reportLost(@RequestBody CreateLostItemRequest req) {
        LostItem entity = new LostItem();
        entity.setDescription(req.description());
        entity.setImageUrl(req.imageUrl());
        entity.setLocation(req.location());
        entity.setTitle(req.title());
        return lostRepo.save(entity);
    }

    @PostMapping("/found")
    public FoundItem reportFound(@RequestBody CreateLostItemRequest req) {
        FoundItem entity = new FoundItem();
        entity.setDescription(req.description());
        entity.setImageUrl(req.imageUrl());
        entity.setLocation(req.location());
        entity.setTitle(req.title());
        return foundRepo.save(entity);
    }

@PutMapping("/lost/{id}/claim")
public ResponseEntity<Void> claimLostItem(@PathVariable Integer id) {
    LostItem item = lostRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    item.setClaimed(true);
    lostRepo.save(item);
    return ResponseEntity.noContent().build();
}

@PutMapping("/found/{id}/claim")
public ResponseEntity<Void> claimFoundItem(@PathVariable Integer id) {
    FoundItem item = foundRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    item.setClaimed(true);
    foundRepo.save(item);
    return ResponseEntity.noContent().build();
}



    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}