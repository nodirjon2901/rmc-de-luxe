package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructSectionItem;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructSectionItemResponseDTO {

    Long id;

    Translation name;

    Translation timeOrDistance;

    public InfrastructSectionItemResponseDTO(InfrastructSectionItem item) {
        this.id = item.getId();
        this.name = new Translation(
                item.getNameUz(),
                item.getNameRu(),
                item.getNameEn()
        );
        this.timeOrDistance = new Translation(
                item.getTimeOrDistanceUz(),
                item.getTimeOrDistanceRu(),
                item.getTimeOrDistanceEn()
        );
    }

}
