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

}
