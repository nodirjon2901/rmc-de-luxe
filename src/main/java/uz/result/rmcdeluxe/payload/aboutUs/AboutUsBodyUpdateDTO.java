package uz.result.rmcdeluxe.payload.aboutUs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBodyUpdateDTO {

    Integer id;

    Translation title;

    Translation subtitle;

    Translation description;

}
