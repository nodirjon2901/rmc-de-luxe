package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.UserQuestion;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.repository.UserQuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuestionService {

    private final UserQuestionRepository questionRepository;

    public ResponseEntity<ApiResponse<UserQuestion>> create(UserQuestion userQuestion) {
        ApiResponse<UserQuestion> response = new ApiResponse<>();
        response.setData(questionRepository.save(userQuestion));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (!questionRepository.existsById(id)) {
            throw new NotFoundException("UserQuestion is not found by id: " + id);
        }
        questionRepository.deleteById(id);
        response.setMessage("Successfully deleted!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<UserQuestion>>> findAll() {
        ApiResponse<List<UserQuestion>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        List<UserQuestion> all = questionRepository.findAll();
        all.forEach(userQuestion -> response.getData().add(userQuestion));
        response.setMessage("Found " + all.size() + " userQuestion(s)");
        return ResponseEntity.ok(response);
    }

}
