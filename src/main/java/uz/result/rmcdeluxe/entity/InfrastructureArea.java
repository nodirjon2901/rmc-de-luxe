package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructureAreaCreateDTO;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructureAreaResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "infrastructure_area")
public class InfrastructureArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "infrastructureArea", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InfrastructSection> sections;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    @ManyToOne
    @JsonIgnore
    Building building;

//    @PostPersist
    @PostUpdate
    void setSubEntity() {
        if (this.sections != null) {
            this.sections.forEach(section -> section.setInfrastructureArea(this));
        }
    }

    public InfrastructureArea(InfrastructureAreaCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        if (createDTO.getSections() != null && !createDTO.getSections().isEmpty()) {
            this.sections = createDTO.getSections().stream().map(
                    InfrastructSection::new
            ).collect(Collectors.toList());
        }
    }

    public InfrastructureArea(InfrastructureAreaResponseDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getSections() != null && !dto.getSections().isEmpty()) {
            this.sections = dto.getSections().stream().map(
                    InfrastructSection::new
            ).collect(Collectors.toList());
        }
    }

}
