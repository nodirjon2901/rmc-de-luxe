package uz.result.rmcdeluxe.entity.temporary;

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
@Entity(name = "rent_page_how_search")
public class RentPageHowSearchForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "searchForm", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RentHowSearchOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId(){
        if (this.options!=null){
            this.options.forEach(option -> option.setSearchForm(this));
        }
    }

}
