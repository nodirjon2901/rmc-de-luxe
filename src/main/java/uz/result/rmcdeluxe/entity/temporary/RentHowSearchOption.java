package uz.result.rmcdeluxe.entity.temporary;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "rent_how_search_option")
public class RentHowSearchOption {

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

    @ManyToOne
    @JsonIgnore
    RentPageHowSearchForm searchForm;

}
