package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String slug;//unique

    @OneToOne(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    BlogOption headOption;

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
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

}
