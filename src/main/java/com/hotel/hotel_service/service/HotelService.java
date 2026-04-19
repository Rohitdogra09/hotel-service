package com.hotel.hotel_service.service;
import com.hotel.hotel_service.dto.HotelRequest;
import com.hotel.hotel_service.dto.HotelResponse;
import com.hotel.hotel_service.entity.Hotel;
import com.hotel.hotel_service.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HotelService - Business logic for Hotel operations
 * Handles CRUD + search operations for hotels
 */
@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    /**
     * CREATE a new hotel
     */
    public HotelResponse createHotel(HotelRequest request) {
        // Build entity from request DTO
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .phone(request.getPhone())
                .email(request.getEmail())
                .description(request.getDescription())
                .starRating(request.getStarRating())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : Hotel.HotelStatus.ACTIVE)
                .build();

        return mapToResponse(hotelRepository.save(hotel));
    }

    /**
     * GET hotel by ID
     */
    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        return mapToResponse(hotel);
    }

    /**
     * GET all hotels
     */
    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * SEARCH hotels by city
     */
    public List<HotelResponse> getHotelsByCity(String city) {
        return hotelRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE hotel details
     */
    public HotelResponse updateHotel(Long id, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setCountry(request.getCountry());
        hotel.setZipCode(request.getZipCode());
        hotel.setPhone(request.getPhone());
        hotel.setEmail(request.getEmail());
        hotel.setDescription(request.getDescription());
        hotel.setStarRating(request.getStarRating());
        hotel.setStatus(request.getStatus());

        return mapToResponse(hotelRepository.save(hotel));
    }

    /**
     * DELETE hotel by ID
     */
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    /**
     * PRIVATE HELPER - Maps Hotel entity → HotelResponse DTO
     */
    private HotelResponse mapToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .state(hotel.getState())
                .country(hotel.getCountry())
                .zipCode(hotel.getZipCode())
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .description(hotel.getDescription())
                .starRating(hotel.getStarRating())
                .status(hotel.getStatus())
                .createdAt(hotel.getCreatedAt())
                .build();
    }
}
