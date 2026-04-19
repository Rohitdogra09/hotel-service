package com.hotel.hotel_service.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Room Entity - Maps to 'rooms' table in MySQL
 * Represents a single room inside a Hotel
 * Many Rooms belong to ONE Hotel (Many-To-One relationship)
 */
@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber;          // e.g. "101", "202A"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;          // SINGLE, DOUBLE, SUITE etc.

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;   // e.g. 2500.00 (INR)

    @Column(nullable = false)
    private Integer maxOccupancy;       // Max number of guests

    private String description;         // Room description

    private String amenities;           // e.g. "WiFi, AC, TV, Mini Bar"

    @Enumerated(EnumType.STRING)
    private RoomStatus status;          // AVAILABLE, BOOKED, MAINTENANCE

    /**
     * Many Rooms → One Hotel
     * @JoinColumn creates foreign key 'hotel_id' in rooms table
     * LAZY fetch → hotel data loaded only when needed
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)    // FK: rooms.hotel_id → hotels.id
    private Hotel hotel;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Types of rooms available
     */
    public enum RoomType {
        SINGLE,         // 1 bed, 1 person
        DOUBLE,         // 1 large bed, 2 persons
        TWIN,           // 2 single beds, 2 persons
        SUITE,          // Luxury suite
        DELUXE,         // Deluxe room
        PENTHOUSE       // Top floor premium room
    }

    /**
     * Current status of the room
     * AVAILABLE   → Can be booked
     * BOOKED      → Already reserved
     * MAINTENANCE → Under repair, cannot be booked
     */
    public enum RoomStatus {
        AVAILABLE,
        BOOKED,
        MAINTENANCE
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        // Default status is AVAILABLE when room is first created
        if (status == null) status = RoomStatus.AVAILABLE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
