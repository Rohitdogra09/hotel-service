package com.hotel.hotel_service.repository;
import com.hotel.hotel_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoomRepository - Database access for Room entity
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Get all rooms belonging to a specific hotel
    // SELECT * FROM rooms WHERE hotel_id = ?
    List<Room> findByHotelId(Long hotelId);

    // Get available rooms in a specific hotel
    // SELECT * FROM rooms WHERE hotel_id = ? AND status = 'AVAILABLE'
    List<Room> findByHotelIdAndStatus(Long hotelId, Room.RoomStatus status);

    // Get rooms by type in a specific hotel
    // SELECT * FROM rooms WHERE hotel_id = ? AND room_type = ?
    List<Room> findByHotelIdAndRoomType(Long hotelId, Room.RoomType roomType);
}
