package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Blog;
import uz.result.rmcdeluxe.entity.BlogType;

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

    BlogOptionResponseDTO headOption;

    List<BlogOptionResponseDTO> options;

    BlogTypeResponse type;

    Date createdDate;

    Integer viewCounter;

    boolean active;

    boolean main;

    public BlogResponseDTO(Blog blog){
        this.id= blog.getId();
        this.slug= blog.getSlug();
        this.type=new BlogTypeResponse(blog.getType());
        this.createdDate=blog.getCreatedDate();
        this.viewCounter= blog.getViewCounter();
        this.active= blog.isActive();
        this.main= blog.isMain();
        this.headOption=new BlogOptionResponseDTO(blog.getHeadOption());
        this.options=blog.getOptions().stream()
                .map(BlogOptionResponseDTO::new)
                .collect(Collectors.toList());
    }

}
