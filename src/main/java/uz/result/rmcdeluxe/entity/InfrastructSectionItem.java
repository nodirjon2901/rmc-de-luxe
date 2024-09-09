package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
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
public class InfrastructSectionItem {

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
