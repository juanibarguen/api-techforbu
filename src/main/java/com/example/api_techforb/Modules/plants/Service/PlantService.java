package com.example.api_techforb.Modules.plants.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_techforb.Modules.plants.Model.Plant;
import com.example.api_techforb.Modules.plants.Repository.PlantRepository;



@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    public List<Plant> getAllPlants() { return plantRepository.findAll(); }

    public Optional<Plant> getPlantById(Long id) { return plantRepository.findById(id); }

    public Plant savePlant(Plant plant) { return plantRepository.save(plant); }

    public Plant updatePlant(Long id, Plant plantDetails) {
        return plantRepository.findById(id).map(plant -> {
            plant.setCountry(plantDetails.getCountry());
            plant.setCountryCode(plantDetails.getCountryCode());
            plant.setName(plantDetails.getName());
            plant.setReadings(plantDetails.getReadings());
            plant.setMediumAlerts(plantDetails.getMediumAlerts());
            plant.setRedAlerts(plantDetails.getRedAlerts());
            return plantRepository.save(plant);
        }).orElseThrow(() -> new RuntimeException("Planta no encontrada con id " + id));
    }


    public void deletePlant(Long id) { plantRepository.deleteById(id); }
}