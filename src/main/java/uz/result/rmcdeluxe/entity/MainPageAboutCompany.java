package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
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
        if (options != null) {
            this.options.forEach(option -> option.setAboutCompany(this));
        }
    }

}
