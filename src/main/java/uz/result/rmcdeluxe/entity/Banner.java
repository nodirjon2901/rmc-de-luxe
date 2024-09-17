package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonProperty(value = "data")
    @OneToMany(mappedBy = "banner", fetch = FetchType.EAGER)
    @OrderBy("orderNum ASC")
    List<BannerSlider> sliders;


}
