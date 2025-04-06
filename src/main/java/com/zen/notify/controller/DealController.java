package com.zen.notify.controller;


import com.zen.notify.entity.Deal;
import com.zen.notify.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    // Create Deal
    @PostMapping
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        return ResponseEntity.ok(dealService.createDeal(deal));
    }

    // Get All Deals
    @GetMapping
    public ResponseEntity<List<Deal>> getAllDeals() {
        return ResponseEntity.ok(dealService.getAllDeals());
    }

    // Get Deal by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deal>> getDealById(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.getDealById(id));
    }

    // Update Deal
    @PutMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal dealDetails) {
        return ResponseEntity.ok(dealService.updateDeal(id, dealDetails));
    }

    // Delete Deal
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return ResponseEntity.ok("Deal deleted successfully");
    }
}

