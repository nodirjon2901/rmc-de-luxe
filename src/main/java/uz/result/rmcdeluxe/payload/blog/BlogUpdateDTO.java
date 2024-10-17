package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogUpdateDTO {

    Long id;

    List<BlogOptionResponseDTO> options;

    String typeName;

    boolean active;

    boolean main;

}
