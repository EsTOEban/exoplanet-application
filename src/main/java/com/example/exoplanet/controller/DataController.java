package com.example.exoplanet.controller;

import com.example.exoplanet.model.Planet;
import com.example.exoplanet.repository.PlanetRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Api(tags = { "Data" })
@RestController
@RequestMapping("/data")
public class DataController {

    final PlanetRepository planetRepository;

    @Autowired
    public DataController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
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
    public String initializeExoPlanetRepo() {
        String responseString = "";
        String url = buildRequestURL();
        List<Planet> foundPlanets = Collections.emptyList();

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);

        try {
            ResponseEntity<List<Planet>> response = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Planet>>() {
                });
            foundPlanets = response.getBody();
            responseString = "Found " + response.getBody().size() + " records in NASA's Exoplanet Archive";
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

    private String buildRequestURL() {
        return
            "https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?table=exoplanets&order=dec&format=json"
                + "&select=" + "pl_hostname," + "pl_name," + "pl_discmethod," + "pl_controvflag," + "rowupdate,"
                + "pl_facility," + "pl_disc," + "pl_locale," + "pl_disc_refname," + "pl_telescope,"
                + "pl_instrument," + "pl_status";
    }
}
