package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.demo.model.FoundItem;
import com.example.demo.model.LostItem;
import com.example.demo.repository.FoundItemRepository;
import com.example.demo.repository.LostItemRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

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
        return toList(lostRepo.findByApprovedTrue());
    }

    @GetMapping("/found")
    public List<FoundItem> getFoundItems() {
        return toList(foundRepo.findAll());
    }

    @PostMapping(value = "/lost", consumes = { "multipart/form-data", "multipart/*" })
    public ResponseEntity<?> createLostItem(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            byte[] imageBytes = image != null ? image.getBytes() : null;

            LostItem item = new LostItem(title, description, location, imageBytes);
            item.setApproved(false);
            LostItem saved = lostRepo.save(item);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading image file", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return lostRepo.findById(id)
                .filter(item -> item.getImage() != null && item.getImage().length > 0)
                .map(item -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    return new ResponseEntity<>(item.getImage(), headers, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/admin/lost-items")
    public List<LostItem> getAllLostItemsForAdmin() {
        return toList(lostRepo.findAll());
    }

    @PutMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveLostItem(@PathVariable Long id) {
        return lostRepo.findById(id)
                .map(item -> {
                    item.setApproved(true);
                    lostRepo.save(item);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        return lostRepo.findById(id)
                .map(item -> {
                    lostRepo.delete(item);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
