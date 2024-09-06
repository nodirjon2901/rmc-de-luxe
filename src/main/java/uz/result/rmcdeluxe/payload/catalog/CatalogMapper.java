package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Catalog;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.entity.HouseType;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;
import uz.result.rmcdeluxe.payload.district.DistrictMapper;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogMapper {

    Long id;

    String slug;

    String name;

    Photo photo;

    DistrictMapper district;

    Double price;

    HouseTypeMapper type;

    String numberOfRooms;

    String completionDate;

    boolean active;

    public CatalogMapper(Catalog catalog, String lang) {
        this.id = catalog.getId();
        this.slug = catalog.getSlug();
        this.name = catalog.getName();
        this.photo = catalog.getPhoto();
        this.district = new DistrictMapper(catalog.getDistrict(),lang);
        this.price = catalog.getPrice();
        this.type = new HouseTypeMapper(catalog.getType(),lang);
        this.active = catalog.isActive();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.numberOfRooms = catalog.getNumberOfRoomsUz();
                this.completionDate = catalog.getCompletionDateUz();
                break;
            }
            case "ru": {
                this.numberOfRooms = catalog.getNumberOfRoomsRu();
                this.completionDate = catalog.getCompletionDateRu();
                break;
            }
            case "en": {
                this.numberOfRooms = catalog.getNumberOfRoomsEn();
                this.completionDate = catalog.getCompletionDateEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
