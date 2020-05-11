package com.example.exoplanet;

import com.example.exoplanet.controller.DataController;
import com.example.exoplanet.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExoplanetStartupCommandLineRunner implements CommandLineRunner {

    final DataController dataController;
    final PlanetRepository repository;

    @Autowired
    public ExoplanetStartupCommandLineRunner(
        DataController dataController,
        PlanetRepository repository
    ) {
        this.dataController = dataController;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        dataController.initializeExoPlanetRepo();
    }
}
