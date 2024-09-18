package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructureArea;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.building.BuildingSubClassDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructureAreaResponseDTO {

    Long id;

    List<InfrastructSectionResponseDTO> sections;

    Photo photo;

    BuildingSubClassDTO build;

    public InfrastructureAreaResponseDTO(InfrastructureArea area) {
        this.id = area.getId();
        this.photo = area.getPhoto();
        this.build = new BuildingSubClassDTO(area.getBuilding());
        this.sections = area.getSections().stream()
                .map(InfrastructSectionResponseDTO::new)
                .collect(Collectors.toList());
    }

}
