package com.example.api_techforb.Modules.plants.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api_techforb.Modules.plants.Model.Plant;;



@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> { }