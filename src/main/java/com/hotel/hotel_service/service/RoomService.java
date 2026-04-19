package com.hotel.hotel_service.service;
import com.hotel.hotel_service.dto.RoomRequest;
import com.hotel.hotel_service.dto.RoomResponse;
import com.hotel.hotel_service.entity.Hotel;
import com.hotel.hotel_service.entity.Room;
import com.hotel.hotel_service.repository.HotelRepository;
import com.hotel.hotel_service.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RoomService - Business logic for Room operations
 * Manages rooms within hotels
 */
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;  // Needed to link room to hotel

    /**
     * CREATE a new room inside a hotel
     */
    public RoomResponse createRoom(RoomRequest request) {
        // First find the hotel this room belongs to
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: "
                        + request.getHotelId()));

        // Build room entity
        Room room = Room.builder()
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .pricePerNight(request.getPricePerNight())
                .maxOccupancy(request.getMaxOccupancy())
                .description(request.getDescription())
                .amenities(request.getAmenities())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : Room.RoomStatus.AVAILABLE)
                .hotel(hotel)       // Link room to hotel (sets hotel_id FK)
                .build();

        return mapToResponse(roomRepository.save(room));
    }

    /**
     * GET room by ID
     */
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return mapToResponse(room);
    }

    /**
     * GET all rooms of a specific hotel
     */
    public List<RoomResponse> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * GET available rooms of a specific hotel
     */
    public List<RoomResponse> getAvailableRooms(Long hotelId) {
        return roomRepository.findByHotelIdAndStatus(hotelId, Room.RoomStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE room details
     */
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setMaxOccupancy(request.getMaxOccupancy());
        room.setDescription(request.getDescription());
        room.setAmenities(request.getAmenities());
        room.setStatus(request.getStatus());

        return mapToResponse(roomRepository.save(room));
    }

    /**
     * DELETE room by ID
     */
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    /**
     * PRIVATE HELPER - Maps Room entity → RoomResponse DTO
     */
    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .maxOccupancy(room.getMaxOccupancy())
                .description(room.getDescription())
                .amenities(room.getAmenities())
                .status(room.getStatus())
                .hotelId(room.getHotel().getId())
                .hotelName(room.getHotel().getName())   // Convenient hotel name in response
                .createdAt(room.getCreatedAt())
                .build();
    }
}
