package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Blog;
import uz.result.rmcdeluxe.entity.BlogOption;
import uz.result.rmcdeluxe.entity.BlogType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogMapper {

    Long id;

    String slug;

    List<BlogOptionMapper> options;

    BlogTypeMapper type;

    Date createdDate;

    Integer viewCounter;

    boolean active;

    boolean main;

    public BlogMapper(Blog blog, String lang) {
        this.id = blog.getId();
        this.slug = blog.getSlug();
        this.type = new BlogTypeMapper(blog.getType(),lang);
        this.createdDate = blog.getCreatedDate();
        this.viewCounter = blog.getViewCounter();
        this.active = blog.isActive();
        this.main = blog.isMain();
        this.options = blog.getOptions().stream()
                .map(option -> new BlogOptionMapper(option, lang))
                .collect(Collectors.toList());
    }

}
