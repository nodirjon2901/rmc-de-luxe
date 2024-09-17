package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructSectionCreateDTO;
import uz.result.rmcdeluxe.payload.infrastructureArea.InfrastructSectionResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "infrastruct_section")
public class InfrastructSection {

    @Id
    @GeneratedValue
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @ManyToOne
    @JsonIgnore
    InfrastructureArea infrastructureArea;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InfrastructSectionItem> sectionItems;

//    @PostPersist
    @PostUpdate
    void setSubEntity() {
        if (this.sectionItems != null) {
            this.sectionItems.forEach(item -> item.setSection(this));
        }
    }

    public InfrastructSection(InfrastructSectionCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        if (createDTO.getTitle() != null) {
            this.titleUz = createDTO.getTitle().getUz();
            this.titleRu = createDTO.getTitle().getRu();
            this.titleEn = createDTO.getTitle().getEn();
        }
        if (createDTO.getDescription() != null) {
            this.descriptionUz = createDTO.getDescription().getUz();
            this.descriptionRu = createDTO.getDescription().getRu();
            this.descriptionEn = createDTO.getDescription().getEn();
        }
        if (createDTO.getSectionItems() != null && !createDTO.getSectionItems().isEmpty()) {
            this.sectionItems = createDTO.getSectionItems().stream().map(
                    InfrastructSectionItem::new
            ).collect(Collectors.toList());
        }
    }

    public InfrastructSection(InfrastructSectionResponseDTO dto) {
        if (dto == null) {
            return;
        }
        this.id = dto.getId();
        if (dto.getTitle() != null) {
            this.titleUz = dto.getTitle().getUz();
            this.titleRu = dto.getTitle().getRu();
            this.titleEn = dto.getTitle().getEn();
        }
        if (dto.getDescription() != null) {
            this.descriptionUz = dto.getDescription().getUz();
            this.descriptionRu = dto.getDescription().getRu();
            this.descriptionEn = dto.getDescription().getEn();
        }
        if (dto.getSectionItems() != null && !dto.getSectionItems().isEmpty()) {
            this.sectionItems = dto.getSectionItems().stream().map(
                    InfrastructSectionItem::new
            ).collect(Collectors.toList());
        }
    }

}
