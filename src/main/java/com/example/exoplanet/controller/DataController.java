package com.example.exoplanet.controller;

import com.example.exoplanet.model.Planet;
import com.example.exoplanet.repository.PlanetRepository;
import com.example.exoplanet.service.NasaExoplanetArchiveService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Api(tags = { "Data" })
@RestController
@RequestMapping("/data")
public class DataController {

    final PlanetRepository planetRepository;
    final NasaExoplanetArchiveService nasaExoplanetArchiveService;

    public DataController(
        PlanetRepository planetRepository,
        NasaExoplanetArchiveService nasaExoplanetArchiveService
    ) {
        this.planetRepository = planetRepository;
        this.nasaExoplanetArchiveService = nasaExoplanetArchiveService;
    }

    @GetMapping("/all")
    public Collection<Planet> allPlanets() {
        return this.planetRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Planet getPlanetById(
        @PathVariable("id") Long id
    ) {
        return this.planetRepository.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public Planet getPlanetByName(
        @PathVariable("name") String name
    ) {
        return this.planetRepository.findByName(name);
    }

    @GetMapping("/hostname/{hostname}")
    public Collection<Planet> getPlanetsByHostName(
        @PathVariable("hostname") String hostName
    ) {
        return this.planetRepository.findByHostName(hostName);
    }

    @PostMapping("/populate")
    public String initializeExoPlanetRepo(
        @RequestParam(required = false) boolean forceRepopulate
    ) {

        String responseString = "";
        List<Planet> foundPlanets = Collections.emptyList();

        if (planetRepository.count() > 0) {
            if (forceRepopulate) {
                log.info("Deleting Existing Data to replace");
                planetRepository.deleteAll();
            } else {
                responseString = "Initialize repository halted - rerun with forceRepopulate set to true to force.";
                return responseString;
            }
        }

        try {
            foundPlanets = nasaExoplanetArchiveService.getPlanets();
            responseString = "Found " + foundPlanets.size() + " records in NASA's Exoplanet Archive";
        } catch (Exception e) {
            //TODO Handle Exception
            responseString = "There was a problem populating data";
        }

        try {
            if (foundPlanets != null) {
                planetRepository.saveAll(foundPlanets);
            }
        } catch (Exception e) {
            responseString = "There was a problem saving the found planets";
        }

        return responseString;
    }
}
