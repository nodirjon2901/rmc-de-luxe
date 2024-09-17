package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructSectionItemCreateDTO;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructSectionItemResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "infrastruct_section_item")
public class InfrastructSectionItem {

    @Id
    @GeneratedValue
    Long id;

    String nameUz;

    String nameRu;

    String nameEn;

    String timeOrDistanceUz;

    String timeOrDistanceRu;

    String timeOrDistanceEn;

    @ManyToOne
    @JsonIgnore
    InfrastructSection section;

    public InfrastructSectionItem(InfrastructSectionItemCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        if (createDTO.getName()!=null){
            this.nameUz=createDTO.getName().getUz();
            this.nameRu=createDTO.getName().getRu();
            this.nameEn=createDTO.getName().getEn();
        }
        if (createDTO.getTimeOrDistance()!=null){
            this.timeOrDistanceUz=createDTO.getTimeOrDistance().getUz();
            this.timeOrDistanceRu=createDTO.getTimeOrDistance().getRu();
            this.timeOrDistanceEn=createDTO.getTimeOrDistance().getEn();
        }
    }


    public InfrastructSectionItem(InfrastructSectionItemResponseDTO dto){
        if (dto==null){
            return;
        }
        this.id=dto.getId();
        if (dto.getName()!=null){
            this.nameUz=dto.getName().getUz();
            this.nameRu=dto.getName().getRu();
            this.nameEn=dto.getName().getEn();
        }
        if (dto.getTimeOrDistance()!=null){
            this.timeOrDistanceUz=dto.getTimeOrDistance().getUz();
            this.timeOrDistanceRu=dto.getTimeOrDistance().getRu();
            this.timeOrDistanceEn=dto.getTimeOrDistance().getEn();
        }
    }

}
