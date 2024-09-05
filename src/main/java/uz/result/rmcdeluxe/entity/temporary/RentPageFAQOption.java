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
@Entity(name = "ren_faq_option")
public class RentPageFAQOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 500)
    String questionUz;

    @Column(length = 500)
    String questionRu;

    @Column(length = 500)
    String questionEn;

    @Column(length = 500)
    String answerUz;

    @Column(length = 500)
    String answerRu;

    @Column(length = 500)
    String answerEn;

    @ManyToOne
    @JsonIgnore
    RentPageFAQForm faqForm;
}
