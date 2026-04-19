package com.hotel.hotel_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Hotel Entity - Maps to 'hotels' table in MySQL
 * Represents a hotel property in the system
 * One Hotel can have MANY Rooms (One-To-Many relationship)
 */
@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;                // Hotel name e.g. "Grand Shimla Palace"

    @NotBlank
    @Column(nullable = false)
    private String address;             // Full address

    @NotBlank
    @Column(nullable = false)
    private String city;                // City name

    @NotBlank
    @Column(nullable = false)
    private String state;               // State name

    @NotBlank
    @Column(nullable = false)
    private String country;             // Country name

    private String zipCode;             // Postal code

    private String phone;               // Hotel contact number

    private String email;               // Hotel contact email

    private String description;         // Hotel description

    @Column(nullable = false)
    private Integer starRating;         // 1 to 5 stars

    @Enumerated(EnumType.STRING)
    private HotelStatus status;         // ACTIVE or INACTIVE

    /**
     * One Hotel → Many Rooms
     * mappedBy = "hotel" refers to the 'hotel' field in Room entity
     * CascadeType.ALL → if hotel deleted, all its rooms deleted too
     * fetch = LAZY → rooms loaded only when accessed (performance)
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Hotel status options
     * ACTIVE   → Hotel is open and accepting bookings
     * INACTIVE → Hotel is closed or under maintenance
     */
    public enum HotelStatus {
        ACTIVE,
        INACTIVE
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        // Default status is ACTIVE when hotel is first created
        if (status == null) status = HotelStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
