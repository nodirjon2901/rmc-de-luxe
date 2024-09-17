package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.building.PlanApartmentCreateDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "plan_apartment")
public class PlanApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    Integer floorNum;

    Integer buildingNum;

    Integer entranceNum;

    String roomCountUz;

    String roomCountRu;

    String roomCountEn;

    Double price;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    boolean active;

    @ManyToOne
    @JsonIgnore
    Building building;

    public PlanApartment(PlanApartmentCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        this.floorNum=createDTO.getFloorNum();
        this.buildingNum=createDTO.getBuildingNum();
        this.entranceNum=createDTO.getEntranceNum();
        this.price=createDTO.getPrice();
        if (createDTO.getTitle()!=null){
            this.titleUz=createDTO.getTitle().getUz();
            this.titleRu=createDTO.getTitle().getRu();
            this.titleEn=createDTO.getTitle().getEn();
        }
        if (createDTO.getRoomCount()!=null){
            this.roomCountUz=createDTO.getRoomCount().getUz();
            this.roomCountRu=createDTO.getRoomCount().getRu();
            this.roomCountEn=createDTO.getRoomCount().getEn();
        }
    }

}
