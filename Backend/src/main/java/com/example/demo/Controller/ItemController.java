package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        entity.setClaimed(false);
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
    public ResponseEntity<Void> claimLost(
            @PathVariable("id") Long id // ← explicitly name it
    ) {
        lostRepo.findById(id).ifPresent(item -> {
            item.setClaimed(true);
            lostRepo.save(item);
        });
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/found/{id}/claim")
    public ResponseEntity<Void> claimFound(
            @PathVariable("id") Long id // ← and here
    ) {
        foundRepo.findById(id).ifPresent(item -> {
            item.setClaimed(true);
            foundRepo.save(item);
        });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/lost/{id}")
    public ResponseEntity<Void> deleteLost(@PathVariable("id") Long id) {
        lostRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/found/{id}")
    public ResponseEntity<Void> deleteFound(@PathVariable("id") Long id) {
        foundRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * Below I will put the Webapp endpoints, there isn't enough time to work it
     * through before showcase
     */

    @GetMapping("/admin/lost-items")
    public ResponseEntity<List<LostItem>> getAllLostItems(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.status(403).build();
        }

        List<LostItem> allItems = toList(lostRepo.findAll());
        return ResponseEntity.ok(allItems);
    }

    @PutMapping("/admin/claim/{id}")
    public ResponseEntity<Object> adminClaimLostItem(@PathVariable Long id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.status(403).build();
        }

        return lostRepo.findById(id).map(item -> {
            item.setClaimed(true);
            lostRepo.save(item);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteLostItem(@PathVariable Long id) {
        if (lostRepo.existsById(id)) {
            lostRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}