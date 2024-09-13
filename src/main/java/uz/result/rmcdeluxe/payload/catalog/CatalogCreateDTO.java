package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.entity.HouseType;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.district.DistrictCreateDTO;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeCreateDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogCreateDTO {

    String name;

    Long districtId;

    Double price;

    Long typeId;

    Translation numberOfRooms;

    Translation completionDate;

}
