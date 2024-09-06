package uz.result.rmcdeluxe.payload.district;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictResponseDTO {

    Long id;

    Translation name;

    public DistrictResponseDTO(District district) {
        this.id = district.getId();
        this.name = new Translation(
                district.getNameUz(),
                district.getNameRu(),
                district.getNameEn()
        );
    }

}
