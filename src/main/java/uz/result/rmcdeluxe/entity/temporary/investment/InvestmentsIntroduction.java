    package uz.result.rmcdeluxe.entity.temporary.investment;

    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import uz.result.rmcdeluxe.entity.Photo;

    import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Entity(name = "investment_introduction")
    public class InvestmentsIntroduction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @OneToOne
        Photo photo;

        String titleUz;

        String titleEng;

        String titleRu;

        String descriptionUz;

        String descriptionRu;

        String descriptionEn;

        @OneToMany
        List<IntroductionDescription> descriptions;
    }
