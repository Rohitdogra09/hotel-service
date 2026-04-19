package com.hotel.hotel_service.repository;
import com.hotel.hotel_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HotelRepository - Database access for Hotel entity
 * JpaRepository gives free CRUD methods
 * We define custom search queries below
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // SELECT * FROM hotels WHERE city = ?
    List<Hotel> findByCity(String city);

    // SELECT * FROM hotels WHERE city = ? AND status = ?
    List<Hotel> findByCityAndStatus(String city, Hotel.HotelStatus status);

    // SELECT * FROM hotels WHERE status = ?
    List<Hotel> findByStatus(Hotel.HotelStatus status);

    // SELECT * FROM hotels WHERE name LIKE %keyword%
    List<Hotel> findByNameContainingIgnoreCase(String name);
}