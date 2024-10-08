package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.blog.BlogOptionCreateDTO;
import uz.result.rmcdeluxe.payload.blog.BlogOptionResponseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "blog_option")
public class BlogOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    Integer orderNum;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Blog blog;

    public BlogOption(BlogOptionCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        this.photo = createDTO.getPhoto();
        if (createDTO.getTitle() != null) {
            this.titleUz = createDTO.getTitle().getUz();
            this.titleRu = createDTO.getTitle().getRu();
            this.titleEn = createDTO.getTitle().getEn();
        }
        if (createDTO.getDescription() != null) {
            this.descriptionUz = createDTO.getDescription().getUz();
            this.descriptionRu = createDTO.getDescription().getRu();
            this.descriptionEn = createDTO.getDescription().getEn();
        }
    }

    public BlogOption(BlogOptionResponseDTO dto) {
        if (dto == null) {
            return;
        }
        this.id = dto.getId();
        this.photo = dto.getPhoto();
        if (dto.getTitle() != null) {
            this.titleUz = dto.getTitle().getUz();
            this.titleRu = dto.getTitle().getRu();
            this.titleEn = dto.getTitle().getEn();
        }
        if (dto.getDescription() != null) {
            this.descriptionUz = dto.getDescription().getUz();
            this.descriptionRu = dto.getDescription().getRu();
            this.descriptionEn = dto.getDescription().getEn();
        }
    }

}
