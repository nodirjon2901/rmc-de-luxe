package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "to_rent_application")
public class ApplicationForToRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String propertyType;

    String numberOfRoom;

    String area;

    String phoneNumber;

    String address;

    @CreationTimestamp
    LocalDateTime createdDate;

}
