package com.example.exoplanet.serviceimpl;

import com.example.exoplanet.model.Planet;
import com.example.exoplanet.service.NasaExoplanetArchiveService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class NasaExoplanetArchiveServiceImpl implements NasaExoplanetArchiveService {

    @Override
    public List<Planet> getPlanets() {
        String url = buildRequestURL();

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);

        ResponseEntity<List<Planet>> response = restTemplate
            .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Planet>>() {
            });
        return response.getBody();
    }

    private String buildRequestURL() {
        return
            "https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?table=exoplanets&order=dec&format=json"
                + "&select=" + "pl_hostname," + "pl_name," + "pl_discmethod," + "pl_controvflag," + "rowupdate,"
                + "pl_facility," + "pl_disc," + "pl_locale," + "pl_disc_refname," + "pl_telescope,"
                + "pl_instrument," + "pl_status";
    }
}
