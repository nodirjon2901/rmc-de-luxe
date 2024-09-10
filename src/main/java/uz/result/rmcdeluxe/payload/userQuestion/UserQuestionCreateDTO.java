package uz.result.rmcdeluxe.payload.userQuestion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserQuestionCreateDTO {

    String fullName;

    String phoneNumber;

    String email;

    String question;

}
