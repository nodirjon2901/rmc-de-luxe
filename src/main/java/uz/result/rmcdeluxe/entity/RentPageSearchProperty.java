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
@Entity(name = "rent_page_search_property")
public class RentPageSearchProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    @Column(length = 500)
    String descriptionUz;

    @Column(length = 500)
    String descriptionRu;

    @Column(length = 500)
    String descriptionEn;

    @OneToMany(mappedBy = "searchProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RentSearchPropertyOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId() {
        if (this.options != null) {
            this.options.forEach(option -> option.setSearchProperty(this));
        }
    }

}
