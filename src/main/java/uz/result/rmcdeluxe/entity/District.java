package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.district.DistrictCreateDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nameUz;

    @Column(unique = true)
    String nameRu;

    @Column(unique = true)
    String nameEn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district", orphanRemoval = true)
    List<Catalog> catalogs;

    public District(DistrictCreateDTO createDTO) {
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
