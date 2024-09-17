package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.blog.BlogTypeCreateDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "blog_type")
public class BlogType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String nameEn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", orphanRemoval = true)
    List<Blog> blogs;

    public BlogType(BlogTypeCreateDTO createDTO){
        if (createDTO==null){
            return;
        }
        if (createDTO.getName()!=null){
            this.nameUz=createDTO.getName().getUz();
            this.nameRu=createDTO.getName().getRu();
            this.nameEn=createDTO.getName().getEn();
        }
    }

}
