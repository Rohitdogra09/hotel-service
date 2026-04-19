package com.hotel.hotel_service.dto;
import com.hotel.hotel_service.entity.Room.RoomType;
import com.hotel.hotel_service.entity.Room.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

/**
 * RoomRequest DTO - Data received FROM client when creating/updating room
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price per night is required")
    private BigDecimal pricePerNight;

    @NotNull(message = "Max occupancy is required")
    private Integer maxOccupancy;

    private String description;

    private String amenities;

    private RoomStatus status;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;               // Which hotel this room belongs to
}