package uz.result.rmcdeluxe.payload.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Blog;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.payload.Translation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponseDTO {

    Long id;

    String slug;

    Translation title;

    List<BlogOptionResponseDTO> options;

    BlogTypeResponse type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date createdDate;

    Integer viewCounter;

    boolean active;

    boolean main;

    public BlogResponseDTO(Blog blog) {
        this.id = blog.getId();
        this.slug = blog.getSlug();
        this.title = new Translation(blog.getTitleUz(), blog.getTitleRu(), blog.getTitleEn());
        this.type = new BlogTypeResponse(blog.getType());
        this.createdDate = blog.getCreatedDate();
        this.viewCounter = blog.getViewCounter();
        this.active = blog.isActive();
        this.main = blog.isMain();
        this.options = blog.getOptions().stream()
                .map(BlogOptionResponseDTO::new)
                .collect(Collectors.toList());
    }

}
