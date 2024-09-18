package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import uz.result.rmcdeluxe.payload.blog.BlogCreateDTO;
import uz.result.rmcdeluxe.payload.blog.BlogOptionCreateDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String slug;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BlogOption> options;

    @ManyToOne
    @JsonIgnore
    BlogType type;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date createdDate;

    Integer viewCounter;

    boolean active;

    boolean main;

    @PostPersist
    @PostUpdate
    private void setOptionEntity() {
        if (this.options != null)
            this.options.forEach(option -> option.setBlog(this));
    }

    public Blog(BlogCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        this.main = createDTO.isMain();
        if (createDTO.getOptions() != null && !createDTO.getOptions().isEmpty()) {
            if (this.options == null) {
                this.options = new ArrayList<>();
            }
            for (BlogOptionCreateDTO optionCreateDTO : createDTO.getOptions()) {
                BlogOption option = new BlogOption(optionCreateDTO);
                option.setBlog(this);
                this.options.add(option);
            }
        }
    }

}
