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
@Entity(name = "rent_page_faq_form")
public class RentPageFAQForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "faqForm", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RentPageFAQOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId() {
        if (this.options != null) {
            this.options.forEach(option -> option.setFaqForm(this));
        }
    }

}
