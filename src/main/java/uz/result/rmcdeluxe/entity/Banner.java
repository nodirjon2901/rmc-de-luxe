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
@Entity(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "banner", orphanRemoval = true)
    List<BannerSlider> sliders;

    @PostPersist
    @PostUpdate
    public void setOptionId() {
        if (this.sliders != null) {
            this.sliders.forEach(i -> i.setBanner(this));
        }
    }
}
