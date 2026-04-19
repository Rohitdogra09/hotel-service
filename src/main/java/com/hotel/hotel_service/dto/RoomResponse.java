package com.hotel.hotel_service.dto;
import com.hotel.hotel_service.entity.Room.RoomType;
import com.hotel.hotel_service.entity.Room.RoomStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RoomResponse DTO - Data sent BACK to client after room operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private Integer maxOccupancy;
    private String description;
    private String amenities;
    private RoomStatus status;
    private Long hotelId;               // Reference to parent hotel
    private String hotelName;           // Hotel name for convenience
    private LocalDateTime createdAt;
}