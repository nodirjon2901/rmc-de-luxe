package uz.result.rmcdeluxe.entity.temporary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "main_page_about_company")
public class MainPageAboutCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String subtitleUz;

    String subtitleRu;

    String subtitleEn;

    @OneToOne
    Photo photo;

    @OneToMany(mappedBy = "aboutCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MainPageAboutCompanyOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId() {
        if (this.options != null) {
            this.options.forEach(option -> option.setAboutCompany(this));
        }
    }

}
