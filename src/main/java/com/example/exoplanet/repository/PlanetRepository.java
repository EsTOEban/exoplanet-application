package com.example.exoplanet.repository;

import com.example.exoplanet.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

    Collection<Planet> findByHostName(String hostName);

    Planet findByName(String name);

    Collection<Planet> findByDiscMethod(String discMethod);

    Collection<Planet> findByControvFlag(String controvFlag);

    Collection<Planet> findByFacility(String facility);

}
