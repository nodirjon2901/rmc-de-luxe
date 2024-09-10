package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Catalog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String slug;//unique

    String name;//unique

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    @ManyToOne
    @JsonIgnore
    District district;

    Double price;

    @ManyToOne
    @JsonIgnore
    HouseType type;

    String numberOfRoomsUz;

    String numberOfRoomsRu;

    String numberOfRoomsEn;

    String completionDateUz;

    String completionDateRu;

    String completionDateEn;

    boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalog", orphanRemoval = true)
    @JsonIgnore
    List<Building> buildingList;

}
