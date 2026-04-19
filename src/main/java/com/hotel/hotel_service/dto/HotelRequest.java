package com.hotel.hotel_service.dto;
import com.hotel.hotel_service.entity.Hotel.HotelStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * HotelRequest DTO - Data received FROM client when creating/updating hotel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    private String zipCode;

    private String phone;

    private String email;

    private String description;

    @NotNull(message = "Star rating is required")
    private Integer starRating;         // Must be 1-5

    private HotelStatus status;         // Optional, defaults to ACTIVE
}
