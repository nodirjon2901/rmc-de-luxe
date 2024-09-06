package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Catalog;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.district.DistrictResponseDTO;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogResponseDTO {

    Long id;

    String slug;

    String name;

    Photo photo;

    DistrictResponseDTO district;

    Double price;

    HouseTypeResponseDTO type;

    Translation numberOfRooms;

    Translation completionDate;

    boolean active;

    public CatalogResponseDTO(Catalog catalog) {
        this.id = catalog.getId();
        this.slug = catalog.getSlug();
        this.name = catalog.getName();
        this.photo = catalog.getPhoto();
        this.district = new DistrictResponseDTO(catalog.getDistrict());
        this.price = catalog.getPrice();
        this.type = new HouseTypeResponseDTO(catalog.getType());
        this.active = catalog.isActive();
        this.numberOfRooms = new Translation(
                numberOfRooms.getUz(),
                numberOfRooms.getRu(),
                numberOfRooms.getEn()
        );
        this.completionDate = new Translation(
                completionDate.getUz(),
                completionDate.getRu(),
                completionDate.getEn()
        );
    }

}
