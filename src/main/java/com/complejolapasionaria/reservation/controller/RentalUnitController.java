package com.complejolapasionaria.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rentalUnits")
public class RentalUnitController {

    @GetMapping("/{id}")
    public ResponseEntity<Void> getRentalUnit(@PathVariable Long id){
        return null;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Void>> getAllRentalUnit(@PathVariable Long id){
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveRentalUnit(){
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRentalUnit(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{id}")
    public void removeRentalUnit(@PathVariable Long id){}
}
