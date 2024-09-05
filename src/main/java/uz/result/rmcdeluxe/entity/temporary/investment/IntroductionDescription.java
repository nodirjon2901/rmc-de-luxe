package uz.result.rmcdeluxe.entity.temporary.investment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "introduction_description")
public class IntroductionDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String descriptionUz;

    String descriptionEn;

    String descriptionRu;
}
