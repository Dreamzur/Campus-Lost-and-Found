package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FoundItem;
import com.example.demo.model.LostItem;
import com.example.demo.repository.FoundItemRepository;
import com.example.demo.repository.LostItemRepository;

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
    public LostItem reportLost(@RequestBody LostItem item) {
        return lostRepo.save(item);
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}