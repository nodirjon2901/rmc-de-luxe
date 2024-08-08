package uz.result.rmcdeluxe.entity;

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
@Entity(name = "investment")
public class Investments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    Photo photo;

    String headingUz;

    String headingRu;

    String headingEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

}
