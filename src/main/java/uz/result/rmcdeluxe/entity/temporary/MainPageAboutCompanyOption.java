package uz.result.rmcdeluxe.entity.temporary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "main_page_company_option")
public class MainPageAboutCompanyOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String nameEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @ManyToOne
    @JsonIgnore
    MainPageAboutCompany aboutCompany;
}
