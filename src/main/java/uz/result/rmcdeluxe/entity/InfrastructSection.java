package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructSection {

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

}
