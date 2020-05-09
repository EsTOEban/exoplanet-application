package com.example.exoplanet;

import com.example.exoplanet.model.Booking;
import com.example.exoplanet.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExoplanetStartupCommandLineRunner implements CommandLineRunner {

        final BookingRepository repository;

        @Autowired
        public ExoplanetStartupCommandLineRunner(BookingRepository repository) {
                this.repository = repository;
        }

        @Override
        public void run(String... args) throws Exception {
                for (Booking b : this.repository.findAll())
                        System.out.println(b.toString());
        }
}
