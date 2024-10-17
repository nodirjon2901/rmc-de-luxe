package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeCreateDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "house_type")
public class HouseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nameUz;

    @Column(unique = true)
    String nameRu;

    @Column(unique = true)
    String nameEn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", orphanRemoval = true)
    List<Catalog> catalogs;

    public HouseType(HouseTypeCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        if (createDTO.getName() != null) {
            this.nameUz = createDTO.getName().getUz();
            this.nameRu = createDTO.getName().getRu();
            this.nameEn = createDTO.getName().getEn();
        }
    }

}
