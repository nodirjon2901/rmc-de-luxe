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
@Entity(name = "rent_work_process_option")
public class RentPageWorkProcessOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String nameEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    Integer orderNum;

    @ManyToOne
    @JsonIgnore
    RentPageWorkProcess workProcess;

}
