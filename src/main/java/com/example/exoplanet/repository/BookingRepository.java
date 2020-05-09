package com.example.exoplanet.repository;

import com.example.exoplanet.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

        Collection<Booking> findByBookingName(String bookingName);
}
