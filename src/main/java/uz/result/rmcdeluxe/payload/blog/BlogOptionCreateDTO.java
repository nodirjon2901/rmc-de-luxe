package uz.result.rmcdeluxe.payload.blog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogOptionCreateDTO {

    @JsonProperty("description")
    Translation title;

    @JsonProperty("text")
    Translation description;

    Integer orderNum;

    Photo photo;

}
