package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.PlanApartment;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanApartmentResponseDTO {

    Long id;

    Translation title;

    Integer floorNum;

    Integer buildingNum;

    Integer entranceNum;

    Translation roomCount;

    Double price;

    Photo photo;

    boolean active;

    BuildingSubClassDTO building;

    public PlanApartmentResponseDTO(PlanApartment planApartment) {
        this.id = planApartment.getId();
        this.floorNum = planApartment.getFloorNum();
        this.buildingNum = planApartment.getBuildingNum();
        this.entranceNum = planApartment.getEntranceNum();
        this.price = planApartment.getPrice();
        this.photo = planApartment.getPhoto();
        this.active = planApartment.isActive();
        this.building = new BuildingSubClassDTO(planApartment.getBuilding());
        this.title = new Translation(
                planApartment.getTitleUz(),
                planApartment.getTitleRu(),
                planApartment.getTitleEn()
        );
        this.roomCount = new Translation(
                planApartment.getRoomCountUz(),
                planApartment.getRoomCountRu(),
                planApartment.getRoomCountEn()
        );
    }

}
