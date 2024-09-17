package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import uz.result.rmcdeluxe.payload.review.ReviewCreateDTO;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date createdDate;

    boolean active;

    public Review(ReviewCreateDTO createDTO){
        if (createDTO==null){
            return;
        }
        if (createDTO.getTitle()!=null){
            this.titleUz=createDTO.getTitle().getUz();
            this.titleRu=createDTO.getTitle().getRu();
            this.titleEn=createDTO.getTitle().getEn();
        }
        if (createDTO.getDescription()!=null){
            this.descriptionUz=createDTO.getDescription().getUz();
            this.descriptionRu=createDTO.getDescription().getRu();
            this.descriptionEn=createDTO.getDescription().getEn();
        }
    }

}
