package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructSection;
import uz.result.rmcdeluxe.payload.Translation;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructSectionResponseDTO {

    Long id;

    Translation title;

    Translation description;

    List<InfrastructSectionItemResponseDTO> sectionItems;

    public InfrastructSectionResponseDTO(InfrastructSection section) {
        this.id = section.getId();
        this.title = new Translation(
                section.getTitleUz(),
                section.getTitleRu(),
                section.getTitleEn()
        );
        this.description = new Translation(
                section.getDescriptionUz(),
                section.getDescriptionRu(),
                section.getDescriptionEn()
        );
        this.sectionItems = section.getSectionItems().stream()
                .map(InfrastructSectionItemResponseDTO::new)
                .collect(Collectors.toList());
    }

}
