package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Building;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingSubClassDTO {

    Long id;

    String slug;

    public BuildingSubClassDTO(Building build) {
        this.id = build.getId();
        this.slug = build.getSlug();
    }

}
