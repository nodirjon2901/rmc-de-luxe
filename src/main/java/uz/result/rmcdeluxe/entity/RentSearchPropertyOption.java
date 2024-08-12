package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "rent_search_property_option")
public class RentSearchPropertyOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String nameEn;

    @OneToOne
    Photo photo;

    @ManyToOne
    @JsonIgnore
    RentPageSearchProperty searchProperty;

}
