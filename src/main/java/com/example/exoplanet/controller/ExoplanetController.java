package com.example.exoplanet.controller;

import com.example.exoplanet.model.Booking;
import com.example.exoplanet.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("Exoplanet")
//@RequestMapping("/exoplanet")
public class ExoplanetController {

        final BookingRepository bookingRepository;

        @Autowired
        public ExoplanetController(BookingRepository bookingRepository) {
                this.bookingRepository = bookingRepository;
        }

        @GetMapping("/all")
        public Collection<Booking> bookings() {
                return this.bookingRepository.findAll();
        }

        @GetMapping("/{name}")
        public Collection<Booking> getBookingByName(
                @PathVariable("name") String name
        ) {
                return this.bookingRepository.findByBookingName(name);
        }

        @PostMapping("/populate")
        @ResponseBody
        public String fetchExoPlanetDetails() {
                // TODO Auto-generated method stub
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
                Map map = new HashMap<String, String>();
                map.put("Content-Type", "application/json");
                headers.setAll(map);
                Map req_payload = new HashMap();
                req_payload.put("foo", "bar");

                HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
                String url = "https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?table=exoplanets&select=pl_hostname,ra,dec&order=dec&format=csv";

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Add the String message converter
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());


//                ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                List<String> rows = CollectionUtils.arrayToList(StringUtils.split(response.getBody(), "\n"));

                System.out.println(response);

                return "";
        }
}
