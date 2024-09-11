package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.PlanApartment;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanApartmentMapper {

    Long id;

    String title;

    Integer floorNum;

    Integer buildingNum;

    Integer entranceNum;

    String roomCount;

    Double price;

    Photo photo;

    boolean active;

    BuildingSubClassDTO building;

    public PlanApartmentMapper(PlanApartment planApartment, String lang) {
        this.id = planApartment.getId();
        this.floorNum = planApartment.getFloorNum();
        this.buildingNum = planApartment.getBuildingNum();
        this.entranceNum = planApartment.getEntranceNum();
        this.price = planApartment.getPrice();
        this.photo = planApartment.getPhoto();
        this.active = planApartment.isActive();
        this.building=new BuildingSubClassDTO(planApartment.getBuilding());
        switch (lang.toLowerCase()) {
            case "uz": {
                this.title = planApartment.getTitleUz();
                this.roomCount = planApartment.getRoomCountUz();
                break;
            }
            case "ru": {
                this.title = planApartment.getTitleRu();
                this.roomCount = planApartment.getRoomCountRu();
                break;
            }
            case "en": {
                this.title = planApartment.getTitleEn();
                this.roomCount = planApartment.getRoomCountEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
