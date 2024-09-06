package uz.result.rmcdeluxe.payload.houseType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.HouseType;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseTypeResponseDTO {

    Long id;

    Translation name;

    public HouseTypeResponseDTO(HouseType type) {
        this.id = type.getId();
        this.name = new Translation(
                type.getNameUz(),
                type.getNameRu(),
                type.getNameEn()
        );
    }

}
