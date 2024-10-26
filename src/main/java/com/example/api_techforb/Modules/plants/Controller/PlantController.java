
package com.example.api_techforb.Modules.plants.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api_techforb.Modules.plants.Model.Plant;
import com.example.api_techforb.Modules.plants.Service.PlantService;



@RestController
@RequestMapping("/plants")
@CrossOrigin(origins = "http://localhost:4200")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @GetMapping
    public List<Plant> getAllPlants() { return plantService.getAllPlants(); }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        return plantService.getPlantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) { return plantService.savePlant(plant); }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant plantDetails) {
        try {
            Plant updatedPlant = plantService.updatePlant(id, plantDetails);
            return ResponseEntity.ok(updatedPlant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }



    
}