package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.building.BuildingCreateDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String slug;//unique

    String titleUz;

    String titleRu;

    String titleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "building", orphanRemoval = true)
    List<Photo> gallery;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "building", orphanRemoval = true)
    List<VideoFile> videoList;

    boolean active;

    @ManyToOne
    @JsonIgnore
    Catalog catalog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "building", orphanRemoval = true)
    @JsonIgnore
    List<PlanApartment> apartments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "building", orphanRemoval = true)
    @JsonIgnore
    List<InfrastructureArea> areaList;

    public Building(BuildingCreateDTO createDTO){
        if (createDTO==null){
            return;
        }
        if (createDTO.getTitle()!=null){
            this.titleUz=createDTO.getTitle().getUz();
            this.titleRu=createDTO.getTitle().getRu();
            this.titleEn=createDTO.getTitle().getEn();
        }
        if (createDTO.getDescription()!=null){
            this.descriptionUz=createDTO.getDescription().getUz();
            this.descriptionRu=createDTO.getDescription().getRu();
            this.descriptionEn=createDTO.getDescription().getEn();
        }
    }

}
