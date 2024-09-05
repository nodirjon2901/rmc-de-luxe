package uz.result.rmcdeluxe.entity.temporary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "main_page_investment")
public class MainPageInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    @Column(length = 3000)
    String descriptionUz;

    @Column(length = 3000)
    String descriptionRu;

    @Column(length = 3000)
    String descriptionEn;

    String link;

    @OneToOne
    Photo mainPhoto;

    @OneToOne
    Photo photo;

}
