package com.hotel.hotel_service.dto;
import com.hotel.hotel_service.entity.Hotel.HotelStatus;
import lombok.*;
import java.time.LocalDateTime;

/**
 * HotelResponse DTO - Data sent BACK to client after hotel operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phone;
    private String email;
    private String description;
    private Integer starRating;
    private HotelStatus status;
    private LocalDateTime createdAt;
}
