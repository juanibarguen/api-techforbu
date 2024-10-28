package com.example.api_techforb.Modules.plants.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_techforb.Modules.plants.Model.Plant;
import com.example.api_techforb.Modules.plants.Repository.PlantRepository;



@Service // Indica que esta clase es un servicio que contiene la lógica de negocio para las plantas
public class PlantService {

    @Autowired // Inyecta el repositorio de plantas
    private PlantRepository plantRepository;
    
    // Obtiene la lista completa de todas las plantas almacenadas
    public List<Plant> getAllPlants() { return plantRepository.findAll(); }
    // Busca una planta por su ID y la devuelve si existe
    public Optional<Plant> getPlantById(Long id) { return plantRepository.findById(id); }
    // Guarda una nueva planta en el repositorio
    public Plant savePlant(Plant plant) { return plantRepository.save(plant); }
    // Actualiza los detalles de una planta existente por su ID
    public Plant updatePlant(Long id, Plant plantDetails) {
        return plantRepository.findById(id).map(plant -> {
            // Actualiza los campos de la planta con los nuevos valores proporcionados
            plant.setCountry(plantDetails.getCountry());
            plant.setCountryCode(plantDetails.getCountryCode());
            plant.setName(plantDetails.getName());
            plant.setReadings(plantDetails.getReadings());
            plant.setMediumAlerts(plantDetails.getMediumAlerts());
            plant.setRedAlerts(plantDetails.getRedAlerts());
             // Guarda y devuelve la planta actualizada
            return plantRepository.save(plant);
        }).orElseThrow(() -> new RuntimeException("Planta no encontrada con id " + id)); // Si no existe, lanza una excepción
    }

    // Elimina una planta del repositorio usando su ID
    public void deletePlant(Long id) { plantRepository.deleteById(id); }
    
}