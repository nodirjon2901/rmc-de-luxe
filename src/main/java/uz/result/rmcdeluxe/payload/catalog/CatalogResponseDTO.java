package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Catalog;
import uz.result.rmcdeluxe.entity.CatalogType;
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

    CatalogType catalogType;

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
        this.catalogType=catalog.getCatalogType();
        this.active = catalog.isActive();
        this.numberOfRooms = new Translation(
                catalog.getNumberOfRoomsUz(),
                catalog.getNumberOfRoomsRu(),
                catalog.getNumberOfRoomsEn()
        );
        this.completionDate = new Translation(
                catalog.getCompletionDateUz(),
                catalog.getCompletionDateRu(),
                catalog.getCompletionDateEn()
        );
    }

}
