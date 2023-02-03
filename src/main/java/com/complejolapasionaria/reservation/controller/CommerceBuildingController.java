package com.complejolapasionaria.reservation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/buildings")
@SecurityRequirement(name="Bearer Authentication")
public class CommerceBuildingController {

    @GetMapping("/{id}")
    public ResponseEntity<Void> getCommerceBuilding(@PathVariable Long id){
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Void>> getAllCommerceBuildings(){
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveCommerceBuilding(){
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCommerceBuilding(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{id}")
    public void removeCommerceBuilding(@PathVariable Long id){}

}
