
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

    @Autowired // Inyecta el servicio de plantas
    private PlantService plantService;

    @GetMapping // Obtiene la lista de todas las plantas
    public List<Plant> getAllPlants() { return plantService.getAllPlants(); }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        return plantService.getPlantById(id)
                .map(ResponseEntity::ok)// Si se encuentra la planta, devuelve un 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve un 404 Not Found
    }

    @PostMapping// Crea una nueva planta con los datos proporcionados en la solicitud
    public Plant createPlant(@RequestBody Plant plant) { return plantService.savePlant(plant); }

    @PutMapping("/{id}") // Actualiza una planta existente con nuevos detalles
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant plantDetails) {
        try {
            Plant updatedPlant = plantService.updatePlant(id, plantDetails); // Intenta actualizar la planta
            return ResponseEntity.ok(updatedPlant); // Devuelve el objeto actualizado
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra, devuelve un 404 Not Found
        }
    }

    @DeleteMapping("/{id}") // Elimina una planta según su ID
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);// Llama al servicio para eliminar la planta
        return ResponseEntity.noContent().build(); // Responde con un 204 No Content si se elimina exitosamente
    }



    
}