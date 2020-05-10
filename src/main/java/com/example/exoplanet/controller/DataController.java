package com.example.exoplanet.controller;

import com.example.exoplanet.model.Planet;
import com.example.exoplanet.repository.BookingRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Api(tags = {"Data"})
@RestController
@RequestMapping("/data")
public class DataController {

    final BookingRepository bookingRepository;

    @Autowired
    public DataController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

//    @GetMapping("/all")
//    public Collection<Booking> bookings() {
//        return this.bookingRepository.findAll();
//    }
//
//    @GetMapping("/{name}")
//    public Collection<Booking> getBookingByName(
//            @PathVariable("name") String name
//    ) {
//        return this.bookingRepository.findByBookingName(name);
//    }

    @PostMapping("/populate")
    public String fetchExoPlanetDetails() {
        String url = buildRequestURL();

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);

        try {
            ResponseEntity<List<Planet>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Planet>>() {
            });
            return "Found " + response.getBody().size() + " records in NASA's Exoplanet Archive";
        } catch (Exception e) {
            //TODO Handle Exception
        }

        return "";
    }

    private String buildRequestURL() {
        return "https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?table=exoplanets&order=dec&format=json" + "&select=" + "pl_hostname," + "pl_name," + "pl_discmethod," + "pl_controvflag," + "rowupdate," + "pl_facility," + "pl_disc," + "pl_locale," + "pl_disc_refname," + "pl_telescope," + "pl_instrument," + "pl_status";
    }
}
