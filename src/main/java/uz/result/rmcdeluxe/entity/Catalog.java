package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.catalog.CatalogCreateDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String slug;

    @Column(unique = true)
    String name;

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

    public Catalog(CatalogCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        this.name = createDTO.getName();
        this.photo = createDTO.getPhoto();
        this.price = createDTO.getPrice();
        if (createDTO.getNumberOfRooms() != null) {
            this.numberOfRoomsUz = createDTO.getNumberOfRooms().getUz();
            this.numberOfRoomsRu = createDTO.getNumberOfRooms().getRu();
            this.numberOfRoomsEn = createDTO.getNumberOfRooms().getEn();
        }
        if (createDTO.getCompletionDate() != null) {
            this.completionDateUz = createDTO.getCompletionDate().getUz();
            this.completionDateRu = createDTO.getCompletionDate().getRu();
            this.completionDateEn = createDTO.getCompletionDate().getEn();
        }
    }

}
